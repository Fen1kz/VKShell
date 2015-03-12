package vkshell.commands.core;

import vkshell.app.App;

import java.lang.reflect.Field;
import java.util.*;

public abstract class Command implements ICommand {
    private static final Comparator<String> optionssort = new Comparator<String>() {
        @Override
        public int compare(String key1, String key2) {
            if (key1.length() != key2.length()) {
                return (key1.length() > key2.length()) ? -1 : 1;
            } else {
                return key1.compareTo(key2);
            }
        }
    };
    protected SortedMap<String, Option> options = new TreeMap<>(optionssort);

    @AOption(names = {"verbose", "v"}, desc = "debugs command", flags = {AOption.Flag.NOHELP})
    public boolean verbose = false; //@todo to debug

    @AOption(names = {"help", "?"}, desc = "Display Help")
    public boolean help = false;

    public Command() {
        registerOptions();
    }

    @Override
    public void execute() {
        execute(new CommandArgs());
    }

    @Override
    public void execute(CommandArgs args) {
        if (help) {
            App.get().cli().out().println("Helpful help.");
            return;
        }
        action(args);
    }

    protected abstract void action(CommandArgs args);

    @Override
    public String getName() {
        return this.getNames()[0];
    }

    @Override
    public String[] getNames() {
        return this.getClass().getAnnotation(ACommand.class).names();
    }

    @Override
    public String getDesc() {
        return this.getClass().getAnnotation(ACommand.class).desc();
    }

    /*
    * Options
    */
    protected void registerOptions() {
        List<Class<? extends ICommand>> classes = new ArrayList<>();
        Class superclass = this.getClass();

        do {
            classes.add(0, superclass);
            superclass = superclass.getSuperclass();
        } while (superclass != null);

        for (Class<? extends ICommand> clazz : classes) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(AOption.class)) {
                    AOption annotation = field.getAnnotation(AOption.class);
                    for (String name : annotation.names()) {
                        options.put(name, new Option(field));
                    }
                    /*
                    try {
                        System.out.println("name: " + Arrays.asList(annotation.names())
                                        + "||desc: " + annotation.desc()
                                        + "||value: " + field.get(this)
                        );
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }
    }

    @Override
    public SortedMap<String, Option> getOptions() {
        return options;
    }

    @Override
    public <T> T getOption(String name, Class<T> type) {
        T value = null;
        try {
            //noinspection unchecked
            value = (T) this.options.get(name).getField().get(this);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(this.getClass() == obj.getClass())) return false;
        ICommand other = (ICommand) obj;

        HashSet<Option> options = new HashSet<Option>(this.getOptions().values());
        if (!options.equals(new HashSet<Option>(other.getOptions().values()))) {
            return false;
        }

        Iterator<Option> it = options.iterator();
        while (it.hasNext()) {
            Field field = it.next().getField();
            try {
                Object o1 = field.get(this);
                Object o2 = field.get(other);
                //System.out.println("Testing <"+option.getName()+"> with ["+o1+"]["+o2+"]");
                if (!(o1 == o2 || o1.equals(o2))) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
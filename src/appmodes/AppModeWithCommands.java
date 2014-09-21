package appmodes;

import commands.Global.BackCmd;
import commands.Global.EchoCmd;
import commands.Global.ExitCmd;
import commands.core.CommandParser;
import commands.core.ICommand;
import commands.core.exceptions.UnknownCommandException;
import main.CLI;
import appmodes.interfaces.IAppModeWithCommands;

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AppModeWithCommands extends AppMode implements IAppModeWithCommands {
    public static final Comparator<String> commandMapSort = new Comparator<String>() {
        @Override
        public int compare(String key1, String key2) {
            return key1.compareTo(key2);
        }
    };
    protected SortedMap<String, Class<? extends ICommand>> commandMap;

    public AppModeWithCommands(CLI cli) {
        super(cli);
    }

    public SortedMap<String, Class<? extends ICommand>> getCommandMap() {
        return commandMap;
    }

    @Override
    public void start() {
        while (handleInput(getInput())) ;
    }

    public boolean handleInput(String input) {
        CommandParser.ParsedCommand parsedCommand = null;
        try {
            parsedCommand = CommandParser.parseCommand(this, input);
        } catch (UnknownCommandException e) {
            getCLI().out().println("Unknown command: " + e.commandname + "");
            return true;
        }
        if (parsedCommand.getCommand().getClass() == BackCmd.class) {
            return false;
        }
        parsedCommand.getCommand().execute(parsedCommand.getCommandArgs());
        getCLI().out().println();
        return true;
    }

    @Override
    protected void init() {
        super.init();
        commandMap = new TreeMap<>(commandMapSort);
        registerCommands(
                EchoCmd.class,
                ExitCmd.class);
    }

    protected void registerCommands(Class<? extends ICommand>... commandClasses) {
        try {
            for (Class<? extends ICommand> commandClass : commandClasses) {
                String[] names = commandClass.newInstance().getNames();
                getCLI().out().print("Registered command <" + names[0] + "> " + commandClass);

                getCLI().out().print("[");
                for (String name : names) {
                    System.out.print(name + ", ");
                    commandMap.put(name, commandClass);
                }
                getCLI().out().print("]");
                getCLI().out().println();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
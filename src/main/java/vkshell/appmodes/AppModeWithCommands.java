package vkshell.appmodes;

import org.springframework.beans.factory.annotation.Autowired;
import vkshell.commands.Global.BackCmd;
import vkshell.commands.Global.EchoCmd;
import vkshell.commands.Global.ExitCmd;

import vkshell.commands.core.ICommand;
import vkshell.commands.core.exceptions.UnknownCommandException;
import vkshell.appmodes.interfaces.IAppModeWithCommands;
import vkshell.shell.cmd.tools.interfaces.ICommandParser;

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

    @Autowired
    protected ICommandParser commandParser;

    protected SortedMap<String, Class<? extends ICommand>> commandMap;

    public SortedMap<String, Class<? extends ICommand>> getCommandMap() {
        return commandMap;
    }

    @Override
    public void start() {
        while (handleInput(getInput())) ;
    }

    public boolean handleInput(String input) {
        ICommandParser.IParsedCommand parsedCommand = null;
        try {
            parsedCommand = commandParser.parseCommand(this, input);
        } catch (UnknownCommandException e) {
            cli.out().println("Unknown command: " + e.commandname + "");
            return true;
        }
        if (parsedCommand.getCommand().getClass() == BackCmd.class) {
            return false;
        }
        parsedCommand.getCommand().execute(parsedCommand.getCommandArgs());
        cli.out().println();
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
                cli.out().print("Registered command <" + names[0] + "> " + commandClass);

                cli.out().print("[");
                boolean first = true;
                for (String name : names) {
                    if (first) first = false;
                    else cli.out().print(", ");
                    cli.out().print(name);
                    commandMap.put(name, commandClass);
                }
                cli.out().print("]");
                cli.out().println();
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
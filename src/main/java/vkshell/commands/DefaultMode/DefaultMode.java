package vkshell.commands.DefaultMode;

import vkshell.appmodes.AppModeWithCommands;
import vkshell.commands.core.CommandParser;
import vkshell.commands.core.ICommand;
import vkshell.main.cli.ICLI;

import java.util.*;

public class DefaultMode extends AppModeWithCommands {
    public DefaultMode(ICLI cli) {
        super(cli);
    }

    @Override
    protected void init() {
        super.init();
        registerCommands(
                AuthCmd.class,
                IMCmd.class,
                AudioCmd.class);
    }

    @Override
    public void start() {
        showMenu();
        super.start();
    }

    @Override
    public String getPrompt() {
        return "vkshell>";
    }

    private void showMenu() {
        int cmdIndex = 0;
        Set<Class<? extends ICommand>> set = new LinkedHashSet<>();

        set.addAll(commandMap.values());

        System.out.println("------ CLI Command Listing ------");
        for (Class<? extends ICommand> value : set) {
            ICommand cmdObj = CommandParser.getCommandInstance(value);
            String[] names = cmdObj.getNames();
            System.out.print(++cmdIndex + ") ");
            System.out.print(names[0]);
            int i = 1;
            if (i < (names.length)) {
                System.out.print("(");
                System.out.print(names[i++]);
                while (i < names.length) {
                    System.out.print(", " + names[i++]);
                }
                System.out.print(")");
            }
            System.out.print(": " + cmdObj.getDesc());
            System.out.println();
        }
        System.out.println();
    }
}

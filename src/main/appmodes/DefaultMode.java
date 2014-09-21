package main.appmodes;

import commands.DefaultMode.AuthCmd;
import commands.DefaultMode.IMCmd;
import commands.core.CommandParser;
import commands.core.ICommand;
import main.CLI;

import java.util.SortedSet;
import java.util.TreeSet;

public class DefaultMode extends AppModeWithCommands {
    public DefaultMode(CLI cli) {
        super(cli);
    }

    @Override
    protected void init() {
        super.init();
        registerCommands(
                AuthCmd.class,
                IMCmd.class);
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
        SortedSet<Class<? extends ICommand>> set = new TreeSet<>((c1, c2) -> (c1 == c2) ? 0 : -1);
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

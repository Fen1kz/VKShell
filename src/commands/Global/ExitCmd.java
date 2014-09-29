package commands.Global;

import commands.core.ACommand;
import commands.core.Command;
import commands.core.CommandArgs;

@ACommand(names = {"exit", "quit"}, desc = "System Exit")
public class ExitCmd extends Command {
    @Override
    public void action(CommandArgs data) {
        System.exit(0);
    }
}
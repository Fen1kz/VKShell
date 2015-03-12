package vkshell.commands.Global;

import vkshell.commands.core.ACommand;
import vkshell.commands.core.Command;
import vkshell.commands.core.CommandArgs;

@ACommand(names = {"exit", "quit"}, desc = "System Exit")
public class ExitCmd extends Command {
    @Override
    public void action(CommandArgs data) {
        System.exit(0);
    }
}
package vkshell.commands.Global;

import vkshell.commands.core.ACommand;
import vkshell.commands.core.Command;
import vkshell.commands.core.CommandArgs;

@ACommand(names = {"back", "", "<"}, desc = "return to previous mode")
public class BackCmd extends Command {
    @Override
    protected void action(CommandArgs args) {
    }
}

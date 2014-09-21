package commands.Global;

import commands.core.ACommand;
import commands.core.Command;
import commands.core.CommandArgs;

@ACommand(names = {"back", "..", "<"}, desc = "return to previous mode")
public class BackCmd extends Command {
    @Override
    protected void action(CommandArgs args) {
    }
}

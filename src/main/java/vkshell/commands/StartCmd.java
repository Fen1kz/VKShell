package vkshell.commands;

import org.springframework.beans.factory.annotation.Autowired;
import vkshell.commands.DefaultMode.DefaultMode;
import vkshell.commands.core.AOption;
import vkshell.commands.core.Command;
import vkshell.commands.core.CommandArgs;

public class StartCmd extends Command {
    @AOption(names = {"config"}, desc = "")
    public String config = null;

    @Override
    protected void action(CommandArgs args) {

        new DefaultMode().start();
    }
}

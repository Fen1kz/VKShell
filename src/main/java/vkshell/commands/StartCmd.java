package vkshell.commands;

import vkshell.commands.core.AOption;
import vkshell.commands.core.Command;
import vkshell.commands.core.CommandArgs;
import vkshell.app.App;
import vkshell.main.cli.ICLI;
import vkshell.main.cli.JLine2CLI;

public class StartCmd extends Command {
    @AOption(names = {"config"}, desc = "")
    public String config = null;

    @Override
    protected void action(CommandArgs args) {
        //CLI cli = new CLI(System.in);
        ICLI cli = new JLine2CLI(System.in);
        if (config != null)
            App.get().config().loadConfig(config);
        App.get().start(cli);
    }
}

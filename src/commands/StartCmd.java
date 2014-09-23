package commands;

import commands.core.AOption;
import commands.core.Command;
import commands.core.CommandArgs;
import app.App;
import main.cli.ICLI;
import main.cli.JLine2CLI;

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

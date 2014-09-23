package appmodes;

import commands.Global.BackCmd;
import main.cli.CLI;

public abstract class SubMode extends AppModeWithCommands {
    public SubMode(CLI cli) {
        super(cli);
    }

    @Override
    public String getPrompt() {
        return "vkshell>";
    }

    @Override
    protected void init() {
        super.init();
        registerCommands(
                BackCmd.class);
    }
}

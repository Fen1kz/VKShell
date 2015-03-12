package vkshell.appmodes;

import vkshell.commands.Global.BackCmd;
import vkshell.main.cli.ICLI;

public abstract class SubMode extends AppModeWithCommands {
    public SubMode(ICLI cli) {
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

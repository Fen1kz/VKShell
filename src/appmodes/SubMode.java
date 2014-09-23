package appmodes;

import commands.Global.BackCmd;
import main.cli.ICLI;

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

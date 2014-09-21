package main.appmodes.SubModes;

import commands.IMMode.ListCmd;
import main.CLI;
import main.appmodes.SubMode;

public class IMMode extends SubMode {
    public IMMode(CLI cli) {
        super(cli);
    }

    @Override
    protected void init() {
        super.init();
        registerCommands(
                ListCmd.class);
    }

    @Override
    public String getPrompt() {
        return super.getPrompt() + "im>";
    }
}
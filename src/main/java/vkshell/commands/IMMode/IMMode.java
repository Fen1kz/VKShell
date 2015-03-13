package vkshell.commands.IMMode;

import vkshell.main.cli.ICLI;
import vkshell.appmodes.SubMode;

public class IMMode extends SubMode {
    @Override
    protected void init() {
        super.init();
        registerCommands(
                //ListCmd.class
        );
    }

    @Override
    public String getPrompt() {
        return super.getPrompt() + "im>";
    }
}
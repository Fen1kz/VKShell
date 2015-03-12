package vkshell.commands.AudioMode;

import vkshell.main.cli.ICLI;
import vkshell.appmodes.SubMode;

public class AudioMode extends SubMode {
    public AudioMode(ICLI cli) {
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
        return super.getPrompt() + "audio>";
    }
}
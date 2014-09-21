package appmodes.SubModes;

import commands.AudioMode.*;
import main.CLI;
import appmodes.SubMode;

public class AudioMode extends SubMode {
    public AudioMode(CLI cli) {
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
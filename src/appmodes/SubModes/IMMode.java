package appmodes.SubModes;

import appmodes.SubMode;
import main.cli.CLI;

public class IMMode extends SubMode {
    public IMMode(CLI cli) {
        super(cli);
    }

    @Override
    protected void init() {
        super.init();
        registerCommands();
    }

    @Override
    public String getPrompt() {
        return super.getPrompt() + "im>";
    }
}
package appmodes.SubModes;

import appmodes.SubMode;
import main.cli.ICLI;

public class IMMode extends SubMode {
    public IMMode(ICLI cli) {
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
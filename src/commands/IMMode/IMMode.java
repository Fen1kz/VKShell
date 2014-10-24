package commands.IMMode;

import main.cli.ICLI;
import appmodes.SubMode;

public class IMMode extends SubMode {
    public IMMode(ICLI cli) {
        super(cli);
    }

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
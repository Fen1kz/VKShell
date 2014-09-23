package appmodes;

import main.cli.ICLI;
import appmodes.interfaces.IAppMode;

import java.io.IOException;

public abstract class AppMode implements IAppMode {
    protected ICLI cli;

    public AppMode(ICLI cli) {
        this.cli = cli;
        init();
    }

    @Override
    public ICLI getCLI() {
        return cli;
    }

    protected void init() {
    }

    ;

    protected String getInput() {
        getCLI().out().print(getPrompt() + " ");
        String inputline = null;
        try {
            inputline = cli.getInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputline;
    }
}

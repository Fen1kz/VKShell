package appmodes;

import main.CLI;
import appmodes.interfaces.IAppMode;

import java.io.IOException;

public abstract class AppMode implements IAppMode {
    protected CLI cli;

    public AppMode(CLI cli) {
        this.cli = cli;
        init();
    }

    @Override
    public CLI getCLI() {
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

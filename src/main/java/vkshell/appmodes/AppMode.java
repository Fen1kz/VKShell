package vkshell.appmodes;

import org.springframework.beans.factory.annotation.Autowired;
import vkshell.main.cli.ICLI;
import vkshell.appmodes.interfaces.IAppMode;

import java.io.IOException;

public abstract class AppMode implements IAppMode {
    @Autowired
    ICLI cli;

    public AppMode() {
        init();
    }

    protected void init() {}

    protected String getInput() {
        cli.out().print(getPrompt() + " ");
        String inputline = null;
        try {
            inputline = cli.getInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputline;
    }
}

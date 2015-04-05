package vkshell.appmodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import vkshell.shell.cli.ICLI;
import vkshell.appmodes.interfaces.IAppMode;

import java.io.IOException;

@Configurable("appmode")
public abstract class AppMode implements IAppMode {
    @Autowired
    protected ICLI cli;

    protected void init() {
    }

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

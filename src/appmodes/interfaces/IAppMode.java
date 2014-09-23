package appmodes.interfaces;

import main.cli.ICLI;

public interface IAppMode {
    public ICLI getCLI();

    public void start();

    public String getPrompt();
}

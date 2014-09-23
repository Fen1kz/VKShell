package appmodes.interfaces;

import main.cli.CLI;

public interface IAppMode {
    public CLI getCLI();

    public void start();

    public String getPrompt();
}

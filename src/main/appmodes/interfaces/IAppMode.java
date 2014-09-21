package main.appmodes.interfaces;

import main.CLI;

public interface IAppMode {
    public CLI getCLI();

    public void start();

    public String getPrompt();
}

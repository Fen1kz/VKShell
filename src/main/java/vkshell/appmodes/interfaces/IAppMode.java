package vkshell.appmodes.interfaces;

import vkshell.main.cli.ICLI;

public interface IAppMode {
    public ICLI getCLI();

    public void start();

    public String getPrompt();
}

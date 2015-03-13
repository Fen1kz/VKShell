package vkshell.appmodes.interfaces;

import vkshell.main.cli.ICLI;

public interface IAppMode {
    public void start();

    public String getPrompt();
}

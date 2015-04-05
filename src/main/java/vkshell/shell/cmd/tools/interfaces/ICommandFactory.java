package vkshell.shell.cmd.tools.interfaces;

import vkshell.commands.core.ICommand;

public interface ICommandFactory {
    public <T extends ICommand> T getCommandInstance(Class<T> clazz);
}

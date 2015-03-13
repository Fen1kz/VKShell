package vkshell.shell.cmd.tools.interfaces;

import vkshell.appmodes.SelectCommandMode;
import vkshell.appmodes.interfaces.IAppModeWithCommands;
import vkshell.commands.core.CommandArgs;
import vkshell.commands.core.ICommand;
import vkshell.commands.core.exceptions.UnknownCommandException;

public interface ICommandParser {
    public IParsedCommand parseCommand(IAppModeWithCommands mode, String input) throws UnknownCommandException;

    public IParsedCommand parseCommand(final Class<? extends ICommand> commandclass, final String inputline);

    public ICommand getCommandInstance(Class<? extends ICommand> value);

    public Class<? extends ICommand> findCommandClass(IAppModeWithCommands selectCommandMode, String inputline) throws UnknownCommandException;

    public static interface IParsedCommand<T extends ICommand> {
        public boolean valid();

        public void execute();

        public T getCommand();

        public CommandArgs getCommandArgs();
    }
}

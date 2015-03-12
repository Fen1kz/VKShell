package vkshell.shell.cmd.tools.interfaces;

import vkshell.appmodes.interfaces.IAppModeWithCommands;
import vkshell.commands.core.CommandArgs;
import vkshell.commands.core.ICommand;
import vkshell.commands.core.exceptions.UnknownCommandException;

public interface ICommandParser {
    public IParsedCommand parseCommand(final IAppModeWithCommands mode, final String input) throws UnknownCommandException;

    public IParsedCommand parseCommand(final Class<? extends ICommand> commandclass, final String inputline);

    public static interface IParsedCommand<T extends ICommand> {
        public boolean valid();

        public void execute();

        public T getCommand();

        public CommandArgs getCommandArgs();
    }
}

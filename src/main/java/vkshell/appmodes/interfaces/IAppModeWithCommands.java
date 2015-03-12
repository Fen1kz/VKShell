package vkshell.appmodes.interfaces;

import vkshell.commands.core.ICommand;

import java.util.SortedMap;

public interface IAppModeWithCommands extends IAppMode {
    public SortedMap<String, Class<? extends ICommand>> getCommandMap();
}

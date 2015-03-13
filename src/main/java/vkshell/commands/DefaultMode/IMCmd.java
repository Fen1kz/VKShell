package vkshell.commands.DefaultMode;

import vkshell.commands.core.ACommand;
import vkshell.commands.core.CommandArgs;
import vkshell.commands.core.VKApiCommand;

import vkshell.commands.IMMode.IMMode;

@ACommand(names = {"im"}, desc = "launches IM session")
public class IMCmd extends VKApiCommand {
    @Override
    protected void vkAPIAction(CommandArgs args) {
        IMMode im = new IMMode();
        im.start();
    }
}
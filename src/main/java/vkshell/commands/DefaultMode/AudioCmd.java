package vkshell.commands.DefaultMode;

import vkshell.commands.core.ACommand;
import vkshell.commands.core.Command;
import vkshell.commands.core.CommandArgs;

import vkshell.commands.AudioMode.AudioMode;

@ACommand(names = {"audio"}, desc = "launches audio mode")
public class AudioCmd extends Command {
    @Override
    public void action(CommandArgs args) {
        AudioMode mode = new AudioMode();
        mode.start();
    }
}
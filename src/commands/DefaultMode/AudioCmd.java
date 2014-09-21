package commands.DefaultMode;

import commands.core.ACommand;
import commands.core.Command;
import commands.core.CommandArgs;
import app.App;
import appmodes.SubModes.AudioMode;

@ACommand(names = {"audio"}, desc = "launches audio mode")
public class AudioCmd extends Command {
    @Override
    public void action(CommandArgs args) {
        AudioMode mode = new AudioMode(App.get().cli());
        mode.start();
    }
}
package commands.DefaultMode;

import commands.core.ACommand;
import commands.core.CommandArgs;
import commands.core.VKApiCommand;
import app.App;
import appmodes.SubModes.IMMode;

@ACommand(names = {"im"}, desc = "launches IM session")
public class IMCmd extends VKApiCommand {
    @Override
    protected void vkAPIAction(CommandArgs args) {
        IMMode im = new IMMode(App.get().cli());
        im.start();
    }
}
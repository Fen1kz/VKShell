package commands.DefaultMode;

import commands.core.ACommand;
import commands.core.CommandArgs;
import commands.core.VKApiCommand;
import app.App;
import commands.IMMode.IMMode;

@ACommand(names = {"im"}, desc = "launches IM session")
public class IMCmd extends VKApiCommand {
    @Override
    protected void vkAPIAction(CommandArgs args) {
        IMMode im = new IMMode(App.get().cli());
        im.start();
    }
}
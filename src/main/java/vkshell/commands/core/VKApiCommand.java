package vkshell.commands.core;

import vkshell.api.exceptions.VKAPIException;
import vkshell.app.App;

public abstract class VKApiCommand extends Command {
    protected String userid;

    @AOption(names = {"debug", "r"}, desc = "shows raw debug")
    public boolean debug = false;

    @Override
    protected void action(CommandArgs args) {
        userid = App.get().getUserId();
        try {
            vkAPIAction(args);
        } catch (VKAPIException e) {
            switch (e.error) {
                case AUTH_FAILED:
                    App.get().cli().out().println("Auth error");
                    App.get().reauthorise();
                    try {
                        vkAPIAction(args);
                    } catch (VKAPIException e1) {
                        e1.printStackTrace();
                        //todo something
                    }
                    break;
                default:
                    App.get().cli().out().println("Unknown error =(");
            }
        }
    }

    protected abstract void vkAPIAction (CommandArgs args) throws VKAPIException;
}

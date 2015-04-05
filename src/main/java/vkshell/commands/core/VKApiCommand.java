package vkshell.commands.core;

import org.springframework.beans.factory.annotation.Autowired;
import vkshell.api.VkAPI;
import vkshell.api.exceptions.VKAPIException;

public abstract class VKApiCommand extends Command {
    @AOption(names = {"debug", "r"}, desc = "shows raw debug")
    public boolean debug = false;

    @Autowired
    protected VkAPI api;

    protected String userid;

    @Override
    protected void action(CommandArgs args) {
        userid = api.getUserId();
        try {
            vkAPIAction(args);
        } catch (VKAPIException e) {
            switch (e.error) {
                case AUTH_FAILED:
                    cli.out().println("Auth error");
//                    api.reauthorise();
//                    try {
//                        vkAPIAction(args);
//                    } catch (VKAPIException e1) {
//                        e1.printStackTrace();
//                        //todo something
//                    }
                    break;
                default:
                    cli.out().println("Unknown error =(");
            }
        }
    }

    protected abstract void vkAPIAction (CommandArgs args) throws VKAPIException;
}

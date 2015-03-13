package vkshell.commands.DefaultMode;

import vkshell.api.VkAPI;
import vkshell.commands.core.*;



@ACommand(names = {"auth"}, desc = "Performs authorization")
public class AuthCmd extends Command {
//    @AOption(names = {"auth", "method"}, desc = "")
//    public App.Authoriser authoriser = App.Authoriser.DIRECT;

    @AOption(names = {"appid"}, desc = "")
    public String appid = VkAPI.AUTH_APP_ID;

    @AOption(names = {"clear", "c"}, desc = "")
    public boolean clear = false;

    @AOption(names = {"revoke"}, desc = "")
    public boolean revoke = false;

    //@AOption(names = {"http", "nossl"}, desc = "Uppercases output")
    //public boolean ForceHTTP = false;

    @Override
    public void action(CommandArgs args) {
//        switch (authoriser) {
//            case DIRECT:
//            default:
//                App.get().authorise(authoriser, new CommandParser.ParsedCommand(this, args));
//        }
    }

    @Override
    public String getDesc() {
        return "auths";
    }
}
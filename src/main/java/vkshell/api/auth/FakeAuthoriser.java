package vkshell.api.auth;

import vkshell.commands.DefaultMode.AuthCmd;
import vkshell.shell.cmd.tools.interfaces.ICommandParser;


public class FakeAuthoriser implements IAuthoriser {
    @Override
    public VkAccessToken authorise(ICommandParser.IParsedCommand<AuthCmd> data) throws AuthoriseException, AuthoriseError {
        return new VkAccessToken("", "", "");
    }
}

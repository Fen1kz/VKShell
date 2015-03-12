package vkshell.api.auth;

import vkshell.commands.DefaultMode.AuthCmd;
import vkshell.commands.core.CommandParser;

public class FakeAuthoriser implements IAuthoriser {
    @Override
    public VkAccessToken authorise(CommandParser.ParsedCommand<AuthCmd> data) throws AuthoriseException, AuthoriseError {
        return new VkAccessToken("", "", "");
    }
}

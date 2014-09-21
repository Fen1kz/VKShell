package api.auth;

import commands.DefaultMode.AuthCmd;
import commands.core.CommandParser;

public class FakeAuthoriser implements IAuthoriser {
    @Override
    public VkAccessToken authorise(CommandParser.ParsedCommand<AuthCmd> data) throws AuthoriseException, AuthoriseError {
        return new VkAccessToken("", "", "");
    }
}

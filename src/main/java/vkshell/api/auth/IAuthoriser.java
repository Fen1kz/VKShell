package vkshell.api.auth;

import vkshell.commands.DefaultMode.AuthCmd;
import vkshell.shell.cmd.tools.interfaces.ICommandParser;


import java.util.Arrays;

public interface IAuthoriser {
    public VkAccessToken authorise(ICommandParser.IParsedCommand<AuthCmd> data) throws AuthoriseException, AuthoriseError;

    public static class AuthoriseException extends Exception {
        public final String reason;

        public AuthoriseException(String reason) {
            this.reason = reason;
        }

        @Override
        public String toString() {
            return reason;
        }
    }

    public static class AuthoriseError extends Error {
        public final String reason;
        public final Object[] data;

        public AuthoriseError(String reason, Object... data) {
            this.reason = reason;
            this.data = data;
        }

        @Override
        public String toString() {
            return super.toString() + reason + Arrays.toString(data);
        }
    }
}

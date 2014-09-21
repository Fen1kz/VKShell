package api.auth;

import commands.DefaultMode.AuthCmd;
import commands.core.CommandParser;

import java.util.Arrays;

public interface IAuthoriser {
    public VkAccessToken authorise(CommandParser.ParsedCommand<AuthCmd> data) throws AuthoriseException, AuthoriseError;

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

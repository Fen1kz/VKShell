package vkshell.commands.core.exceptions;

public class UnknownCommandException extends Exception {
    public final String commandname;

    public UnknownCommandException(String commandname) {
        this.commandname = commandname;
    }
}

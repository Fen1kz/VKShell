package commands.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandArgs {
    protected final List<String> arguments;

    public CommandArgs() {
        this.arguments = new ArrayList<String>();
    }

    public CommandArgs(String... arguments) {
        this.arguments = new ArrayList<String>(Arrays.asList(arguments));
    }

    public CommandArgs(List<String> rawArguments) {
        this.arguments = rawArguments;
    }

    public List<String> getArgumentsList() {
        return arguments;
    }

    public String get(int number) {
        String arg = null;
        try {
            arg = arguments.get(number);
        } catch (IndexOutOfBoundsException e) {
            /* ignore */
        }
        return arg;
    }

    public String get(String option, int number) {
        String arg = null;
        try {
            arg = arguments.get(number);
        } catch (IndexOutOfBoundsException e) {
            /* ignore */
        }
        return arg;
    }

    @Override
    public String toString() {
        return "Data: " + arguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(this.getClass() == obj.getClass())) return false;
        CommandArgs other = (CommandArgs) obj;

        if (!getArgumentsList().equals(other.getArgumentsList()))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public String getArgumentsAsString() {
        StringBuilder argsAsString = new StringBuilder();
        for (String s : arguments) {
            argsAsString.append(s);
            argsAsString.append(", ");
        }
        return argsAsString.toString();
    }
}

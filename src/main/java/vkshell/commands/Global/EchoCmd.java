package vkshell.commands.Global;


import vkshell.commands.core.ACommand;
import vkshell.commands.core.AOption;
import vkshell.commands.core.Command;
import vkshell.commands.core.CommandArgs;

@ACommand(names = {"echo"}, desc = "Echoes back something")
public class EchoCmd extends Command {
    @AOption(names = {"uppercase", "cu", "up"}, desc = "Uppercases output")
    public boolean uppercase = false;

    @AOption(names = {"lowercase", "lc", "cl"}, desc = "Lowercases output")
    public boolean lowercase = false;

    @AOption(names = {"repeat", "rep"}, desc = "Repeats output")
    public int repeat = 1;

    @AOption(names = {"prefix"}, desc = "Prefix output")
    public String prefix = "";

    @AOption(names = {"suffix"}, desc = "Prefix output")
    public String suffix = "";

    @Override
    public void action(CommandArgs args) {
        for (String arg : args.getArgumentsList()) {
            if (uppercase) {
                arg = arg.toUpperCase();
            }
            if (lowercase) {
                arg = arg.toLowerCase();
            }
            for (int i = 0; i < repeat; ++i) {
                cli.out().println(prefix + arg + suffix);
            }
        }
    }
}

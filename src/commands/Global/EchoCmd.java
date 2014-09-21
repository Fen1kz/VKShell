package commands.Global;

import commands.core.ACommand;
import commands.core.AOption;
import commands.core.Command;
import commands.core.CommandArgs;

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

    @Override
    public void action(CommandArgs args) {
        for (String arg : args.getArgumentsList()) {
            arg = prefix + arg;

            if (uppercase) {
                arg = arg.toUpperCase();
            }
            if (lowercase) {
                arg = arg.toLowerCase();
            }
            for (int i = 0; i < repeat; ++i) {
                System.out.println(arg);
            }
        }
    }
}

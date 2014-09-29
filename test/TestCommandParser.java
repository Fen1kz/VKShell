import commands.DefaultMode.AuthCmd;
import commands.Global.EchoCmd;
import commands.core.CommandArgs;
import commands.core.CommandParser;
import commands.core.ICommand;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestCommandParser {

    //@Test
    public void testCommandEquality() {
        AuthCmd authCmd = new AuthCmd();
        ICommand authCmd2 = new AuthCmd();

        EchoCmd echoCmd = new EchoCmd();
        EchoCmd echoCmdWithOptions = new EchoCmd();
        echoCmdWithOptions.uppercase = true;

        EchoCmd echoCmdWithOptions2 = new EchoCmd();
        echoCmdWithOptions.verbose = true;
        EchoCmd echoCmdWithOptions3 = new EchoCmd();
        echoCmdWithOptions.verbose = true;

        assertThat(authCmd, is(not(null)));
        assertThat(authCmd, is(authCmd));
        assertThat(authCmd, is(authCmd2));
        assertThat(echoCmd,  is(not(authCmd2)));
        assertThat(echoCmd,  is(not(echoCmdWithOptions)));
        assertThat(echoCmdWithOptions2, is(echoCmdWithOptions3));
    }

    @Test
    public void testMini() {
        //System.out.println(CommandParser.parseCommand(EchoCmd.class, "-rep abc -up -prefixvrep 2 -up hai wrong arg, x_x ,,,pls no, -up hai\\, hai, tests tests"));
        //System.out.println(CommandParser.parseCommand(EchoCmd.class, "-rep abc -prefixvrep 2 -up hai hai, tests tests"));
        //System.out.println(CommandParser.parseCommand(EchoCmd.class, " -prefix ' omg \"' -up"));
        //System.out.println(CommandParser.parseCommand(EchoCmd.class, "ec -prefix 'hai: ' -up arg1, arg2"));
    }

    @Test
    public void testParser() {
        Assert.assertEquals(new AuthCmd(), CommandParser.parseCommand(AuthCmd.class, "").getCommand());

        EchoCmd echoCmd;

        echoCmd = new EchoCmd();
        echoCmd.uppercase = true;
        //Assert.assertNotEquals(echoCmd, CommandParser.parseCommand(EchoCmd.class, "").getCommand());
        Assert.assertEquals(echoCmd, CommandParser.parseCommand(EchoCmd.class, "-up").getCommand());

        echoCmd = new EchoCmd();
        echoCmd.uppercase = true;
        echoCmd.prefix = "2";
        echoCmd.repeat = 2;
        echoCmd.verbose = true;
        Assert.assertEquals(
                new CommandParser.ParsedCommand(echoCmd, new CommandArgs("hai hai", "tests tests"))
                , CommandParser.parseCommand(EchoCmd.class, "-rep abc -prefixvrep 2 -up hai hai, tests tests"));

    }


        /*
dl -path "D:\music" -threads 4 a/
dl -path 'D:\music' -threads 4 a/
vkshell auth qwe qwe | dl albums -l=50 -thr=3 T:\music\VKShell

- -- -s-k 123 asdf honor for al,,h, haiaha -44
-uplc -rep 4 - -- -s-s 123 asdf honor for al, ,,j,haiaha
asdf -uplc -rep 4 -s-s - -- 123 asdf honor for al, haiaha
Arctic_пеываThe_View_From_The_Afternoon-38841212.mp3
s -in -m4 ыArctic Mфываeys - The View From The Afternoon -38841212.mp3
-in -m4 Arctic Monkeys - The View From The Afternoo -heyhey sadf kekeke
        */

    //tests.writeLine("e -up -prefixvrep 2 -prefix 'kekeke: keke:' -unknown hai hai, tests tests");
    //tests.writeLine("1");
    //tests.writeLine("ec -rep abc -up -prefixvrep 2 -unknown hai hai, tests tests");
    //tests.writeLine("ec fen1kz 'myp,assword'");
}

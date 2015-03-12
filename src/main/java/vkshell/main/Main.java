package vkshell.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vkshell.commands.StartCmd;
import vkshell.shell.cmd.tools.interfaces.ICommandParser;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        CommandParser.parseCommand(
//                StartCmd.class,
//                Arrays.toString(args).replace(",", "").replace("[", "").replace("]", "")
//        ).execute();
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        ICommandParser commandParser = (ICommandParser) context.getBean("CommandParser");
        commandParser.parseCommand(
                StartCmd.class,
                Arrays.toString(args).replace(",", "").replace("[", "").replace("]", "")
        ).execute();
    }
}

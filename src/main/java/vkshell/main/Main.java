package vkshell.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import vkshell.appmodes.AppMode;
import vkshell.commands.DefaultMode.DefaultMode;
import vkshell.commands.StartCmd;
import vkshell.shell.cli.ICLI;
import vkshell.shell.cmd.tools.interfaces.ICommandParser;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(SpringConfig.class);
//                new ClassPathXmlApplicationContext("context.xml");
        AppMode mode = context.getBean("startMode", AppMode.class);
        mode.start();
//        ICommandParser commandParser = (ICommandParser) context.getBean("CommandParser");
//        commandParser.parseCommand(
//                StartCmd.class,
//                Arrays.toString(args).replace(",", "").replace("[", "").replace("]", "")
//        ).execute();
    }

//    @Autowired
//    protected ICLI cli;
//
//    public Main() {
////        System.out.println("Constructor");
//    }
}

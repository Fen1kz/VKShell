package test;

import app.App;
import main.cli.CLI;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class CLITest {
    @Test
    public void testCommand() {
        Scanner cookieScanner = null;
        try {
            cookieScanner = new Scanner(new FileReader(".test-emailpass"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String email = cookieScanner.next();
        String pass = cookieScanner.next();

        StringCommand test = new StringCommand();
        //tests.writeLine("auth "+email+","+pass);

        test.writeLine("audio");
        test.writeLine("list -l 3");

        CLI cli = new CLI(new ByteArrayInputStream(test.get().getBytes(StandardCharsets.UTF_8)));
        cli.echo = true;
        App.get().start(cli);
    }

    private class StringCommand {
        public String str;
        public StringCommand(){
            this.str = "";
        }
        public String get(){
            return str;
        }
        private String writeLine(String command) {
            str += command + "\n";
            return str;
        }
    }
}

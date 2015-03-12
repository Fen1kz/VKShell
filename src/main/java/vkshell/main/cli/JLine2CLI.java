package vkshell.main.cli;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class JLine2CLI implements ICLI {
    public boolean echo = false;
    private ConsoleReader console;

    private InputStream in;
    private PrintStream out;

    public JLine2CLI() {
        this(System.in, System.out);
    }

    public JLine2CLI(InputStream in) {
        this(in, System.out);
    }

    public JLine2CLI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        TerminalFactory.configure(TerminalFactory.UNIX); // for IDEA console o_O
        try {
            console = new ConsoleReader(in, out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInput() throws IOException {
        String inputline = console.readLine();

        if (echo) {
            console.println(">>" + inputline + "<<");
        }

        if (inputline == null) {
            System.exit(0);
        }
        return inputline.trim();
    }

    public PrintStream out() {
        return out;
    }

    @Override
    public ICLI setEcho(boolean flag) {
        echo = flag;
        return this;
    }
}
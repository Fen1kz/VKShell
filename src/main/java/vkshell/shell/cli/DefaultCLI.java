package vkshell.shell.cli;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DefaultCLI implements ICLI {
    public boolean echo = false;
    private BufferedReader console;

    private InputStream in;
    private PrintStream out;

    public DefaultCLI() {
        this(System.in, System.out);
    }

    public DefaultCLI(InputStream in) {
        this(in, System.out);
    }

    public DefaultCLI(InputStream in, PrintStream out) {
        this.in = in;
        this.out = out;
        console = new BufferedReader(new InputStreamReader(in));
    }

    public String getInput() throws IOException {
        String inputline = console.readLine();

        if (echo) {
            out.println(">>" + inputline + "<<");
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
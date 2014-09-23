package main.cli;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public interface ICLI {
    public String getInput() throws IOException;
    public PrintStream out();
}

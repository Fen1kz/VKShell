package vkshell.main.cli;

import java.io.IOException;
import java.io.PrintStream;

public interface ICLI {
    public String getInput() throws IOException;
    public PrintStream out();
    public ICLI setEcho(boolean flag);
}

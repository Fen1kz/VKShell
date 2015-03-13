package vkshell.appmodes;


import org.springframework.beans.factory.annotation.Autowired;
import vkshell.commands.core.ICommand;
import vkshell.commands.core.exceptions.UnknownCommandException;
import vkshell.main.cli.ICLI;
import vkshell.appmodes.interfaces.IAppModeWithCommands;
import vkshell.shell.cmd.tools.interfaces.ICommandParser;

import java.util.Map;
import java.util.SortedMap;

public class SelectCommandMode extends AppMode implements IAppModeWithCommands {
    protected SortedMap<String, Class<? extends ICommand>> commandMap;

    @Autowired
    protected ICommandParser commandParser;

    public SelectCommandMode(SortedMap<String, Class<? extends ICommand>> avaliableCommands) {
        this.commandMap = avaliableCommands;
    }

    @Override
    public SortedMap<String, Class<? extends ICommand>> getCommandMap() {
        return null;
    }

    @Override
    public void start() {}

    public String getPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Found multiple commands, choose one (type number or command): \n");
        int i = 0;
        for (Map.Entry<String, Class<? extends ICommand>> entry : commandMap.entrySet()) {
            ++i;
            sb.append(i + ") " + entry.getKey() + "\n");
        }
        return sb.toString();
    }

    public Class<? extends ICommand> getSelection() throws UnknownCommandException {
        String inputline = getInput();
        int intSelection = -1;
        try {
            Integer i = Integer.parseInt(inputline);
            intSelection = i - 1;
        } catch (NumberFormatException e) {
        }
        if (intSelection >= 0 && intSelection < commandMap.size()) {
            int i = 0;
            for (Map.Entry<String, Class<? extends ICommand>> entry : commandMap.entrySet()) {
                if (i == intSelection) {
                    return entry.getValue();
                }
                ++i;
            }
        }
        try {
            return commandParser.findCommandClass(this, inputline);
        } catch (UnknownCommandException e) {
            throw new UnknownCommandException(inputline);
        }
    }
}
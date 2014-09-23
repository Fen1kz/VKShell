package appmodes;

import commands.core.CommandParser;
import commands.core.ICommand;
import commands.core.exceptions.UnknownCommandException;
import main.cli.ICLI;
import appmodes.interfaces.IAppModeWithCommands;

import java.util.Map;
import java.util.SortedMap;

public class SelectCommandMode extends AppMode implements IAppModeWithCommands {
    protected SortedMap<String, Class<? extends ICommand>> commandMap;

    public SelectCommandMode(ICLI cli, SortedMap<String, Class<? extends ICommand>> avaliableCommands) {
        super(cli);
        this.commandMap = avaliableCommands;
    }

    @Override
    public SortedMap<String, Class<? extends ICommand>> getCommandMap() {
        return null;
    }

    @Override
    public void start() {

    }

    public String getPrompt() {
        StringBuilder sb = new StringBuilder();
        sb.append("Found multiple commands, choose one (type number or command): ");
        int i = 0;
        for (Map.Entry<String, Class<? extends ICommand>> entry : commandMap.entrySet()) {
            ++i;
            sb.append(i + ") " + entry.getKey());
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
            return CommandParser.findCommandClass(this, inputline);
        } catch (UnknownCommandException e) {
            throw new UnknownCommandException(inputline);
        }
    }
}
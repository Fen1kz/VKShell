package commands.core;

import appmodes.SelectCommandMode;
import appmodes.interfaces.IAppModeWithCommands;
import commands.core.exceptions.UnknownCommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandParser {
    @SuppressWarnings("MalformedRegex") // Intellij IDEA warning at \\k'q'
    public static final Matcher matcherOption = Pattern.compile("(?<=^|\\s+)(?<OPTION>-[\\wа-я\\?]+)(?<PARAMETER>=(?<q>[\"']).*\\k<q>|([^\\s])+)?").matcher("");
    public static final Matcher matcherArgs = Pattern.compile("(?<=[\\s,]++|^)([^\\s])+").matcher("");

    private static final Logger logger = LogManager.getLogger(CommandParser.class);
    private static final Marker FINDING_OPTION = MarkerManager.getMarker("OPTIONS");

    private CommandParser() {
    }

    public static ParsedCommand parseCommand(IAppModeWithCommands mode, String input) throws UnknownCommandException {
        ParsedData data = new ParsedData(input);
        Class<? extends ICommand> commandclass = findCommandClass(mode, data.commandname);
        return parseCommand(commandclass, data.inputline);
    }

    public static ParsedCommand parseCommand(Class<? extends ICommand> commandclass, final String inputline) {
        ParsedCommand parsedCommand = new ParsedCommand();
        parsedCommand.command = getCommandInstance(commandclass);
        ArrayList<String> arguments = new ArrayList<>();

        logger.trace("Parsing command <{}> with <{}>", commandclass, inputline);
        if (inputline != null) {
            StringBuilder rest = new StringBuilder(inputline.trim());

            // ===== ===== ===== Options
            SortedMap<String, Option> avaliableOptions = parsedCommand.command.getOptions();

            boolean option_found;
            do {
                logger.trace("Searching in <{}>", rest);
                matcherOption.reset(rest);
                option_found = matcherOption.find();
                if (option_found) {
                    logger.trace("Found group: <{}>: <{}>=<{}>", matcherOption.group(), matcherOption.group("OPTION"), matcherOption.group("PARAMETER"));
                    Collection<Option> optionsInSubstring = findOption(avaliableOptions, matcherOption.group("OPTION"));
                    logger.trace("Options found: " + optionsInSubstring);
                    if (optionsInSubstring.isEmpty()) {
                        logger.trace("Aboring");
                        break;
                    }

                    String parameter = matcherOption.group("PARAMETER") != null ? matcherOption.group("PARAMETER").trim().substring(1) : "true";
                    for (Option option : optionsInSubstring) {
                        assignOption(parsedCommand.command, option, parameter);
                    }

                    rest = rest.delete(0, matcherOption.end());
                }
            } while (option_found);
            // ===== ===== ===== Arguments
            logger.trace("Arguments string: <{}>", rest);

            matcherArgs.reset(rest);
            while (matcherArgs.find()) {
                logger.trace("Argument: " + matcherArgs.group().trim());
                arguments.add(matcherArgs.group().trim().replaceAll("\\\\,", ","));
            }
        }
        logger.trace("Arguments are: {}", arguments);
        parsedCommand.commandArgs = new CommandArgs(arguments);
        logger.trace("Command <{}> parsed\n", commandclass);
        return parsedCommand;
    }

    public static void assignOption(ICommand command, Option option, String parameter) {
        try {
            logger.trace("Assigned <{}> to <{}>", option.getName(), parameter);
            option.setFromString(command, parameter);
        } catch (IllegalArgumentException e) {
            logger.trace("<" + option.getName() + "> illegal arg <" + parameter + ">");
        }
    }

    private static Collection<Option> findOption(SortedMap<String, Option> options, String source) {
        Collection<Option> list = new HashSet<>();
        logger.trace(FINDING_OPTION, "Options avaliable: {}", options.keySet());
        for (Map.Entry<String, Option> entry : options.entrySet()) {
            if (source.contains(entry.getKey())) {
                logger.trace(FINDING_OPTION, "found option <" + entry.getKey() + ">");
                list.add(entry.getValue());
                source = source.replace(entry.getKey(), "");
                if (source.isEmpty()) {
                    break;
                }
            }
        }
        return list;
    }

    public static ICommand getCommandInstance(Class<? extends ICommand> commandclass) {
        ICommand cmd = null;
        try {
            cmd = commandclass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return cmd;
    }

    public static Class<? extends ICommand> findCommandClass(IAppModeWithCommands mode, String commandname) throws UnknownCommandException {
        if (mode.getCommandMap().containsKey(commandname)) {
            return mode.getCommandMap().get(commandname);
        }
        /* sweet Java 8 <3
        SortedMap<String, Class<? extends ICommand>> avaliableCommands =
                mode.getCommandMap().entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().startsWith(commandname))
                        //.sorted((entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey()))
                        .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue(), (class1, class2) -> class1, TreeMap::new));
                        */

        SortedMap<String, Class<? extends ICommand>> avaliableCommands = new TreeMap<>(mode.getCommandMap().comparator());
        for (Map.Entry<String, Class<? extends ICommand>> entry : mode.getCommandMap().entrySet()) {
            if (entry.getKey().startsWith(commandname)) {
                avaliableCommands.put(entry.getKey(), entry.getValue());
            }
        }

        logger.trace(avaliableCommands.getClass().getSimpleName() +":"+avaliableCommands);
        if (avaliableCommands.size() == 0) {
            throw new UnknownCommandException(commandname);
        } else if (avaliableCommands.size() == 1) {
            logger.trace("Found command: " + avaliableCommands.get(0));
            return avaliableCommands.get(avaliableCommands.firstKey());
        } else {
            return new SelectCommandMode(mode.getCLI(), avaliableCommands).getSelection();
        }
    }

    private static class ParsedData {
        private final String commandname;
        private final String inputline;

        private ParsedData(String inputline) {
            String[] inputarray = inputline.split(" ", 2);
            commandname = inputarray[0];
            this.inputline = (inputarray.length > 1) ? inputarray[1] : null;
        }
    }

    public static class ParsedCommand<T extends ICommand> {
        private T command = null;
        private CommandArgs commandArgs = null;

        public ParsedCommand() {
            commandArgs = new CommandArgs();
        }

        public ParsedCommand(T command, CommandArgs commandArgs) {
            this.command = command;
            this.commandArgs = commandArgs;
        }

        public boolean valid() {
            return command != null;
        }

        public void execute() {
            command.execute(commandArgs);
        }

        public T getCommand() {
            return command;
        }

        public CommandArgs getCommandArgs() {
            return commandArgs;
        }

        @Override
        public String toString() {
            return "<" + command.toString() + ">" + " with " + commandArgs.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            if (!(this.getClass() == obj.getClass())) return false;
            ParsedCommand other = (ParsedCommand) obj;
            if (!getCommand().equals(other.getCommand()))
                return false;
            if (!getCommandArgs().equals(other.getCommandArgs()))
                return false;
            return true;
        }
    }
}

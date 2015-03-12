package vkshell.commands.core;

import java.util.SortedMap;

public interface ICommand {
    /**
     * Command name
     *
     * @return String
     */
    public String getName();

    /**
     * Command name and aliases
     *
     * @return String
     */
    public String[] getNames();

    /**
     * Command description
     *
     * @return String
     */
    public String getDesc();

    /**
     * Execute this command
     */
    public void execute();

    /**
     * Execute this command
     *
     * @param args arguments object
     */
    public void execute(CommandArgs args);

    /**
     * Avaliable options
     *
     * @return SortedMap<String, Field>
     */
    public SortedMap<String, Option> getOptions();

    /**
     * Get option by string name
     *
     * @param name name of option
     * @param type
     * @return SortedMap<String, Field>
     */
    @Deprecated
    public <T> T getOption(String name, Class<T> type);
}

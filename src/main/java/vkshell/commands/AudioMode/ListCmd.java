package vkshell.commands.AudioMode;

import vkshell.api.exceptions.VKAPIException;
import vkshell.commands.core.ACommand;
import vkshell.commands.core.AOption;
import vkshell.commands.core.CommandArgs;
import vkshell.commands.core.VKApiCommand;
import org.json.JSONArray;
import org.json.JSONObject;

@ACommand(names = {"list"}, desc = "lists")
public class ListCmd extends VKApiCommand {
    @AOption(names = {"length", "l"}, desc = "Length of list")
    public int length = 10;

    @Override
    protected void vkAPIAction(CommandArgs args) throws VKAPIException {
        JSONObject jsonResponse = api.build("audio.get")
                .data("owner_id", userid)
                .data("count", length)
                .send();

        JSONArray list = jsonResponse.getJSONArray("response");
        cli.out().println("List of " + length + "/" + list.get(0));
        for (int i = 1; i < list.length(); ++i) {
            JSONObject item = list.getJSONObject(i);
            String artist = item.getString("artist");
            String title = item.getString("title");
            cli.out().println(artist + " - " + title);
        }
        //JSONArray jsonList = debug.getJSONArray();
    }
}

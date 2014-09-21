package commands.AudioMode;

import api.VkAPI;
import api.exceptions.VKAPIException;
import commands.core.ACommand;
import commands.core.AOption;
import commands.core.CommandArgs;
import commands.core.VKApiCommand;
import app.App;
import org.json.JSONArray;
import org.json.JSONObject;

@ACommand(names = {"list"}, desc = "lists")
public class ListCmd extends VKApiCommand {
    @AOption(names = {"length", "l"}, desc = "Length of list")
    public int length = 10;

    @Override
    protected void vkAPIAction(CommandArgs args) throws VKAPIException {
        JSONObject jsonResponse = VkAPI.build("audio.get")
                .data("owner_id", userid)
                .data("count", length)
                .send();

        JSONArray list = jsonResponse.getJSONArray("response");
        App.get().cli().out().println("List of " + length + "/" + list.get(0));
        for (int i = 1; i < list.length(); ++i) {
            JSONObject item = list.getJSONObject(i);
            String artist = item.getString("artist");
            String title = item.getString("title");
            App.get().cli().out().println(artist + " - " + title);
        }
        //JSONArray jsonList = debug.getJSONArray();
    }
}

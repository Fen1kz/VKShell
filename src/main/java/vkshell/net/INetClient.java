package vkshell.net;

import java.util.Map;

public interface INetClient {
    public String executePost(String targetURL, Map<String, String> parameters);

    public String executeGet(String targetURL, Map<String, String> parameters);
}

package vkshell.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import vkshell.api.auth.VkAccessToken;
import vkshell.api.exceptions.VKAPIException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import vkshell.app.Config;
import vkshell.commands.DefaultMode.AuthCmd;
import vkshell.shell.cli.ICLI;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class VkAPI {
    private static final Logger logger = LogManager.getLogger(VkAPI.class);

    public static final String URL = "https://api.vk.com/method/";
    public static final String VK_API_VERSION = "5.24";
    //https://vk.com/dev/auth_mobile
    public static final String AUTH_BASE_URI = "https://oauth.vk.com/authorize";
    public static final String AUTH_APP_ID = "4498629";
    public static final String AUTH_PERMISSIONS = VkAPIPermissions.AUTH_PERMISSIONS;
    public static final String AUTH_REDIRECT_URI = "https://oauth.vk.com/blank.html";
    public static final String AUTH_DISPLAY = "wap";
    public static final String AUTH_RESPONSE_TYPE = "token";

    @Autowired
    Config config;

    @Autowired
    ICLI cli;

    private VkAccessToken token;

    public String getUserId() {
        if (getToken().valid()) {
            return getToken().getUserId();
        } else {
            if (config.get(Config.Property.AUTH_AUTO_RETRY) && reauthorise()) {
                return getToken().getUserId();
            }
        }
        return null;
    }


    public VkAccessToken getToken() {
        if (token != null) {
            return token;
        }
        VkAccessToken token = new VkAccessToken(null, null, null);

        if (config.get(Config.Property.AUTH_TOKEN_FILE)) {
            try {
                File file = config.getFile(Config.Files.TOKEN_FILE);
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                try {
                    token = (VkAccessToken) stream.readObject();
                } finally {
                    stream.close();
                }
            } catch (ClassNotFoundException e) {
                logger.info(e);
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return token;
    }

    public boolean reauthorise() {
        new AuthCmd().execute();
        return (getToken().valid());
    }





    public JSONObject sendRequest(String methodName, Map<String, String> parameters) throws VKAPIException {
        return sendRequest(methodName, parameters, false);
    }

    public JSONObject sendRequest(String methodName, Map<String, String> parameters, boolean debug) throws VKAPIException {
        //https://api.vk.com/url/'''METHOD_NAME'''?'''PARAMETERS'''&access_token='''ACCESS_TOKEN'''
        Connection.Response response = null;
        try {
            response = Jsoup.connect(URL + methodName)
                    .data(parameters)
                    .data("access_token", getToken().getAccessToken())
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonResponse = new JSONObject(response.body());
        System.out.println("Response:\n" + jsonResponse + "\n");
        if (jsonResponse.has("response")) {
            return jsonResponse;
        } else {
            JSONObject error = jsonResponse.getJSONObject("error");
            throw new VKAPIException(error);
        }
    }

    public Request build (String methodName) {
        return new Request(methodName);
    }

    public static class Request {
        @Autowired
        VkAPI api;

        private final String methodName;
        private final Map<String, String> data = new HashMap<>();
        private boolean debug = false;

        public Request(String methodName) {
            this.methodName = methodName;
        }

        public Request debug () {
            debug = true;
            return this;
        }

        public Request data (String key, String value) {
            data.put(key, value);
            return this;
        }

        public Request data (String key, Object value) {
            return data(key, String.valueOf(value));
        }

        public JSONObject send() throws VKAPIException {
            return api.sendRequest(methodName, data, debug);
        }
    }
}

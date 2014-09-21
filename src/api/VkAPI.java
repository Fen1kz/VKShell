package api;

import api.exceptions.VKAPIException;
import app.App;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VkAPI {
    public static final String url = "https://api.vk.com/method/";


    public static JSONObject sendRequest(String methodName, Map<String, String> parameters) throws VKAPIException {
        return sendRequest(methodName, parameters, false);
    }

    public static JSONObject sendRequest(String methodName, Map<String, String> parameters, boolean debug) throws VKAPIException {
        //https://api.vk.com/url/'''METHOD_NAME'''?'''PARAMETERS'''&access_token='''ACCESS_TOKEN'''
        Connection.Response response = null;
        try {
            response = Jsoup.connect(url + methodName)
                    .data(parameters)
                    .data("access_token", App.get().getToken().getAccessToken())
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

    public static Request build (String methodName) {
        return new Request(methodName);
    }

    public static class Request {
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
            return sendRequest(methodName, data, debug);
        }
    }
}

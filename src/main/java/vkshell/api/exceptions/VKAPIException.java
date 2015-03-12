package vkshell.api.exceptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VKAPIException extends Exception {
    public final VkErrorCode error;
    public final Map<String, String> requestParams = new HashMap<>();
    public VKAPIException () {
        error = VkErrorCode.UNKNOWN;
    }
    public VKAPIException (JSONObject jsonError) {
        error = VkErrorCode.getError(jsonError.getInt("error_code"));
        JSONArray jsonRequestParams = jsonError.getJSONArray("request_params");
        for (int i=0; i <jsonRequestParams.length(); i++) {
            JSONObject param = jsonRequestParams.getJSONObject(i);
            requestParams.put(param.getString("key"), param.getString("value"));
        }
    }
}

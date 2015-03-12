package vkshell.net;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class URLUtils {
    public static Map<String, String> splitUrlParameters(String urlParameters) throws UnsupportedEncodingException {
        final Map<String, String> pairs_map = new LinkedHashMap<String, String>();
        if (urlParameters != null) {
            final String[] pairs = urlParameters.split("&");
            for (String pair : pairs) {
                final int idx = pair.indexOf("=");
                final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
                final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
                pairs_map.put(key, value);
            }
        }
        return pairs_map;
    }
}

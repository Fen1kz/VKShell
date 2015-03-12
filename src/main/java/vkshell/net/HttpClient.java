package vkshell.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

public class HttpClient implements INetClient {
    private static HttpClient instance = new HttpClient();

    private HttpClient() {
    }

    public static HttpClient get() {
        return instance;
    }

    public static String formatUrlParameters(Map<String, String> parameters) {
        if (parameters == null)
            return null;
        Iterator<Map.Entry<String, String>> it = parameters.entrySet().iterator();
        StringBuilder sb = new StringBuilder();
        try {
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                sb.append(entry.getKey() + '=' + URLEncoder.encode(entry.getValue(), "UTF-8"));
                if (it.hasNext()) {
                    sb.append('&');
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String executeGet(String targetURL, Map<String, String> parameters) {
        String urlParameters = formatUrlParameters(parameters);
        return executeRequest(targetURL + "?" + urlParameters, null, "GET");
    }

    public String executePost(String targetURL, Map<String, String> parameters) {
        return executeRequest(targetURL, parameters, "POST");
    }

    public String executeRequest(String targetURL, Map<String, String> parameters, String method) {
        URL url;
        HttpURLConnection connection = null;

        String urlParameters = formatUrlParameters(parameters);
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            if (method.equals("POST")) {
                connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));

                //Send request
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            }

            StringBuffer headers = new StringBuffer();
            String headerName = null;
            for (int i = 1; (headerName = connection.getHeaderFieldKey(i)) != null; i++) {
                String cookie = connection.getHeaderField(i);
                headers.append(i + ")" + headerName + '=' + cookie + '\n');
            }

            //Get Response

            BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\n');
            }
            rd.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

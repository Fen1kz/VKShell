package api.auth;

import java.io.Serializable;

public class VkAccessToken implements Serializable {
    private final String access_token;
    private final String expires_in;
    private final String user_id;

    public VkAccessToken(String access_token, String expires_in, String user_id) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.user_id = user_id;
    }

    public boolean valid() {
        return !(this.access_token == null || this.expires_in == null || this.user_id == null);
    }

    public String getAccessToken() {
        return access_token;
    }

    public String getExpiresIn() {
        return expires_in;
    }

    public String getUserId() {
        return user_id;
    }

    @Override
    public String toString() {
        return super.toString() + "{" + access_token + ", " + expires_in + ", " + user_id + "}";
    }
}

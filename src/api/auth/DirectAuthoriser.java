package api.auth;

import commands.DefaultMode.AuthCmd;
import commands.core.CommandParser;
import app.App;
import app.Config;
import app.Consts;
import net.URLUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DirectAuthoriser implements IAuthoriser {
    private boolean loginPerformed;
    private boolean authPerformed;
    public Map<String, String> loginCookies;

    private String username, password, app_id;
    private boolean debug, revoke;

    private static final Logger logger = LogManager.getLogger(IAuthoriser.class);

    private final Connection connection = Jsoup.connect(Consts.AUTH_BASE_URI)
            .data("scope", Consts.AUTH_PERMISSIONS)
            .data("redirect_uri", Consts.AUTH_REDIRECT_URI)
            .data("display", Consts.AUTH_DISPLAY)
            .data("v", Consts.VK_API_VERSION)
            .data("response_type", Consts.AUTH_RESPONSE_TYPE)
            .method(Connection.Method.GET);

    public DirectAuthoriser () {
        /*try {
            loginCookies = getLoginCookies();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public VkAccessToken authorise(CommandParser.ParsedCommand<AuthCmd> cmd) throws AuthoriseException, AuthoriseError {
        username = cmd.getCommand().clear ? null : cmd.getCommandArgs().get(0);
        password = cmd.getCommand().clear ? null : cmd.getCommandArgs().get(1);
        app_id = cmd.getCommand().appid;
        debug = cmd.getCommand().verbose;
        revoke = cmd.getCommand().revoke;

        // connection:
        connection.data("client_id", app_id);
        if (revoke) connection.data("revoke", "1");

        loginPerformed = !(username != null && password != null);
        authPerformed = false;
        try {
            VkAccessToken token = requestToken(connection.cookies(loginCookies));
            return token;
        } catch (IOException e) {
            throw new AuthoriseError("IOException @", e);
        }
    }

    private VkAccessToken login(Document document) throws AuthoriseError, AuthoriseException, IOException {
        debug("Trying to log in");
        loginPerformed = true;

        Element form = getForm(document);

        String action = form.attr("action");
        Elements formInputs = form.select("input[type=text],input[type=password],input[type=hidden]");

        Map<String, String> inputsForSend = new HashMap<String, String>();
        for (Element input : formInputs) {
            inputsForSend.put(input.attr("name"), input.val());
        }
        inputsForSend.put("email", username);
        inputsForSend.put("pass", password);

        Connection.Response loginConnection = Jsoup.connect(action)
                .data(inputsForSend)
                .method(Connection.Method.GET)
                .execute();

        /*
        System.out.println("LOGIN COOKIES:" + loginConnection.cookies());
        System.out.println("LOGIN URL:" + loginConnection.url());
        System.out.println("LOGIN HEADERS:" + loginConnection.headers());
        */

        //because DELETED is not reliable :>
        if (loginConnection.hasCookie("remixsid") && loginConnection.cookie("remixsid").length() > 20) {
            setLoginCookies(loginConnection.cookies());
            return requestToken(connection.cookies(loginConnection.cookies()));
        } else {
            String errormsg = loginConnection.parse().select(".service_msg").text();
            if (!errormsg.isEmpty()) {
                throw new AuthoriseException(errormsg);
            } else {
                throw new AuthoriseError("Wrong cookie", loginConnection);
            }
        }
    }

    private VkAccessToken authOrLogin(Connection.Response response) throws AuthoriseException, IOException {
        debug("Trying to auth OR Login <" + (loginPerformed ? "loginPerformed" : "") + ":" + (authPerformed ? "authPerformed" : "") + ">");
        Document document = response.parse();
        Element form = getForm(document);
        if (form.select("input[name=email]").isEmpty() && form.select("input[name=pass]").isEmpty()) {
            return auth(response);
        } else {
            if (!loginPerformed) {
                return login(document);
            } else {
                throw new AuthoriseError("Login performed, but still not logged in", response);
            }
        }
    }

    private VkAccessToken auth(Connection.Response response) throws AuthoriseException, IOException {
        debug("Trying to auth");
        authPerformed = true;
        Document document = response.parse();
        Element form = getForm(document);
        String actionAuth = form.attr("action");
        /*
        System.out.println("AUTH COOKIES:" + debug.cookies());
        System.out.println("LOGIN COOKIES:" + loginCookies);
        System.out.println("AUTH URL:" + debug.url());
        System.out.println("AUTH HEADERS:" + debug.headers());
        */
        HashMap<String, String> cookies = new HashMap<String, String>();
        cookies.putAll(response.cookies());
        cookies.putAll(loginCookies);

        Connection authConnection = Jsoup.connect(actionAuth)
                .method(Connection.Method.GET)
                .cookies(cookies);

        return requestToken(authConnection);
    }

    private VkAccessToken requestToken(Connection connection) throws AuthoriseException, IOException {
        debug("Trying to get token");
        Connection.Response response = connection.execute();
        if (response.url().getRef() != null) {
            debug("Token success");
            Map<String, String> params = null;
            try {
                params = URLUtils.splitUrlParameters(response.url().getRef());
            } catch (UnsupportedEncodingException e) {
                throw new AuthoriseError("UnsupportedEncodingException", e);
            }
            /*
            System.out.println("params:" + params);

            System.out.println("COOKIES:" + debug.cookies());
            System.out.println("URL:" + debug.url());
            System.out.println("HEADERS:" + debug.headers());
            */
            if (params.containsKey("access_token") && params.containsKey("expires_in") && params.containsKey("user_id")) {
                return new VkAccessToken(params.get("access_token"), params.get("expires_in"), params.get("user_id"));
            } else {
                throw new AuthoriseError("Wrong Parameters @Ref", params);
            }
        } else if (!authPerformed) {
            debug("Token fail");
            return authOrLogin(response);
        }
        throw new AuthoriseError("Token Request failed");
    }

    private Element getForm(Document document) throws AuthoriseError {
        Elements forms = document.select("form");
        if (forms.size() != 1) {
            throw new AuthoriseError("Form parsing failed", forms);
        }
        return forms.get(0);
    }

    private Map<String, String> getLoginCookies() throws IOException {
        HashMap<String, String> cookies = new HashMap<>();
        if (App.get().config().COOKIE_READ) {
            try {
                File file = App.get().config().getFile(Config.Files.COOKIE_FILE);
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                try {
                    cookies = (HashMap<String, String>) stream.readObject();
                } finally {
                    stream.close();
                }
            } catch (EOFException e) {
                // ignore
            } catch (ClassNotFoundException e) {
                // ignore
            } catch (IllegalStateException e) {
                logger.error(e);
            }
        }
        return cookies;
    }

    private void setLoginCookies(Map<String, String> cookies) throws IOException {
        loginCookies = cookies;
        if (App.get().config().COOKIE_WRITE) {
            try {
                File file = App.get().config().createFile(Config.Files.COOKIE_FILE);
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
                try {
                    stream.writeObject(cookies);
                } finally {
                    stream.close();
                }
            } catch (IllegalStateException e) {
                logger.error(e);
            }
        }
    }

    private void debug(String string) {
        if (debug) {
            logger.debug(string);
        }
    }
}
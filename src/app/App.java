package app;

import api.auth.DirectAuthoriser;
import api.auth.FakeAuthoriser;
import api.auth.IAuthoriser;
import api.auth.VkAccessToken;
import commands.DefaultMode.AuthCmd;
import commands.core.CommandParser;
import main.CLI;
import appmodes.DefaultMode;

import java.io.*;

public class App {
    public static enum Authoriser {
        DIRECT(new DirectAuthoriser()),
        FAKE(new FakeAuthoriser());

        private final IAuthoriser auth;

        private Authoriser(IAuthoriser auth) {
            this.auth = auth;
        }

        public IAuthoriser get() {
            return this.auth;
        }
    }

    // Singleton
    private static App instance = new App();
    private CLI cli;
    private VkAccessToken token = null;
    private Logger logger;
    private Config config;

    private App() {
        this.logger = new Logger();
        this.config = new Config();
    }

    // misc

    public static App get() {
        return instance;
    }

    public CLI cli() {
        return cli;
    }

    public Logger logger() {
        return logger;
    }

    public Config config() {
        return config;
    }

    public void start(CLI cli) {
        this.cli = cli;
        new DefaultMode(cli).start();
    }

    public VkAccessToken getToken() {
        if (token != null) {
            return token;
        }
        VkAccessToken token = new VkAccessToken(null, null, null);

        if (config().TOKEN_READ) {
            try {
                File file = config().getFile(Config.Files.TOKEN_FILE);
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));
                try {
                    token = (VkAccessToken) stream.readObject();
                } finally {
                    stream.close();
                }

            } catch (EOFException e) {
                // ignore
            } catch (ClassNotFoundException e) {
                // ignore
            } catch (IOException e) {
                App.get().logger().println(e.getMessage(), Logger.Level.ERROR);
            }
        }
        return token;
    }

    // auth >

    public void setToken(VkAccessToken token) {
        this.token = token;
        if (config().TOKEN_WRITE) {
            try {
                File file = config().createFile(Config.Files.TOKEN_FILE);
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));
                try {
                    stream.writeObject(token);
                } finally {
                    stream.close();
                }
            } catch (IOException e) {
                App.get().logger().println(e.getMessage(), Logger.Level.ERROR);
            } catch (IllegalStateException e) {
                App.get().logger().println(e.getMessage(), Logger.Level.ERROR);
            }
        }
    }

    public void authorise(Authoriser authoriser, CommandParser.ParsedCommand<AuthCmd> data) {
        VkAccessToken token = null;
        try {
            token = authoriser.get().authorise(data);
        } catch (IAuthoriser.AuthoriseException e) {
            System.out.println(e.toString());
        } catch (IAuthoriser.AuthoriseError e) {
            e.printStackTrace();
        }
        if (token != null) {
            setToken(token);
            System.out.println("Authorised, token: " + token);
        }
    }

    public String getUserId() {
        if (getToken().valid()) {
            return getToken().getUserId();
        } else {
            if (reauthorise()) {
                return getToken().getUserId();
            }
        }
        return null;
    }

    public boolean reauthorise() {
        cli().out().println("Trying to reauthorise");
        if (config().AUTH_AUTO) {
            new AuthCmd().execute();
            return (getToken().valid());
        }
        return false;
    }
}

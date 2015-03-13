//package vkshell.main;
//
//import vkshell.api.auth.DirectAuthoriser;
//import vkshell.api.auth.FakeAuthoriser;
//import vkshell.api.auth.IAuthoriser;
//import vkshell.api.auth.VkAccessToken;
//import vkshell.commands.DefaultMode.AuthCmd;
//import vkshell.commands.Global.EchoCmd;
//
//import vkshell.commands.DefaultMode.DefaultMode;
//import vkshell.main.cli.ICLI;
//
//import java.io.*;
//
//public class App {
//    // Singleton
//    private static App instance = new App();
//    private ICLI cli;
//    private VkAccessToken token = null;
//
//    private App() {
//    }
//
//    // misc
//
//    public static App get() {
//        return instance;
//    }
//
//    public void start(ICLI cli) {
//        this.cli = cli;
//    }
//
//    public ICLI getCLI() {
//        return cli;
//    }
//
//    public VkAccessToken getToken() {
//        if (token != null) {
//            return token;
//        }
//        VkAccessToken token = new VkAccessToken(null, null, null);
//
//        File file = null;
//        try {
//            file = Config.get().getTokenFile();
//        } catch (IOException e) {
//            // ignore
//        }
//        if (file != null) {
//            //http://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
//            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file));) {
//                token = (VkAccessToken) stream.readObject();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                // ignore
//            }
//        }
//        return token;
//    }
//
//    // auth >
//
//    public void setToken(VkAccessToken token) {
//        this.token = token;
//        try {
//            File file = Config.get().getTokenFile();
//            ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file));
//            s.writeObject(token);
//            s.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void authorise(Authoriser authoriser, CommandParser.ParsedCommand<AuthCmd> data) {
//        VkAccessToken token = null;
//        try {
//            token = authoriser.get().authorise(data);
//        } catch (IAuthoriser.AuthoriseException e) {
//            System.out.println(e.toString());
//        } catch (IAuthoriser.AuthoriseError e) {
//            e.printStackTrace();
//        }
//        if (token != null) {
//            setToken(token);
//            System.out.println("Authorised, token: " + token);
//        }
//    }
//
//    public String getUserId() {
//        if (getToken().valid()) {
//            return getToken().getUserId();
//        } else {
//            if (Config.get().autoAuth) {
//                new EchoCmd().execute();
//                if (getToken().valid()) {
//                    return getToken().getUserId();
//                }
//            }
//        }
//        return null;
//    }
//
//    public static enum Authoriser {
//        DIRECT(new DirectAuthoriser()),
//        FAKE(new FakeAuthoriser());
//
//        private final IAuthoriser auth;
//
//        private Authoriser(IAuthoriser auth) {
//            this.auth = auth;
//        }
//
//        public IAuthoriser get() {
//            return this.auth;
//        }
//    }
//}

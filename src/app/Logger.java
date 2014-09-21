package app;

public class Logger {

    public static enum Level {
        DEBUG(0),
        TRACE(1),
        INFO(2),
        WARNING(3),
        ERROR(4),
        UNKNOWN(-1);
        private final int level;

        public int getLevel() {
            return level;
        }

        public static Level getByInt (int i) {
            return (i >= 0 && i < values().length) ? values()[i] : UNKNOWN;
        }

        private Level(int level) {
            this.level = level;
        }
    }

    public void print(String msg) {
        print(msg, Level.getByInt(App.get().config().getInt(Config.key.LOG_LEVEL_DEFAULT)));
    }

    public void print(String msg, Level level) {
        if (level.getLevel() >= App.get().config().getInt(Config.key.LOG_LEVEL)) {
            App.get().cli().out().print(msg);
        }
    }

    public void println(String msg) {
        println(msg, Level.getByInt(App.get().config().getInt(Config.key.LOG_LEVEL_DEFAULT)));
    }

    public void println(String msg, Level level) {
        if (level.getLevel() >= App.get().config().getInt(Config.key.LOG_LEVEL)) {
            App.get().cli().out().print(msg);
        }
    }

    public void log(String msg) {
        log(msg, Level.getByInt(App.get().config().getInt(Config.key.LOG_LEVEL_DEFAULT)));
    }

    public void log(String msg, Level level) {
        App.get().config().getFile(Config.key.LOG_FILE);
    }
}

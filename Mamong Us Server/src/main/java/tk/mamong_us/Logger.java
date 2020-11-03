package tk.mamong_us;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static final int INFO = 0;
    public static final int ERROR = 2;
    public static final int WARN = 1;
    public static final int FATAL = 3;
    public static final int DEBUG = 4;

    public static final int BOTH = 0;
    public static final int ONLY_CONSOLE = 1;
    public static final int ONLY_FILE = -1;

    private static PrintStream out;

    public static void log(int kind, String message, int loc, boolean timestamp) {
        StringBuilder msg = new StringBuilder().append('[');
        msg.append(switch (kind) {
            case INFO -> "INFO";
            case ERROR -> "ERROR";
            case WARN -> "WARN";
            case FATAL -> "FATAL";
            case DEBUG -> "DEBUG";
            default -> "N/A";
        });
        msg.append(" | ");
        if (timestamp) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            msg.append(dtf.format(now));
            msg.append("] ");
        }
        msg.append(message);
        if (loc >= 0)
            System.out.println(msg.toString());
        if (loc <= 0)
            out.println(msg.toString());
    }

    public static void log(int kind, String message) {
        log(kind, message, 0, true);
    }

    public static void log(String message) {
        log(INFO, message, 0, true);
    }

    public static void setStream(PrintStream out) {
        Logger.out = out;
    }

    public static PrintStream getStream() {
        return out;
    }
}

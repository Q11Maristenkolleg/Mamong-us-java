package tk.q11mc.chat;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class OutputChat {
    public static final int LINES = 10;
    public static final int MAX_BUFFER = 60;

    private static String[] lines = new String[LINES];
    private static StringBuilder text = new StringBuilder();

    private static int buffer = MAX_BUFFER;

    public static void update() {
        if (buffer > 0) {
            buffer--;
            return;
        }
        buffer = MAX_BUFFER;

        text = new StringBuilder();
        for (String s : lines) {
            if (s == null) {
                text.append('\n');
                continue;
            }
            text.append(s).append('\n');
        }
        String[] temp = new String[lines.length];
        System.arraycopy(lines, 1, temp, 0, lines.length-1);
        lines = temp;
    }

    @Contract(pure = true)
    public static @NotNull String text() {
        return text.toString();
    }

    public static void add(String s) {
        if (lines[0] != null) {
            String[] temp = new String[lines.length];
            System.arraycopy(lines, 1, temp, 0, lines.length-1);
            lines = temp;
        }
        lines[LINES-1] = s;
    }
}

package tk.mamong_us.game;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MamongUsGame {
    public static String optionText = null;
    public static GameVariables vars = null;

    public static boolean impostor = false;
    public static ArrayList<Task> tasks = null;

    public static @NotNull String taskText() {
        if (tasks == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            sb.append(task.getType().name);
            if (task.getType().steps > 1) {
                sb.append(" (").append(task.getCompleted()).append('/').append(task.getType().steps).append(')');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}

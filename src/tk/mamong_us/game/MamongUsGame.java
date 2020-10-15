package tk.mamong_us.game;

import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tk.mamong_us.Main;
import tk.mamong_us.gui.Button;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.OtherPlayer;

import java.util.ArrayList;

public class MamongUsGame {
    private static final ImageTile arrowLeft = new ImageTile("/arrow_left.png", 24, 24);
    private static final ImageTile arrowRight = new ImageTile("/arrow_right.png", 24, 24);

    public static String optionText = null;
    public static GameVariables vars = null;

    public static boolean impostor = false;
    public static ArrayList<OtherPlayer> mates = new ArrayList<>();
    public static ArrayList<Task> tasks = null;

    public static Button[] configButtons = new Button[32];

    static {
        for (int i=0; i<configButtons.length; i+=2) {
            configButtons[i] = new Button(Main.getInstance(), arrowLeft, 75, 146+(i/2)*42, 24, 24, createConfigAction(i/2, true));
            configButtons[i+1] = new Button(Main.getInstance(), arrowRight, 575, 146+(i/2)*42, 24, 24, createConfigAction(i/2, false));
        }
    }

    @Contract(pure = true)
    private static @NotNull Runnable createConfigAction(int i, boolean left) {
        return () -> Multiplayer.send("config "+i+" "+left);
    }

    public static @NotNull String taskText() {
        if (tasks == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (impostor) {
            sb.append("Fake ");
        }
        sb.append("Tasks:\n");
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

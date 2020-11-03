package tk.mamong_us;

import com.siinus.server.JavaServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tk.mamong_us.game.MamongUsGame;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static JavaServer server;
    public static HashMap<String, String> names = new HashMap<>();
    public static List<String> operators = new ArrayList<>();

    static {
        operators.add("127.0.0.1");
    }

    public static boolean allowJoin = false;

    @Nullable public static MamongUsGame game = null;

    public static void main(String @NotNull [] args) throws IOException {
        PrintStream log = new PrintStream("./latest.log");
        Logger.setStream(log);
        System.setErr(Logger.getStream());
        server = new JavaServer();
        server.start(Integer.parseInt(args[0]));
        JavaServer.setHandler(new Protocol());
        new Console().start();
        JSONObject root = FileIO.loadJSON("./operators.json");
        if (root != null) {
            Object data = root.get("operators");
            if (data instanceof JSONArray) {
                for (Object o : (JSONArray) data) {
                    if (o instanceof String && !operators.contains(o)) {
                        operators.add((String) o);
                    }
                }
            }
        }
    }
}

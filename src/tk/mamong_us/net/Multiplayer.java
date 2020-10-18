package tk.mamong_us.net;

import com.siinus.client.JavaClient;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.PlayerSprite;
import tk.mamong_us.objects.OtherPlayer;

import java.io.IOException;
import java.util.HashMap;

public class Multiplayer {
    static JavaClient client;
    static String ip = null;
    static HashMap<String, String> names = new HashMap<>();
    static HashMap<String, OtherPlayer> players = new HashMap<>();

    public static boolean connect(String hostname, int port) {
        client = new JavaClient();
        try {
            client.connect(hostname, port);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        JavaClient.setHandler(new Protocol());
        return true;
    }

    public static void send(String message) {
        if (client != null) {
            client.send(message);
        }
    }

    public static double getPing() {
        return client.getPing();
    }

    public static void disconnect() {
        send("disconnect");
        if (client != null) {
            client.disconnect();
            client.getThread().interrupt();
            client = null;
        }
    }

    public static void spawnPlayer(String ip) {
        if (!Multiplayer.ip.equals(ip) && names.containsKey(ip) && !players.containsKey(ip)) {
            OtherPlayer p = PlayerSprite.ORANGE.getNewOtherPlayer(Main.getProgram(), ip, names.get(ip));
            p.register(GameState.MULTIPLAYER);
            players.put(ip, p);
        }
    }
}

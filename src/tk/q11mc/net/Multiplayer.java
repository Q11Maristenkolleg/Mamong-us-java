package tk.q11mc.net;

import com.siinus.client.JavaClient;
import tk.q11mc.Main;
import tk.q11mc.PlayerSprite;
import tk.q11mc.objects.OtherPlayer;

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
        try {
            client.send(message);
        } catch (NullPointerException ignored) { }
    }

    public static double getPing() {
        return client.getPing();
    }

    public static void disconnect() {
        send("disconnect");
        client.disconnect();
        client = null;
    }

    public static void spawnPlayer(String ip) {
        if (!Multiplayer.ip.equals(ip) && names.containsKey(ip) && !players.containsKey(ip)) {
            OtherPlayer p = PlayerSprite.ORANGE.getNewOtherPlayer(Main.getInstance(), names.get(ip));
            players.put(ip, p);
        }
    }
}

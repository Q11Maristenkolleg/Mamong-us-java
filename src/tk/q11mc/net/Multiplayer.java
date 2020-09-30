package tk.q11mc.net;

import com.siinus.client.JavaClient;
import tk.q11mc.Main;
import tk.q11mc.objects.OtherPlayer;

import java.io.IOException;
import java.util.HashMap;

public class Multiplayer {
    static JavaClient client;
    static String ip = null;
    static HashMap<String, String> names = new HashMap<>();
    static HashMap<String, OtherPlayer> players = new HashMap<>();

    public static void connect(String hostname, int port) {
        client = new JavaClient();
        try {
            client.connect(hostname, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaClient.setProtocol(new Protocol());
    }

    public static void send(String message) {
        client.send(message);
    }

    public static double getPing() {
        return client.getPing();
    }

    public static void disconnect() {
        client.disconnect();
        client = null;
    }

    public static void spawnPlayer(String ip) {
        if (compareIP(ip) && !names.containsKey(ip) && !players.containsKey(ip)) {
            OtherPlayer p = new OtherPlayer(Main.getInstance(), Main.spritePlayer, names.get(ip));
            players.put(ip, p);
        }
    }

    public static boolean compareIP(String ip) {
        return Multiplayer.ip != null && !Multiplayer.ip.equals(ip);
    }
}

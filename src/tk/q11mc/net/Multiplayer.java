package tk.q11mc.net;

import com.siinus.client.JavaClient;
import tk.q11mc.GameState;
import tk.q11mc.Main;
import tk.q11mc.objects.OtherPlayer;

import java.io.IOException;
import java.util.HashMap;

public class Multiplayer {
    static JavaClient client;
    static String ip = "127.0.0.1";
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
        JavaClient.setProtocol(new Protocol());
        return true;
    }

    public static void send(String message) {
        client.send(message);
    }

    public static double getPing() {
        return client.getPing();
    }

    public static void disconnect() {
        Main.gameState = GameState.MAIN_MENU;
        destroyPlayer(ip);
        if (client != null) {
            client.disconnect();
        }
        client = null;
    }

    public static void spawnPlayer(String ip) {
        System.out.println(" ----- SP ----- ");
        if (!compareIP(ip)) {
            System.out.println("CIP");
            return;
        }
        if (!names.containsKey(ip)) {
            System.out.println("Names");
            return;
        }
        if (players.containsKey(ip)) {
            System.out.println("Players");
            return;
        }
        //if (compareIP(ip) && !names.containsKey(ip) && !players.containsKey(ip)) {
        System.out.println("Spawn");
        OtherPlayer p = new OtherPlayer(Main.getInstance(), Main.spritePlayer, names.get(ip));
        players.put(ip, p);
        //}
    }

    public static void destroyPlayer(String ip) {
        players.remove(ip);
    }

    public static boolean compareIP(String ip) {
        //System.out.println("Your ip is: "+Multiplayer.ip);
        return Multiplayer.ip != null && !Multiplayer.ip.equals(ip);
    }
}

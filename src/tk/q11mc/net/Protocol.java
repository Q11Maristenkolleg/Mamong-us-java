package tk.q11mc.net;

import com.siinus.client.ClientProtocol;
import tk.q11mc.GameState;
import tk.q11mc.Main;

import java.util.Arrays;

public class Protocol implements ClientProtocol {

    @Override
    public String processInput(String s) {
        //System.out.println("----------------------------");
        if (s.equals("null")) {
            return "";
        }
        String[] msg = s.split(" ");

        switch (msg[1]) {
            case "name" -> {
                Multiplayer.ip = msg[0];
                Main.gameState = GameState.MULTIPLAYER;
            }
            case "connect" -> {
                if (!Multiplayer.names.containsKey(msg[0])) {
                    Multiplayer.names.put(msg[0], msg[2]);
                } /*else {
                    Multiplayer.disconnect();
                }*/
                System.out.println("Connect");
                Multiplayer.spawnPlayer(msg[0]);
            }
            case "pos" -> {
                if (Multiplayer.compareIP(msg[0])) {
                    Multiplayer.players.get(msg[0]).setX(Integer.parseInt(msg[2]));
                    Multiplayer.players.get(msg[0]).setY(Integer.parseInt(msg[3]));
                }
            }
            default -> {
                return "Unexpected value";
            }
        }
        return "+ "+s;
    }
}

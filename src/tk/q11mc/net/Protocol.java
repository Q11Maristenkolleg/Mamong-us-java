package tk.q11mc.net;

import com.siinus.client.ClientProtocol;

public class Protocol implements ClientProtocol {

    @Override
    public String processInput(String s) {
        if (s.equals("null")) {
            return "";
        }
        String[] msg = s.split(" ");

        switch (msg[1]) {
            case "name" -> Multiplayer.ip = msg[0];
            case "connect" -> {
                if (!Multiplayer.names.containsKey(msg[0])) {
                    Multiplayer.names.put(msg[0], msg[2]);
                } else {
                    Multiplayer.disconnect();
                }
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
        return s;
    }
}

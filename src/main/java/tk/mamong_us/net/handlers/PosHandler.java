package tk.mamong_us.net.handlers;

import tk.mamong_us.net.Multiplayer;

public class PosHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 4 && Multiplayer.players.containsKey(ip)) {
            Multiplayer.players.get(ip).setX(Integer.parseInt(msg[2]));
            Multiplayer.players.get(ip).setY(Integer.parseInt(msg[3]));
        }
    }
}

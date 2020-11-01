package tk.mamong_us.net.handlers;

import tk.mamong_us.net.Multiplayer;

public class FaceHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 3 && Multiplayer.players.containsKey(ip)) {
            Multiplayer.players.get(ip).setLeft(msg[2].equals("left"));
        }
    }
}

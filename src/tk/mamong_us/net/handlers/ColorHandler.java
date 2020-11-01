package tk.mamong_us.net.handlers;

import tk.mamong_us.PlayerSprite;
import tk.mamong_us.net.Multiplayer;

public class ColorHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 3 && Multiplayer.players.containsKey(ip)) {
            Multiplayer.players.get(ip).setSprite(PlayerSprite.valueOf(msg[2]));
        }
    }
}

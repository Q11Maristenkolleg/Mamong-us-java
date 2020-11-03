package tk.mamong_us.net.handlers;

import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.OtherPlayer;

import java.util.ArrayList;

public class MatesHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (msg.length >= 3) {
            ArrayList<OtherPlayer> mates = new ArrayList<>();
            for (int i = 2; i < msg.length; i++) {
                if (msg[i].equals(ip)) continue;
                mates.add(Multiplayer.players.get(msg[i]));
            }
            MamongUsGame.mates = mates;
        }
    }
}

package tk.mamong_us.net.handlers;

import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.objects.Shhh;

public class ImpostorHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (msg.length >= 3) {
            MamongUsGame.impostor = Boolean.parseBoolean(msg[2]);
            Shhh.shhh(MamongUsGame.impostor, MamongUsGame.mates);
        }
    }
}

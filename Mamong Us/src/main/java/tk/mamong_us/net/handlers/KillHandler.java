package tk.mamong_us.net.handlers;

import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.Kill;

public class KillHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (msg.length >= 3 && Multiplayer.ip.equals(msg[2])) {
            Kill.getKilled(ip);
        }
    }
}

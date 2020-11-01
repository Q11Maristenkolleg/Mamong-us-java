package tk.mamong_us.net.handlers;

import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.net.Multiplayer;

public class DisconnectHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (!Multiplayer.ip.equals(ip)) {
            OutputChat.add(Multiplayer.names.get(ip) + " left the game!");
            Multiplayer.names.remove(ip);
            Multiplayer.players.get(ip).destroy();
            Multiplayer.players.remove(ip);
        }
    }
}

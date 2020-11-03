package tk.mamong_us.net.handlers;

import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.net.Multiplayer;

public class ConnectHandler implements Handler{
    @Override
    public void handle(String ip, String[] msg) {
        if (msg.length >= 3) {
            StringBuilder playerName = new StringBuilder();
            for (int i=2; i<msg.length; i++) {
                playerName.append(msg[i]).append(' ');
            }
            OutputChat.add(playerName.toString().trim() + " joined the game!");
            if (!Multiplayer.names.containsKey(ip)) {
                Multiplayer.names.put(ip, playerName.toString().trim());
                Multiplayer.spawnPlayer(ip);
            }
        }
    }
}

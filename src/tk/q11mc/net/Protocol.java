package tk.q11mc.net;

import com.siinus.client.ClientHandler;
import org.jetbrains.annotations.NotNull;
import tk.q11mc.chat.OutputChat;

public class Protocol implements ClientHandler {

    @Override
    public void onConnect(String s) {
        System.out.println("--- IP: " + s + " ---");
        Multiplayer.ip = s;
    }

    @Override
    public synchronized String processInput(@NotNull String s) {
        String[] msg = s.split(" ");
        String ip = msg[0];
        if (msg.length<2) {

        } else {

            String task = msg[1];

            switch (task) {
                case "connect" -> {
                    if (msg.length >= 3) {
                        OutputChat.add(msg[2] + " joined the game!");
                        if (!Multiplayer.names.containsKey(ip)) {
                            Multiplayer.names.put(ip, msg[2]);
                            Multiplayer.spawnPlayer(ip);
                        }
                    }
                }
                case "disconnect" -> {
                    if (!Multiplayer.ip.equals(ip)) {
                        OutputChat.add(Multiplayer.names.get(ip) + " left the game!");
                        Multiplayer.names.remove(ip);
                        Multiplayer.players.get(ip).destroy();
                        Multiplayer.players.remove(ip);
                    }
                }
                case "pos" -> {
                    if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 4) {
                        Multiplayer.players.get(ip).frameUp();
                        Multiplayer.players.get(ip).setX(Integer.parseInt(msg[2]));
                        Multiplayer.players.get(ip).setY(Integer.parseInt(msg[3]));
                    }
                }
                case "move" -> {
                    if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 3) {
                        Multiplayer.players.get(ip).setMoving(msg[2].equals("start"));
                    }
                }
                case "face" -> {
                    if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 3) {
                        Multiplayer.players.get(ip).setLeft(msg[2].equals("left"));
                    }
                }
            }

        }

        return "+ "+s;
    }

    @Override
    public void handleException(@NotNull Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onDisconnect() {
        disconnect();
    }

    public static void disconnect() {
        Multiplayer.disconnect();
    }
}

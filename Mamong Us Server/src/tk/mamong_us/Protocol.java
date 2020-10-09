package tk.mamong_us;

import com.siinus.server.ServerChannel;
import com.siinus.server.ServerHandler;
import org.jetbrains.annotations.NotNull;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.game.PlayerData;

import java.net.SocketException;

public class Protocol implements ServerHandler {

    @Override
    public void onConnect(@NotNull ServerChannel serverChannel) {

    }

    @Override
    public synchronized String processInput(@NotNull ServerChannel serverChannel, @NotNull String s) {
        String r = null;

        String[] msg = s.split(" ");

        String task = msg[0];

        switch (task) {
            case "connect" -> {
                Main.names.remove(serverChannel.getName());
                Main.names.put(serverChannel.getName(), msg[1]);
                for (String other : Main.names.keySet()) {
                    if (serverChannel.getName().equals(other)) {
                        continue;
                    }
                    Main.server.send(serverChannel, other + " connect " + Main.names.get(other));
                }
            }
            case "start" -> {
                if (Main.operators.contains(serverChannel.getName())) {
                    if (Main.game == null) {
                        Main.game = MamongUsGame.startNewGame();
                        for (String n : Main.names.keySet()) {
                            PlayerData data = Main.game.addPlayer(n);
                            System.out.println(data);
                            for (ServerChannel channel : Main.server.getChannels()) {
                                if (channel.getName().equals(serverChannel.getName())) {
                                    Main.server.send(channel, serverChannel.getName()+" impostor "+data.isImpostor());
                                }
                            }
                        }
                    }
                }
            }
            case "stop" -> {
                if (Main.operators.contains(serverChannel.getName())) {
                    if (Main.game != null) {
                        Main.game = null;
                        Main.server.broadcast("stop");
                    }
                }
            }
            case "disconnect" -> disconnect(serverChannel);
        }

        Main.server.broadcast(serverChannel.getName() + " " + s);
        return r;
    }

    @Override
    public void handleException(@NotNull ServerChannel serverChannel, @NotNull Throwable throwable) {
        if (throwable instanceof SocketException && throwable.getMessage().equals("Connection reset")) {
            disconnect(serverChannel);
            return;
        }
        System.out.println("--- " + serverChannel.getName() + ": EXCEPTION ---");
        throwable.printStackTrace();
    }

    @Override
    public void onDisconnect(@NotNull ServerChannel serverChannel) {
        disconnect(serverChannel);
    }

    public static void disconnect(@NotNull ServerChannel serverChannel) {
        System.out.println(serverChannel.getName()+" has disconnected!");
        Main.server.broadcast(serverChannel.getName()+" disconnect");
        Main.names.remove(serverChannel.getName());
    }
}

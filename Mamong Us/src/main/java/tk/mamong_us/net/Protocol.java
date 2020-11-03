package tk.mamong_us.net;

import com.siinus.client.ClientHandler;
import org.jetbrains.annotations.NotNull;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.PlayerSprite;
import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.game.GameVariables;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.game.Task;
import tk.mamong_us.net.handlers.ConnectHandler;
import tk.mamong_us.net.handlers.Handlers;
import tk.mamong_us.objects.Kill;
import tk.mamong_us.objects.OtherPlayer;
import tk.mamong_us.objects.Shhh;

import java.net.SocketException;
import java.util.ArrayList;

public class Protocol implements ClientHandler {

    @Override
    public void onConnect(String s) {
        System.out.println("--- IP: " + s + " ---");
        Multiplayer.ip = s;
        Main.mpState = GameState.MultiplayerState.LOBBY;
    }

    @Override
    public synchronized String processInput(@NotNull String s) {
        String[] msg = s.split(" ");
        String ip = msg[0];
        if (msg.length<2) {
            return "[SERVER] Message too short: "+s;
        } else {
            String task = msg[1];

            try {
                Handlers.valueOf(task.toUpperCase()).handle(ip, msg);
            } catch (IllegalArgumentException e) {
                return "[SERVER] Task not found: "+s;
            }
        }

        return "[SERVER] "+s;
    }

    @Override
    public void handleException(@NotNull Throwable throwable) {
        if (throwable instanceof SocketException) {
            if (throwable.getMessage().equals("Connection reset")) {
                Main.gameState = GameState.ERROR;
            }
        }
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

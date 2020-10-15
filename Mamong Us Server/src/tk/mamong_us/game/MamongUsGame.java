package tk.mamong_us.game;

import com.siinus.server.ServerChannel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tk.mamong_us.Main;

import java.util.ArrayList;
import java.util.HashMap;

public class MamongUsGame {
    private HashMap<String, PlayerData> players = new HashMap<>();
    private ArrayList<String> impostors = new ArrayList<>();
    private GameVariables gameVariables = GameVariables.createDefault();

    public PlayerData addPlayer(String name) {
        PlayerData data = new PlayerData();
        players.put(name, data);
        return data;
    }

    public HashMap<String, PlayerData> getPlayers() {
        return players;
    }

    public ArrayList<String> getImpostors() {
        return impostors;
    }

    public GameVariables getGameVariables() {
        return gameVariables;
    }

    public void startGame() {
        assert Main.game != null;
        Main.server.broadcast("start");
        for (String n : Main.names.keySet()) {
            PlayerData data = Main.game.addPlayer(n);
            System.out.println(data);
            for (ServerChannel channel : Main.server.getChannels()) {
                if (channel.getName().equals(n)) {
                    StringBuilder mates = new StringBuilder();
                    if (data.isImpostor()) {
                        for (String s : impostors) mates.append(s).append(" ");
                    } else {
                        for (String s : players.keySet()) mates.append(s).append(" ");
                    }
                    Main.server.send(channel, "Server mates "+mates.toString().trim());
                    Main.server.send(channel, "Server impostor "+data.isImpostor());
                }
            }
        }
    }

    public void stopGame() {
        Main.game = null;
        Main.server.broadcast("stop");
    }

    @Contract(value = " -> new", pure = true)
    public static @NotNull MamongUsGame startNewGame() {
        return new MamongUsGame();
    }
}

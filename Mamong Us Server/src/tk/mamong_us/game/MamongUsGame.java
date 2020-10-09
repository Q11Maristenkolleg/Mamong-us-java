package tk.mamong_us.game;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

    @Contract(value = " -> new", pure = true)
    public static @NotNull MamongUsGame startNewGame() {
        return new MamongUsGame();
    }
}

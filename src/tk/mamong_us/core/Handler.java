package tk.mamong_us.core;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;

import java.util.HashMap;
import java.util.LinkedList;

public class Handler {

    public static HashMap<GameState, LinkedList<ProgramObject>> objects = new HashMap<>();

    static {
        for (GameState state : GameState.values()) {
            objects.put(state, new LinkedList<>());
        }
    }

    public static void deleteObject(ProgramObject object) {
        for (GameState state : objects.keySet()) {
            objects.get(state).remove(object);
        }
    }

    public static void update() {
        for (ProgramObject object : objects.get(Main.gameState)) {
            object.update();
        }
    }

    public static void render() {
        for (ProgramObject object : objects.get(Main.gameState)) {
            object.render();
        }
    }
}

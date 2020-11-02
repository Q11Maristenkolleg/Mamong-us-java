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
        for (int i=0; i<objects.get(Main.gameState).size(); i++) {
            objects.get(Main.gameState).get(i).update();
        }
    }

    public static void render() {
        for (int i=0; i<objects.get(Main.gameState).size(); i++) {
            objects.get(Main.gameState).get(i).render();
        }
    }
}

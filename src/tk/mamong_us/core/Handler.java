package tk.mamong_us.core;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;

import java.util.HashMap;
import java.util.LinkedList;

public class Handler {

    public static HashMap<GameState, LinkedList<ProgramObject>> objects = new HashMap<>();

    public static void deleteObject(ProgramObject object) {
        for (GameState state : objects.keySet()) {
            objects.get(state).remove(object);
        }
    }

    public synchronized void update() {
        for (ProgramObject object : objects.get(Main.gameState)) {
            object.update();
        }
    }

    public synchronized void render() {
        for (ProgramObject object : objects.get(Main.gameState)) {
            object.render();
        }
    }
}

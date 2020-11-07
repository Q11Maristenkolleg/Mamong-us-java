package tk.mamong_us.core;

import org.jetbrains.annotations.NotNull;
import tk.mamong_us.GameState;

import java.util.LinkedList;

public interface ProgramObject {

    void update();

    void render();

    default void register(GameState @NotNull ... states) {
        /*for (GameState state : states) {
            if (Handler.objects.containsKey(state)) {
                Handler.objects.get(state).add(this);
            } else {
                LinkedList<ProgramObject> obj = new LinkedList<>();
                obj.add(this);
                Handler.objects.put(state, obj);
            }
        }*/
    }
}

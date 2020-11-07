package tk.mamong_us.core;

import java.util.List;

public abstract  class Scene {
    private static Scene active = null;

    private List<ProgramObject> objects;

    public void add(ProgramObject o) {
        objects.add(o);
    }

    public void remove(ProgramObject o) {
        objects.remove(o);
    }

    public List<ProgramObject> getObjects() {
        return objects;
    }

    public abstract void load();

    public abstract void update();

    public abstract void render();

    public abstract void unload();

    public static Scene getActive() {
        return active;
    }

    public static void setActive(Scene active) {
        if (Scene.active != null) {
            Scene.active.unload();
        }
        Scene.active = active;
        Scene.active.load();
    }
}

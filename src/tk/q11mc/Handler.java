package tk.q11mc;

import java.util.LinkedList;

public class Handler {

    public static LinkedList<GameObject> objects = new LinkedList<>();

    public void update() {

        for (GameObject tempObject : objects) {
            tempObject.update();

        }

    }
    public void render() {

        for (GameObject tempObject : objects) {
            tempObject.render();

        }
    }
    public void addObject(GameObject objects) {

       Handler.objects.add(objects);
    }
    public void deleteObject(GameObject objects) {
        Handler.objects.remove(objects);
    }
}

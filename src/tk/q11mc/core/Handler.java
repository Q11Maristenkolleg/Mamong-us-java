package tk.q11mc.core;

import tk.q11mc.Main;
import tk.q11mc.gui.GUIObject;
import tk.q11mc.objects.GameObject;

import java.util.LinkedList;

public class Handler {

    public static LinkedList<GameObject> gameObjects = new LinkedList<>();
    public static LinkedList<GUIObject> guiObjects = new LinkedList<>();

    public void update() {
        switch (Main.gameState) {
            case MAIN_MENU -> {
                for (GUIObject tempObject : guiObjects) {
                    tempObject.update();
                }
            }
            case SINGLEPLAYER, MULTIPLAYER -> {
                for (GameObject tempObject : gameObjects) {
                    tempObject.update();
                }
            }
        }
    }
    public void render() {
        switch (Main.gameState) {
            case MAIN_MENU -> {
                for (GUIObject tempObject : guiObjects) {
                    tempObject.render();
                }
            }
            case SINGLEPLAYER, MULTIPLAYER -> {
                for (GameObject tempObject : gameObjects) {
                    tempObject.render();
                }
            }
        }
    }

    public static void addObject(GameObject objects) {

       Handler.gameObjects.add(objects);
    }

    public static void addObject(GUIObject objects) {
        Handler.guiObjects.add(objects);
    }

    public static void deleteObject(GameObject objects) {
        Handler.gameObjects.remove(objects);
    }

    public static void deleteObject(GUIObject objects) {
        Handler.guiObjects.remove(objects);
    }
}

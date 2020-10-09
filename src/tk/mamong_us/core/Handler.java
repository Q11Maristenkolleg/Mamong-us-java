package tk.mamong_us.core;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.gui.GUIObject;
import tk.mamong_us.objects.GameObject;
import tk.mamong_us.objects.Player;

import java.util.LinkedList;

public class Handler {

    public static LinkedList<GameObject> gameObjects = new LinkedList<>();
    public static LinkedList<GUIObject> guiObjects = new LinkedList<>();

    public synchronized void update() {
        switch (Main.gameState) {
            case MAIN_MENU -> {
                for (GUIObject tempObject : guiObjects) {
                    if (tempObject.getStates().length==1 && tempObject.getStates()[0] == GameState.MAIN_MENU)
                    tempObject.update();
                }
            }
            case PAUSE -> {
                Main.getInstance().getRenderer().setBgColor(0x7f7f7f);
                for (GUIObject tempObject : guiObjects) {
                    if (tempObject.getStates().length==1 && tempObject.getStates()[0] == GameState.PAUSE)
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

    public synchronized void render() {
        switch (Main.gameState) {
            case MAIN_MENU -> {
                for (GUIObject tempObject : guiObjects) {
                    if (tempObject.getStates().length==1 && tempObject.getStates()[0] == GameState.MAIN_MENU)
                        tempObject.render();
                }
            }
            case PAUSE -> {
                for (GUIObject tempObject : guiObjects) {
                    if (tempObject.getStates().length==1 && tempObject.getStates()[0] == GameState.PAUSE)
                        tempObject.render();
                }
                if (Main.lastState == GameState.SINGLEPLAYER || Main.lastState == GameState.MULTIPLAYER) {
                    for (GameObject temp : gameObjects) {
                        if (temp instanceof Player) {
                            ((Player) temp).renderShadow();
                        }
                    }
                    for (GameObject tempObject : gameObjects) {
                        tempObject.render();
                    }
                }
            }
            case SINGLEPLAYER, MULTIPLAYER -> {
                for (GameObject temp : gameObjects) {
                    if (temp instanceof Player) {
                        ((Player) temp).renderShadow();
                    }
                }
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
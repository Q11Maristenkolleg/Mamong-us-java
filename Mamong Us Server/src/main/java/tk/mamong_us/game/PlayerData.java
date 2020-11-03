package tk.mamong_us.game;

import java.util.Random;

public class PlayerData {
    private Color color;
    private boolean impostor;

    public PlayerData() {
        color = Color.BLACK;
        impostor = new Random().nextBoolean();
    }

    public boolean isImpostor() {
        return impostor;
    }

    @Override
    public String toString() {
        return "PlayerData{" +
                "color=" + color +
                ", impostor=" + impostor +
                '}';
    }

    public enum Color {
        RED, ORANGE, BLUE, BLACK;
    }
}

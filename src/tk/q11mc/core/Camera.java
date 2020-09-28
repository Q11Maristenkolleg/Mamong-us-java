package tk.q11mc.core;

import tk.q11mc.objects.Player;

public class Camera {
    private Player player;
    private int x, y;

    public Camera(Player player) {
        this.player = player;
    }

    public void update() {
        x = player.getX() + player.getWidth() / 2;
        y = player.getY() + player.getHeight() / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

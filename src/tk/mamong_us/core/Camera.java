package tk.mamong_us.core;

import tk.mamong_us.objects.Player;

public class Camera {
    private final Player player;
    private int x, y;

    public Camera(Player player) {
        this.player = player;
    }

    public void update() {
        int width = -150;
        int targetX = player.getX() - width / 2 + player.getWidth()/2;
        int height = -150;
        int targetY = player.getY() - height / 2 + player.getHeight()/2;

        targetX = Math.min(/*Map.width*/ 5000 - width, Math.max(0, targetX));
        targetY = Math.min(/*Map.height*/2000 - height, Math.max(0, targetY));

        x += (targetX - x) * 0.1;
        y += (targetY - y) * 0.1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

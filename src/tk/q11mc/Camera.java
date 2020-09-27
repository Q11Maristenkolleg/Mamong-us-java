package tk.q11mc;

public class Camera {
    private Player player;
    private int x, y;

    public Camera(Player player) {
        this.player = player;
    }

    public void update() {
        x = player.x + player.width / 2;
        y = player.y + player.height / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

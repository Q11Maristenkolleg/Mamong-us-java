package tk.mamong_us;

import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mamong_us.objects.OtherPlayer;
import tk.mamong_us.objects.Player;

public enum PlayerSprite {
    ORANGE(new ImageTile("/animation_orange.png",300, 300), 1, 0),
    RED(new ImageTile("/animation_red.png",300, 300), 0, 0),
    BLUE(new ImageTile("/animation_blue.png",300, 300), 0, 2),
    BLACK(new ImageTile("/animation_black.png",300, 300), 0, 3);

    public static final int WIDTH = 130;
    public static final int HEIGHT = 180;
    public static final int OX = 90;
    public static final int OY = 60;

    public final ImageTile image;
    public final int cx, cy;

    PlayerSprite(ImageTile image, int cx, int cy) {
        this.image = image;
        this.cx = cx;
        this.cy = cy;
    }

    @Contract("_ -> new")
    public @NotNull Player getNewPlayer(Main program) {
        return new Player(program, image, WIDTH, HEIGHT, OX, OY);
    }

    @Contract("_, _ -> new")
    public @NotNull OtherPlayer getNewOtherPlayer(Main program, String name) {
        return new OtherPlayer(program, name, image);
    }

    public static @Nullable PlayerSprite fromCxCy(int x, int y) {
        for (PlayerSprite sprite : PlayerSprite.values()) {
            if (sprite.cx == x && sprite.cy == y) {
                return sprite;
            }
        }
        return null;
    }
}

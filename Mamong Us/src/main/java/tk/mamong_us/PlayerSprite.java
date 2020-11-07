package tk.mamong_us;

import com.siinus.Texture;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mamong_us.objects.OtherPlayer;
import tk.mamong_us.objects.Player;

public enum PlayerSprite {;
    /*ORANGE(new Texture("/animation_orange.png"), 1, 0),
    RED(new Texture("/animation_red.png"), 0, 0),
    BLUE(new Texture("/animation_blue.png"), 0, 2),
    BLACK(new Texture("/animation_red.png"), 0, 3);*/

    public static final int WIDTH = 130;
    public static final int HEIGHT = 180;
    public static final int OX = 90;
    public static final int OY = 60;

    public final Texture image;
    public final int cx, cy;

    PlayerSprite(Texture image, int cx, int cy) {
        this.image = image;
        this.cx = cx;
        this.cy = cy;
    }

    @Contract("_ -> new")
    public @NotNull Player getNewPlayer(Program program) {
        return new Player(program, image, WIDTH, HEIGHT, OX, OY);
    }

    @Contract("_, _, _ -> new")
    public @NotNull OtherPlayer getNewOtherPlayer(Program program, String ip, String name) {
        return new OtherPlayer(program, ip, name, image);
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

package tk.q11mc;

import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import tk.q11mc.objects.OtherPlayer;
import tk.q11mc.objects.Player;

public enum PlayerSprite {
    ORANGE(new ImageTile("/animation_orange.png",300, 300)),
    RED(new ImageTile("/animation_red.png",300, 300));

    public static final int WIDTH = 130;
    public static final int HEIGHT = 180;
    public static final int OX = 90;
    public static final int OY = 60;

    private final ImageTile image;

    PlayerSprite(ImageTile image) {
        this.image = image;
    }

    public ImageTile getImage() {
        return image;
    }

    @Contract("_ -> new")
    public @NotNull Player getNewPlayer(Main program) {
        return new Player(program, image, WIDTH, HEIGHT, OX, OY);
    }

    @Contract("_, _ -> new")
    public @NotNull OtherPlayer getNewOtherPlayer(Main program, String name) {
        return new OtherPlayer(program, name, image);
    }
}

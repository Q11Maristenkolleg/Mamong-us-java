package tk.mamong_us.objects;

import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;
import tk.mamong_us.PlayerSprite;
import tk.mamong_us.Program;
import tk.mamong_us.core.Handler;

import java.util.ArrayList;

public class OtherPlayer extends GameObject {
    public static final ArrayList<OtherPlayer> onlinePlayers = new ArrayList<>();

    public int nameColor = 0xff000000;

    private final String ip;
    private final String name;
    private boolean moving = false;
    private boolean left = false;
    private byte frame = 1;
    private byte buffer = 0;

    /**
     * Creates a new OtherPlayer object.
     *
     * @param program The associated program
     * @param name The name
     */
    public OtherPlayer(Program program, String ip, String name, ImageTile spriteSheet) {
        super(program,spriteSheet, 0,0,0,0);
        this.ip = ip;
        this.name = name;
        onlinePlayers.add(this);
    }

    @Override
    public void update() {
        if (moving) {
            frameUp();
        } else {
            frame = 1;
            buffer = 0;
        }
    }

    @Override
    public void render() {
        program.getRenderer().drawImageTile(spriteSheet, x+offX(), y+offY(),left?1:0,moving?(4-frame):4);
        program.getRenderer().drawText(name, x+offX()+100, y+offY(), nameColor, null);
    }

    public void destroy() {
        Handler.deleteObject(this);
        onlinePlayers.remove(this);
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void frameUp() {
        if (buffer > 0) {
            buffer--;
            return;
        }
        buffer = 5;
        if (frame < 4) {
            frame++;
            return;
        }
        frame = 1;
    }

    public void setSprite(@NotNull PlayerSprite sprite) {
        this.spriteSheet = sprite.image;
    }

    public String getIp() {
        return ip;
    }
}

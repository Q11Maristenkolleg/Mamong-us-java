package tk.q11mc.objects;

import com.siinus.simpleGrafix.gfx.ImageTile;
import tk.q11mc.Main;
import tk.q11mc.core.Handler;

public class OtherPlayer extends GameObject {
    private final String name;

    /**
     * Creates a new OtherPlayer object.
     *
     * @param program The associated program
     * @param name The name
     */
    public OtherPlayer(Main program, String name, ImageTile spriteSheet) {
        super(program,spriteSheet, 0,0,0,0);
        this.name = name;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        program.getRenderer().drawImageTile(spriteSheet, x+offX(), y+offY(),0,3);
        program.getRenderer().drawText(name, x+offX()+100, y+offY(), 0xff000000, Main.arial32);
    }

    public void destroy() {
        Handler.deleteObject(this);
    }
}

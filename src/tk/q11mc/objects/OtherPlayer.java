package tk.q11mc.objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Main;

public class OtherPlayer extends GameObject {
    private final String name;

    /**
     * Creates a new OtherPlayer object.
     *
     * @param program The associated program
     * @param sprite  The texture
     * @param name The name
     */
    public OtherPlayer(Main program, ShaderImage sprite, String name) {
        super(program, sprite, 0,0,0,0);
        this.name = name;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        program.getRenderer().drawImage(sprite, x+offX(), y+offY());
        program.getRenderer().drawText(name, x+offX(), y+offY()-40, 0xff000000, Main.arial32);
    }
}

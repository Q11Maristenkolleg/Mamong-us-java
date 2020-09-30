package tk.q11mc.objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Main;

import java.awt.*;

public class Wall extends GameObject implements Collideable {


    /**
     * Creates a new Wall object.
     *
     * @param program The associated program
     * @param sprite  The texture
     * @param width   The width of the bounding box
     * @param height  The height of the bounding box
     * @param ox      The offset of the bounding box to the right
     * @param oy      The offset of the bounding box to down
     */
    public Wall(Main program, ShaderImage sprite, int width, int height, int ox, int oy) {
        super(program, sprite, width, height, ox, oy);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        program.getRenderer().drawImage(sprite, x+offX(), y+offY());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }
}

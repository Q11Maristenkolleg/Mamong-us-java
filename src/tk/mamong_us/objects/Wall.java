package tk.mamong_us.objects;


import tk.mamong_us.Main;

import java.awt.*;

public class Wall extends GameObject implements Collideable {


    /**
     * Creates a new Wall object.
     *
     * @param program The associated program
     * @param id  The type of Object
     * @param width   The width of the bounding box
     * @param height  The height of the bounding box
     * @param ox      The offset of the bounding box to the right
     * @param oy      The offset of the bounding box to down
     */
    public Wall(Main program, int id, int width, int height, int ox, int oy) {
        super(program, id, width, height, ox, oy);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        program.getRenderer().drawImageTile(Main.objectSheet,x+offX(),y+offY(),id,0);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }
}

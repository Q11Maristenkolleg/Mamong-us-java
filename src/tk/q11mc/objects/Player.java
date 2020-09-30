package tk.q11mc.objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Main;
import tk.q11mc.core.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    public int dx = 4, dy = 4;
    //dx und dy müssen genauso groß sein wie speed
    static float speed,minusSpeed;

    /**
     * Creates a new Player object.
     *
     * @param program The associated program
     * @param sprite  The texture
     * @param width   The width of the bounding box
     * @param height  The height of the bounding box
     * @param ox      The offset of the bounding box to the right
     * @param oy      The offset of the bounding box to down
     */
    public Player(Main program, ShaderImage sprite, int width, int height, int ox, int oy) {
        super(program, sprite, width, height, ox, oy);
    }

    @Override
    public void update() {
        controls();
    }
    public void controls() {
        speed = 4f;
        minusSpeed = -1*speed;
        if (program.getInput().isKeyPressed(KeyEvent.VK_D) && !collisionright()) {
            x+=speed;
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_S) && !collisiondown()) {
            y+=speed;
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_A) && !collisionleft()) {
            x-=speed;
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_W) && !collisionup()) {
            y-=speed;
        }
    }
    public boolean collisionup() {
        for(GameObject other : Handler.gameObjects ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle(x+ox,y-dy+oy,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisiondown() {
        for(GameObject other : Handler.gameObjects ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle(x+ox,y+dy+oy,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisionleft() {
        for(GameObject other : Handler.gameObjects ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle(x-dx+ox,y+oy,width
            ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisionright() {
        for(GameObject other : Handler.gameObjects ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle(x+dx+ox,y+oy,width
                    ,height))) {
                return true;
            }
        }
        return false;
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

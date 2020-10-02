package tk.q11mc.objects;

import com.siinus.simpleGrafix.gfx.ImageTile;

import tk.q11mc.GameState;
import tk.q11mc.Main;
import tk.q11mc.core.Handler;
import tk.q11mc.net.Multiplayer;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Player extends GameObject {
    static float speed,minusSpeed;
    public int dx = 2, dy = 2;
    static boolean isMoving,left = false;
    static byte buffer = 0;
    static byte frame = 1;


    /**
     * Creates a new Player object.
     *
     * @param program The associated program
     * @param spriteSheet The sheet with all the images of Player
     * @param width   The width of the bounding box
     * @param height  The height of the bounding box
     * @param ox      The offset of the bounding box to the right
     * @param oy      The offset of the bounding box to down
     */
    public Player(Main program, ImageTile spriteSheet, int width, int height, int ox, int oy) {
        super(program,spriteSheet,width,height,ox
        ,oy);

    }

    @Override
    public void update() {

        if(isMoving) {
            if(buffer <= 0) {
                buffer = 20;
                frame = (byte) (frame >= 3 ? 1 : frame+1);
            }else buffer--;
        }
        isMoving = false;
        controls();
    }
    public void controls() {
        speed = 4f;
        minusSpeed = -1*speed;

        if (program.getInput().isKeyPressed(KeyEvent.VK_D) && !collisionright()) {
            x+=speed;
            isMoving = true;
            left = false;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_S) && !collisiondown()) {
            y+=speed;

            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_A) && !collisionleft()) {
            x-=speed;

            isMoving = true;
            left = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_W) && !collisionup()) {
            y-=speed;

            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
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
        if(!isMoving) program.getRenderer().drawImageTile(spriteSheet,x+offX(),y+offY(),0,left ? 1 : 0);
        else {
            program.getRenderer().drawImageTile(spriteSheet, x + offX(), y + offY(), frame, left ? 1 : 0);
        }
    }


}

package tk.mamong_us.objects;

import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.core.Handler;
import tk.mamong_us.core.ProgramObject;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.net.Multiplayer;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Player extends GameObject {
    public static float SPEED = 8;
    public static final byte FRAME_TIME = 5;

    private static final Image shadow = new Image("/shadow.png");

    static float minusSpeed;
    public int dx, dy;
    static boolean isMoving, left, serverM = false;
    static byte buffer = 0;
    static byte frame = 1;

    private static int shhhBuffer = 0;
    private static boolean shhhImp = false;

    public static Image shhhImage = new Image("/shhh.png");

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
        dx = (int) SPEED;
        dy = (int) SPEED;
    }

    @Override
    public void update() {
        if (shhhBuffer > 0) {
            shhhBuffer--;
        }
        if (Main.lastState==GameState.MULTIPLAYER && MamongUsGame.vars != null) {
            SPEED = MamongUsGame.vars.speed * 8;
        }
        if (isMoving) {
            if(buffer <= 0) {
                buffer = FRAME_TIME;
                frame = (byte) (frame >= 4 ? 1 : frame+1);
            }else buffer--;
        }
        isMoving = false;
        controls();
    }

    public void controls() {

        minusSpeed = -1 * SPEED;

        if (program.getInput().isKeyPressed(KeyEvent.VK_D) && !collisionright()) {
            x+= SPEED;
            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER && left) {
                Multiplayer.send("face right");
            }
            left = false;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_S) && !collisiondown()) {
            y+= SPEED;

            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_A) && !collisionleft()) {
            x-= SPEED;

            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER && !left) {
                Multiplayer.send("face left");
            }
            left = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_W) && !collisionup()) {
            y-= SPEED;

            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }

        if (Main.gameState == GameState.MULTIPLAYER) {
            if (serverM && !isMoving) {
                serverM = false;
                Multiplayer.send("move stop");
            } else if (!serverM && isMoving) {
                serverM = true;
                Multiplayer.send("move start");
            }
        }
    }
    public boolean collisionup() {
        for(ProgramObject other : Handler.objects.get(Main.gameState) ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle(x+ox, (int) (y-SPEED+oy),width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisiondown() {
        for(ProgramObject other : Handler.objects.get(Main.gameState) ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle(x+ox, (int) (y+SPEED+oy),width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisionleft() {
        for(ProgramObject other : Handler.objects.get(Main.gameState) ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle((int) (x-SPEED+ox),y+oy,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisionright() {
        for(ProgramObject other : Handler.objects.get(Main.gameState) ) {
            if(other instanceof Collideable && ((Collideable) other).intersects(new Rectangle((int) (x+SPEED+ox),y+oy,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render() {
        if(!isMoving) {
            program.getRenderer().drawImageTile(spriteSheet,x+offX(),y+offY(),left ? 1 : 0, 4);
            frame = (byte) 1;
        }
        else {
            program.getRenderer().drawImageTile(spriteSheet, x + offX(), y + offY(), left ? 1 : 0, 4-frame);
        }
        if (shhhBuffer > 0) {
            program.getRenderer().setBgColor(0xff000000);
            if (shhhBuffer > 50) {
                program.getRenderer().drawImage(shhhImage, 250, 0);
            } else {
                program.getRenderer().drawText(shhhImp?"Impostor":"Crewmate", 500, 250, shhhImp?0xffff0000:0xffffffff, Main.arial32);
            }
        }
    }

    public void renderShadow() {
        program.getRenderer().drawImage(shadow, x + offX() + (left?127:67), y + offY() + 250);
    }

    public static void shhh(boolean imp) {
        shhhImp = imp;
        shhhBuffer = 200;
    }
}

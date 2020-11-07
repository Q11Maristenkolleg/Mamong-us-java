package tk.mamong_us.objects;

import com.siinus.Input;
import com.siinus.Renderer;
import com.siinus.Texture;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.PlayerSprite;
import tk.mamong_us.Program;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.net.Multiplayer;


public class Player extends GameObject {
    public static float SPEED = 8;
    public static final byte FRAME_TIME = 5;

    private int nameColor = 0xff000000;
    float vision;

    private static final Texture shadow = new Texture("/shadow.png");

    static float minusSpeed;
    static boolean isMoving, left, serverM = false;
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
    public Player(Program program, Texture spriteSheet, int width, int height, int ox, int oy) {
        super(program,spriteSheet,width,height,ox
        ,oy);
    }

    @Override
    public void update() {
        if (Main.lastState==GameState.MULTIPLAYER && Main.mpState == GameState.MultiplayerState.GAME) {
            SPEED = MamongUsGame.vars.speed * 8;
            nameColor = MamongUsGame.impostor?0xffff0000:0xff000000;
            if (vision != (MamongUsGame.impostor?MamongUsGame.vars.vision_imp:MamongUsGame.vars.vision_cm)) {
                vision = (MamongUsGame.impostor?MamongUsGame.vars.vision_imp:MamongUsGame.vars.vision_cm);
            }
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

    @Override
    public void render() {
        /*if (Main.lastState == GameState.MULTIPLAYER) {
            program.getRenderer().drawImage(Assets.theSkeld, -x, -y);
        }
        program.getRenderer().drawText(Assets.nameField.getText(), x+offX()+150-(Font.getStandard().getPixelsOfText(Assets.nameField.getText())/2), y+offY(), nameColor, null);*/
        if(!isMoving) {
            //program.getRenderer().drawImageTile(spriteSheet,x+offX(),y+offY(),left ? 1 : 0, 4);
            Renderer.render(spriteSheet);
            frame = (byte) 1;
        }
        else {
            //program.getRenderer().drawImageTile(spriteSheet, x + offX(), y + offY(), left ? 1 : 0, 4-frame);
            Renderer.render(spriteSheet);
        }
        /*if (program.getRenderer() instanceof ShaderRendererLite) {
            ((ShaderRendererLite) program.getRenderer()).drawLight(light, x+offX()+150, y+offY()+150);
            ((ShaderRendererLite) program.getRenderer()).setAmbientLight((byte) 0x3f);
        } else {
            System.out.println("FJG");
        }*/
    }

    public void controls() {

        minusSpeed = -1 * SPEED;

        if (Input.isKey(GLFW.GLFW_KEY_D) /*&& !collisionright()*/) {
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
        if (Input.isKey(GLFW.GLFW_KEY_S) /*&& !collisiondown()*/) {
            y+= SPEED;

            isMoving = true;
            if (Main.gameState == GameState.MULTIPLAYER) {
                Multiplayer.send("pos "+x+" "+y);
            }
        }
        if (Input.isKey(GLFW.GLFW_KEY_A) /*&& !collisionleft()*/) {
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
        if (Input.isKey(GLFW.GLFW_KEY_W) /*&& !collisionup()*/) {
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
    /*public boolean collisionup() {
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
    }*/


    public void renderShadow() {
        //program.getRenderer().drawImage(shadow, x + offX() + (left?127:67), y + offY() + 250);
        Renderer.render(shadow);
    }

    public void setSprite(@NotNull PlayerSprite sprite) {
        spriteSheet = sprite.image;
    }
}

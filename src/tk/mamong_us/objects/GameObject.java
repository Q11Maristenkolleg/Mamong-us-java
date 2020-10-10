package tk.mamong_us.objects;

import com.siinus.simpleGrafix.gfx.ImageTile;
import tk.mamong_us.Main;
import tk.mamong_us.core.ProgramObject;

public abstract class GameObject implements ProgramObject {
    protected Main program;
    protected int id;
    protected float speedX,speedY;
    protected int width, height;
    protected int ox, oy;
    protected int x=0, y=0;
    protected ImageTile spriteSheet;

    /**
     * Creates a new Game object.
     *
     * @param program The associated program
     * @param width The width of the bounding box
     * @param height The height of the bounding box
     * @param ox The offset of the bounding box to the right
     * @param oy The offset of the bounding box to down
     */
    public GameObject(Main program, ImageTile spriteSheet, int width, int height, int ox, int oy) {
        this.program = program;
        this.spriteSheet = spriteSheet;
        this.width = width;
        this.height = height;
        this.ox = ox;
        this.oy = oy;
    }
    public GameObject(Main program, int id, int width, int height, int ox, int oy) {
        this.program = program;
        this.id = id;
        this.width = width;
        this.height = height;
        this.ox = ox;
        this.oy = oy;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }
    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
    public float getSpeedY() {
        return speedY;
    }
    public float getSpeedX() {
        return speedX;
    }

    public int offX() {
        return (int) ((program.getWindow().getWidth()/(program.getWindow().getScale()*2))-program.getCamera().getX());
    }

    public int offY() {
        return (int) ((program.getWindow().getHeight()/(program.getWindow().getScale()*2))-program.getCamera().getY());
    }
}

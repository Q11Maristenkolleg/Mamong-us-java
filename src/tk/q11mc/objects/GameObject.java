package tk.q11mc.objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Handleable;
import tk.q11mc.Main;
import tk.q11mc.core.Handler;

import java.awt.*;

public abstract class GameObject implements Handleable {
    protected Main program;
    protected ShaderImage sprite;
    protected float speedX,speedY;
    protected int width, height;
    protected int ox, oy;
    protected int x=0, y=0;

    /**
     * Creates a new Game object.
     *
     * @param program The associated program
     * @param sprite The texture
     * @param width The width of the bounding box
     * @param height The height of the bounding box
     * @param ox The offset of the bounding box to the right
     * @param oy The offset of the bounding box to down
     */
    public GameObject(Main program, ShaderImage sprite, int width, int height, int ox, int oy) {
        this.program = program;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.ox = ox;
        this.oy = oy;
        Handler.addObject(this);
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

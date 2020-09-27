package tk.q11mc.Objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Main;
import tk.q11mc.ID.TypeID;

import java.awt.*;

public abstract class GameObject {
    protected TypeID typeID;
    protected Handler handler;
    protected Main program;
    protected ShaderImage sprite;
    protected float speedX,speedY;
    protected int width, height;
    protected int x=0, y=0;

    public GameObject(Main program, ShaderImage sprite, int width, int height, TypeID typeID, Handler handler) {
        this.program = program;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.handler = handler;
        this.typeID = typeID;
        handler.addObject(this);
    }

    public abstract void update();

    public abstract void render();

    public abstract Rectangle getBounds();

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
    public TypeID getTypeId() {
        return this.typeID;
    }

    public int offX() {
        return (int) ((program.getWindow().getWidth()/(program.getWindow().getScale()*2))-program.getCamera().getX());
    }

    public int offY() {
        return (int) ((program.getWindow().getHeight()/(program.getWindow().getScale()*2))-program.getCamera().getY());
    }
}

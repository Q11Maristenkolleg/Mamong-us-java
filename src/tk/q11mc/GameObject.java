package tk.q11mc;

import com.siinus.simpleGrafixShader.ShaderImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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





}

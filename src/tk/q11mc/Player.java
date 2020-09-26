package tk.q11mc;


import com.siinus.simpleGrafixShader.Light;
import com.siinus.simpleGrafixShader.ShaderImage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private final Light light;
    public int dx = 2, dy = 2;
    //boolean oben = false,unten = false,links = false,rechts = false;
    static float speed,minusSpeed;
    public Player(Main program, ShaderImage sprite, int width, int height, TypeID TypeID,Handler handler) {
        super(program, sprite, width, height,TypeID,handler);
        light = new Light(256,0xffffffff);
    }

    @Override
    public void update() {
        controls();
    }
    public void controls() {
        speed = 4.5f;
        minusSpeed = -1*speed;

        if(program.getInput().isKeyPressed(KeyEvent.VK_W)) {
            setSpeedY(minusSpeed);
            if(!collisionup()) {
                y+=speedY;
            }
        }
        else if(program.getInput().isKeyPressed(KeyEvent.VK_A)) {
            setSpeedX(minusSpeed);
            if(!collisionleft()) {
                x+=speedX;
            }
        }
        else if(program.getInput().isKeyPressed(KeyEvent.VK_S)) {
            setSpeedY(speed);
            if(!collisiondown()) {
                y+=speedY;
            }
        }
        else if(program.getInput().isKeyPressed(KeyEvent.VK_D)) {
            setSpeedX(speed);
            if(!collisionright()) {
                x+=speedX;
            }
        }

    }
    public boolean collisionup() {
        for(GameObject other : Handler.objects ) {
            if(other.getTypeId() != TypeID.PLAYER && other.getBounds().intersects(new Rectangle(x,y-dx,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisiondown() {
        for(GameObject other : Handler.objects ) {
            if(other.getTypeId() != TypeID.PLAYER && other.getBounds().intersects(new Rectangle(x,y+dy,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisionleft() {
        for(GameObject other : Handler.objects ) {
            if(other.getTypeId() != TypeID.PLAYER && other.getBounds().intersects(new Rectangle(x-dx,y,width
            ,height))) {
                return true;
            }
        }
        return false;
    }
    public boolean collisionright() {
        for(GameObject other : Handler.objects ) {
            if(other.getTypeId() != TypeID.PLAYER && other.getBounds().intersects(new Rectangle(x+dx,y,width
                    ,height))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render() {
        program.getRenderer().drawImage(sprite, x, y);
        program.getShader().drawLight(light, x+64, y+64);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

}

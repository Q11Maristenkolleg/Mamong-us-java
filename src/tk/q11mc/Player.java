package tk.q11mc;


//import com.siinus.simpleGrafixShader.Light;
import com.siinus.simpleGrafixShader.ShaderImage;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {
    //private final Light light;
    public int dx = 4, dy = 4;
    //dx und dy müssen genauso groß sein wie speed
    boolean oben = false,unten = false,links = false,rechts = false;
    static float speed,minusSpeed;
    public Player(Main program, ShaderImage sprite, int width, int height, TypeID TypeID,Handler handler) {
        super(program, sprite, width, height,TypeID,handler);
        //light = new Light(256,0xffffffff);
    }

    @Override
    public void update() {
        controls();
    }
    public void controls() {
        speed = 4f;
        minusSpeed = -1*speed;
        if(program.getInput().isKeyDown(KeyEvent.VK_W)) {
            oben = false;
            if(unten) {
                setSpeedY(speed);
            }
            else {
                setSpeedY(0);
            }
        }
        if(program.getInput().isKeyDown(KeyEvent.VK_A)) {
            links = false;
            if(rechts) {
                setSpeedX(speed);
            }
            else {
                setSpeedX(0);
            }
        }
        if(program.getInput().isKeyDown(KeyEvent.VK_S)) {
            unten = false;
            if(oben) {
                setSpeedY(minusSpeed);
            }
            else {
                setSpeedY(0);
            }
        }
        if(program.getInput().isKeyDown(KeyEvent.VK_D)) {
            rechts = false;
            if(links) {
                setSpeedX(minusSpeed);
            }
            else {
                setSpeedX(0);
            }
        }
        if(program.getInput().isKeyPressed(KeyEvent.VK_W)) {
            oben = true;
            setSpeedY(minusSpeed);
            if(!collisionup()) {
                y+=speedY;
            }
        }
        if(program.getInput().isKeyPressed(KeyEvent.VK_A)) {
            links = true;
            setSpeedX(minusSpeed);
            if(!collisionleft()) {
                x+=speedX;
            }
        }
        if(program.getInput().isKeyPressed(KeyEvent.VK_S)) {
            unten = true;
            setSpeedY(speed);
            if(!collisiondown()) {
                y+=speedY;
            }
        }
        if(program.getInput().isKeyPressed(KeyEvent.VK_D)) {
            rechts = true;
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
        //program.getShader().drawLight(light, x+64, y+64);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

}

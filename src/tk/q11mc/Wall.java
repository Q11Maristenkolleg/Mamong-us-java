package tk.q11mc;

import com.siinus.simpleGrafixShader.ShaderImage;

import java.awt.*;

public class Wall extends GameObject{


    public Wall(Main program, ShaderImage sprite, int width, int height, TypeID typeID, Handler handler) {
        super(program, sprite, width, height, typeID, handler);
    }

    @Override
    public void update() {

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

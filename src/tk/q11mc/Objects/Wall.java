package tk.q11mc.Objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Main;
import tk.q11mc.ID.TypeID;

import java.awt.*;

public class Wall extends GameObject {


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

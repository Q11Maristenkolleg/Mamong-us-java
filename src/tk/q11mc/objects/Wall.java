package tk.q11mc.objects;

import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.Main;
import tk.q11mc.core.Handler;

import java.awt.*;

public class Wall extends GameObject implements Collideable {


    public Wall(Main program, ShaderImage sprite, int width, int height, Handler handler) {
        super(program, sprite, width, height, handler);
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

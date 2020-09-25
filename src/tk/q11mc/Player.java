package tk.q11mc;


import com.siinus.simpleGrafixShader.Light;
import com.siinus.simpleGrafixShader.ShaderImage;

import java.awt.event.KeyEvent;

public class Player extends GameObject {
    private float speed = 2f;
    private Light light;
    private ICollideable.Side side;

    public Player(Main program, ShaderImage sprite, int width, int height) {
        super(program, sprite, width, height);
        light = new Light(256,0xffffffff);
        objects.add(this);
    }

    @Override
    public void update() {
        for (GameObject other : GameObject.objects) {
            if (other==this) {
                continue;
            }
            if (other instanceof ICollideable) {
                side = ((ICollideable) other).collides(this);
            }
        }

        control();
    }

    public void control() {
        if (program.getInput().isKeyPressed(KeyEvent.VK_W) && side != ICollideable.Side.BOTTOM) {
            y-=speed;
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_A) && side != ICollideable.Side.RIGHT) {
            x-=speed;
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_S) && side != ICollideable.Side.TOP) {
            y+=speed;
        }
        if (program.getInput().isKeyPressed(KeyEvent.VK_D) && side != ICollideable.Side.LEFT) {
            x+=speed;
        }
    }

    @Override
    public void render() {
        program.getRenderer().drawImage(sprite, x, y);
        program.getShader().drawLight(light, x+64, y+64);
    }

    public float getSpeed() {
        return speed;
    }
}

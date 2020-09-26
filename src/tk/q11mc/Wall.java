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
        program.getRenderer().drawImage(sprite, x, y);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x,y,width,height);
    }

    /*@Override
    public Side collides(GameObject other) {
        int speed = other instanceof Player ? (int) ((Player) other).getSpeed() : 1;

        if (checkCollision(x+speed, y, other)) {
            return Side.RIGHT;
        }
        if (checkCollision(x-speed, y, other)) {
            return Side.LEFT;
        }
        if (checkCollision(x, y+speed, other)) {
            return Side.BOTTOM;
        }
        if (checkCollision(x, y-speed, other)) {
            return Side.TOP;
        }
        return Side.NONE;
    }

    private boolean checkCollision(int x, int y, GameObject other) {
        return x + width >= other.x && x <= other.x + other.width && y + height >= other.y && y <= other.y + other.height;
    }

     */
}

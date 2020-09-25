package tk.q11mc;

import com.siinus.simpleGrafixShader.ShaderImage;

public class Wall extends GameObject implements ICollideable{

    public Wall(Main program, ShaderImage sprite, int width, int height) {
        super(program, sprite, width, height);
        this.sprite.setLightBlock(1);
        objects.add(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        program.getRenderer().drawImage(sprite, x, y);
    }

    @Override
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
}

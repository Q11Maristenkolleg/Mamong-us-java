package tk.q11mc;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.core.Camera;
import tk.q11mc.core.Handler;
import tk.q11mc.objects.Player;
import tk.q11mc.objects.Wall;

public class Main extends Program {
    Handler handler;
    ShaderImage spritePlayer = new ShaderImage("/player.png");
    ShaderImage spriteWall = new ShaderImage("/test.png");
    //Light light = new Light(150, 0xffffffff);
    Player player;
    Wall wall;

    Camera camera;

    public static void main(String[] args) {
        new Main().init();
    }

    public Main() {
        handler = new Handler();
        setIconImage(spritePlayer);
        spriteWall.setLightBlock(1);
        wall = new Wall(this, spriteWall, 126, 126, handler);
        player = new Player(this, spritePlayer, 126,126, handler);
        wall.setX(500);
        wall.setY(250);

        camera = new Camera(player);
    }

    @Override
    public void start() {
        getWindow().setScaleOnResize(true);
        setCapFps(false);
    }

    @Override
    public void update() {
        handler.update();

        camera.update();
    }

    @Override
    public void render() {
        getRenderer().setBgColor(0xffffffff);
        handler.render();
        //getShaderRenderer().drawLight(light, getInput().getMouseX(), getInput().getMouseY());
    }

    @Override
    public void stop() {

    }

    public Camera getCamera() {
        return camera;
    }
}

package tk.q11mc;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafixShader.Light;
import com.siinus.simpleGrafixShader.ShaderImage;
import com.siinus.simpleGrafixShader.ShaderProgram;
import com.siinus.simpleGrafixShader.ShaderRenderer;

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
        wall = new Wall(this, spriteWall, 126, 126,TypeID.WALL,new Handler());
        player = new Player(this, spritePlayer, 126,126,TypeID.PLAYER,new Handler());
        wall.x = 500;
        wall.y = 250;

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

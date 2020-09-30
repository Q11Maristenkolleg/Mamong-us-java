package tk.q11mc;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.ImageTile;
import com.siinus.simpleGrafixShader.ShaderImage;
import tk.q11mc.core.Camera;
import tk.q11mc.core.Handler;
import tk.q11mc.gui.Button;
import tk.q11mc.objects.Player;
import tk.q11mc.objects.Wall;

public class Main extends Program {
    Handler handler;
    ShaderImage spritePlayer = new ShaderImage("/player.png");
    ShaderImage spriteWall = new ShaderImage("/test.png");
    ImageTile spriteButton = new ImageTile("/SPB.png", 256, 64);
    Player player;
    Wall wall;
    Button sp;
    Button mp;

    Font arial32 = new Font("/font.png", 32, 37);

    Camera camera;

    public static GameState gameState = GameState.MAIN_MENU;

    public static void main(String[] args) {
        new Main().init();
    }

    public Main() {
        handler = new Handler();
        setIconImage(spritePlayer);
        spriteWall.setLightBlock(1);
        wall = new Wall(this, spriteWall, 126, 26, 0, 100);
        player = new Player(this, spritePlayer, 126,126, 0, 0);
        sp = new Button(this, spriteButton, 300, 300, 256, 64, this::startSingleplayer);
        mp = new Button(this, spriteButton, 300, 400, 256, 64, this::startMultiplayer);
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
        if (gameState==GameState.MAIN_MENU) {
            getRenderer().drawText("//Multiplayer", 580, 415, 0xff000000, arial32);
        }
        //getShaderRenderer().drawLight(light, getInput().getMouseX(), getInput().getMouseY());
    }

    @Override
    public void stop() {

    }

    public void startSingleplayer() {
        gameState = GameState.SINGLEPLAYER;
    }

    public void startMultiplayer() {
        gameState = GameState.MULTIPLAYER;
    }

    public Camera getCamera() {
        return camera;
    }
}

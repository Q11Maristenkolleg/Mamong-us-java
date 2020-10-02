package tk.q11mc;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.ImageTile;
import com.siinus.simpleGrafixShader.ShaderImage;
import org.json.simple.JSONObject;
import tk.q11mc.core.Camera;
import tk.q11mc.core.Handler;
import tk.q11mc.gui.Button;
import tk.q11mc.gui.TextInput;
import tk.q11mc.gui.TextQueue;
import tk.q11mc.net.Multiplayer;
import tk.q11mc.objects.OtherPlayer;
import tk.q11mc.objects.Player;
import tk.q11mc.objects.Wall;

import java.awt.event.KeyEvent;

public class Main extends Program {
    private static Main instance;

    Handler handler;
    public static ShaderImage spritePlayer = new ShaderImage("/player.png");
    public static ShaderImage spriteWall = new ShaderImage("/test.png");
    public static ImageTile spriteButton = new ImageTile("/SPB.png", 256, 64);
    public static ImageTile spriteText = new ImageTile("/text.png", 256, 64);
    Player player;
    Wall wall;
    Button sp;
    Button mp;
    TextInput ti;
    TextInput pi;
    TextInput ni;
    TextQueue tq;


    //OtherPlayer otherPlayer;


    public static Font arial32 = new Font("/font.png", 32, 37);

    Camera camera;

    public static GameState gameState = GameState.MAIN_MENU;
    boolean loadMP = false;

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
        ni = new TextInput(this, spriteText, 300, 150, 256, 64, 0xff0000ff, arial32);
        ti = new TextInput(this, spriteText, 300, 500, 256, 64, 0xff000000, arial32);
        pi = new TextInput(this, spriteText, 300, 600, 256, 64, 0xff000000, arial32);
        tq = new TextQueue();
        tq.endAction = this::startMultiplayer;
        ti.register(tq);
        pi.register(tq);
        wall.setX(500);
        wall.setY(250);

        camera = new Camera(player);

        instance = this;

        //otherPlayer = new OtherPlayer(this, spritePlayer, "Hi");
    }

    @Override
    public void start() {
        getWindow().setScaleOnResize(true);
        setCapFps(true);
    }

    @Override
    public void update() {
        if (getInput().isKeyDown(KeyEvent.VK_ESCAPE) && gameState != GameState.MAIN_MENU) {
            if (gameState == GameState.PAUSE) {
                gameState = GameState.SINGLEPLAYER;
            } else {
                System.out.println("pause");
                startPause();
            }
        }
        if (loadMP) {
            loadMP = false;
            connectMultiplayer();
        }

        handler.update();

        camera.update();
    }

    @Override
    public void render() {
        getRenderer().setBgColor(0xffffffff);
        handler.render();
        if (gameState == GameState.MULTIPLAYER) {
            double ping = (Multiplayer.getPing() * 1000);
            getRenderer().drawText("Ping: " + ((int) ping) + " ms", 10, 10, 0xff000000, arial32);
        }
        if (gameState == GameState.LOADING) {
            getRenderer().drawText("Loading...",10, 10,0xff000000, Main.arial32);
        }
        if (gameState == GameState.ERROR) {
            getRenderer().drawText("Connection refused!",10, 10,0xffff0000, arial32);
        }
        //getShaderRenderer().drawLight(light, getInput().getMouseX(), getInput().getMouseY());
    }

    @Override
    public void stop() {

    }

    public void startMainMenu() {
        if (gameState == GameState.MULTIPLAYER) {
            Multiplayer.disconnect();
        }
        gameState = GameState.MAIN_MENU;
    }

    public void startPause() {
        gameState = GameState.PAUSE;
    }

    public void startSingleplayer() {
        gameState = GameState.SINGLEPLAYER;
    }

    public void startMultiplayer() {
        gameState = GameState.LOADING;
        loadMP = true;
    }

    private void connectMultiplayer() {
        if (Multiplayer.connect(ti.getText().toLowerCase(), Integer.parseInt(pi.getText()))) {
            if (ni.getText().length()<=0) {
                ni.setText("Player"+((int) (Math.random()*100)));
            }
            Multiplayer.send("connect "+ni.getText());
            gameState = GameState.MULTIPLAYER;
        } else {
            gameState = GameState.ERROR;
        }
    }

    public Camera getCamera() {
        return camera;
    }

    public static Main getInstance() {
        return instance;
    }

    /*private JSONObject saveData() {

    }*/
}

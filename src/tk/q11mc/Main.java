package tk.q11mc;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
//import org.json.simple.JSONObject;
import org.json.simple.JSONObject;
import tk.q11mc.core.Camera;
import tk.q11mc.core.Handler;
import tk.q11mc.gui.Button;
import tk.q11mc.gui.TextInput;
import tk.q11mc.gui.TextQueue;
import tk.q11mc.io.FileIO;
import tk.q11mc.net.Multiplayer;
import tk.q11mc.objects.Player;
import tk.q11mc.objects.Wall;

import java.awt.event.KeyEvent;

public class Main extends Program {
    private static Main instance;

    Handler handler;
    public static ImageTile playerSheet = new ImageTile("/playerSheet.png",50,100);
    public static ImageTile objectSheet = new ImageTile("/objectSheet.png",126,126);
    public static ImageTile spriteButton = new ImageTile("/SPB.png", 256, 64);
    public static ImageTile spriteText = new ImageTile("/text.png", 256, 64);
    private Image icon = new Image("/icon.png");
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
        setIconImage(icon);
        wall = new Wall(this, 1, 126, 26, 0, 0);
        player = new Player(this, playerSheet, 50,100, 0, 0);
        sp = new Button(this, spriteButton, 300, 300, 256, 64, this::startSingleplayer);
        mp = new Button(this, spriteButton, 300, 400, 256, 64, this::startMultiplayer);
        ni = new TextInput(this, spriteText, 300, 150, 256, 64, 0xff0000ff, arial32);
        ni.setDefaultText("Name");
        ti = new TextInput(this, spriteText, 300, 500, 256, 64, 0xff000000, arial32);
        ti.setDefaultText("IP");
        pi = new TextInput(this, spriteText, 300, 600, 256, 64, 0xff000000, arial32);
        pi.setDefaultText("Port");
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

        InputUtils.setInput(getInput());
    }

    @Override
    public void update() {
        InputUtils.update(getInput());
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

    @SuppressWarnings("unchecked")
    private void saveData() {
        JSONObject root = new JSONObject();
        root.put("name", ni.getText());
        root.put("ip", ti.getText());
        root.put("port", pi.getText());
        FileIO.saveJSON("./config.json", root);
    }

    private void loadData() {
        JSONObject root = FileIO.loadJSON("./config.json");
        if (root == null) {
            return;
        }
        ni.setText((String) root.get("name"));
        ti.setText((String) root.get("ip"));
        pi.setText((String) root.get("port"));
    }
}

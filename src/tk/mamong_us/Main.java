package tk.mamong_us;

import com.siinus.simpleGrafix.Program;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.json.simple.JSONObject;
import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.core.Camera;
import tk.mamong_us.core.Handler;
import tk.mamong_us.discord.DiscordRP;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.gui.*;
import tk.mamong_us.io.FileIO;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.Player;
import tk.mamong_us.objects.Wall;
import tk.mamong_us.video.Video;

import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Main extends Program {
    private static Main instance;

    Handler handler;
    public static ImageTile objectSheet = new ImageTile("/objectSheet.png",126,126);
    public static ImageTile mainMenu = new ImageTile("/mainMenu.png",300,100);
    public static ImageTile mpButton = new ImageTile("/mpButton.png",300,100);
    public static ImageTile spButton = new ImageTile("/spButton.png",300,100);
    public static ImageTile spriteText = new ImageTile("/text.png", 256, 64);
    private static final Image icon = new Image("/icon2.png");

    public static Video shhhVideo;
    public static Video emergencyVideo;
    public static Video discussVideo;
    public static LinkedList<Video> killVideos = new LinkedList<>();
    public static Video deadBodyVideo;

    static {
        new Thread(() -> {
            killVideos.add(new Video("/gunkill", 25));
            deadBodyVideo = new Video("/deadbody", 25);
            killVideos.add(new Video("/knifekill", 25));
            killVideos.add(new Video("/neckkill", 25));
            killVideos.add(new Video("/tonguekill", 25));
        }).start();
        shhhVideo = new Video("/shhh", 25);
        emergencyVideo = new Video("/emergency", 25);
        discussVideo = new Video("/discuss", 25);
    }

    private static final DiscordRP discordRP = new DiscordRP();
    public static int width, height;
    public static float scale;
    public PlayerSprite sprite = PlayerSprite.RED;
    public Player player;
    Wall wall;
    Button singlePlayerButton;
    Button multiPlayerButton;
    Button btmm;
    TextInput ipField;
    TextInput portField;
    TextInput nameField;
    TextQueue tq;


    //OtherPlayer otherPlayer;
    public ColorChooser colorChooser = null;


    public static Font arial32 = new Font("/font.png", 37, 37);
    public static Font bahnschrift32 = new Font("/bahnschrift.png", 37, 40);

    Camera camera;

    public static GameState gameState = GameState.MAIN_MENU;
    public static GameState lastState = GameState.MAIN_MENU;
    public static GameState.MultiplayerState mpState = null;
    boolean loadMP = false;

    public static void main(String[] args) {
        new Main().init();
    }

    public Main() {
        Font.setStandard(bahnschrift32);
        setIconImage(icon);
        wall = new Wall(this, 1, 126, 26, 0, 0);
        wall.register(GameState.SINGLEPLAYER, GameState.MULTIPLAYER);
        player = sprite.getNewPlayer(this);
        player.register(GameState.SINGLEPLAYER, GameState.MULTIPLAYER);
        wall.setX(500);
        wall.setY(250);

        camera = new Camera(player);

        instance = this;

        //otherPlayer = new OtherPlayer(this, spritePlayer, "Hi");
    }

    @Override
    public void start() {
        discordRP.start();

        getWindow().setScaleOnResize(true);
        getWindow().getFrame().setTitle("Mamong us");
        getWindow().getFrame().setFocusTraversalKeysEnabled(false);
        setCapFps(true);


        InputUtils.setInput(getInput());

        setupMainMenu();

        Stars.init();

        loadData();
    }

    @Override
    public void update() {
        InputUtils.update(getInput());
        OutputChat.update();
        Stars.update();

        if (InputUtils.isKeyDown(KeyEvent.VK_ESCAPE) && gameState != GameState.MAIN_MENU) {
            if (gameState == GameState.PAUSE) {
                gameState = lastState;
            } else {
                System.out.println("pause");
                startPause();
            }
        }
        if (gameState == GameState.MULTIPLAYER) {
            if (InputUtils.isKeyDown(0x42)) {
                Multiplayer.send("create");
            }
            if (mpState == GameState.MultiplayerState.LOBBY && InputUtils.isKeyDown(0x45)) {
                Multiplayer.send("start");
            }
            if (InputUtils.isKeyDown(0x43)) {
                if (colorChooser == null) {
                    colorChooser = new ColorChooser(this, sprite == null ? sprite = PlayerSprite.RED : sprite);
                    colorChooser.register(GameState.MULTIPLAYER);
                } else {
                    Handler.deleteObject(colorChooser);
                    colorChooser = null;
                }
            }
            if (InputUtils.isKeyDown(0x48)) {
                Multiplayer.send("stop");
            }
            if (MamongUsGame.optionText != null) {
                for (Button button : MamongUsGame.configButtons) {
                    button.update();
                }
            }
        }
        if (loadMP) {
            loadMP = false;
            connectMultiplayer();
        }

        Handler.update();

        camera.update();
        discordRP.update(ipField.getText());
    }

    @Override
    public void render() {
        if (gameState==GameState.MAIN_MENU) {
            Stars.render();
            getRenderer().setBgColor(0xff000000);
        } else {
            getRenderer().setBgColor(0xffffffff);
        }
        Handler.render();
        if (gameState == GameState.MULTIPLAYER) {
            double ping = (Multiplayer.getPing() * 1000);
            getRenderer().drawText("Ping: " + ((int) ping) + " ms", 10, 10, 0xff000000, null);
            getRenderer().drawText(OutputChat.text(), 1400, 200, 0xff007f3f, null);
            if (mpState == GameState.MultiplayerState.GAME) {
                getRenderer().drawText(MamongUsGame.taskText(), 100, 100, 0xff000000, null);
            } else {
                getRenderer().drawText("Press [B] to create a game and [E] to start.", 700, 800, 0xff000000, null);
                if (MamongUsGame.optionText != null) {
                    getRenderer().drawText(MamongUsGame.optionText, 100, 100, 0xff000000, null);
                    for (Button button : MamongUsGame.configButtons) {
                        button.render();
                    }
                }
            }
        }
        if (gameState == GameState.LOADING) {
            getRenderer().drawText("Loading...",10, 10,0xff000000, null);
        }
        if (gameState == GameState.ERROR) {
            getRenderer().drawText("Connection refused!",10, 10,0xffff0000, null);
        }
    }

    @Override
    public void stop() {
        discordRP.shutdown();
    }

    public void startMainMenu() {
        if (lastState == GameState.MULTIPLAYER) {
            Multiplayer.disconnect();
            if (colorChooser != null) {
                Handler.deleteObject(colorChooser);
                colorChooser = null;
            }
        }
        gameState = GameState.MAIN_MENU;
        lastState = GameState.MAIN_MENU;
    }

    public void startPause() {
        gameState = GameState.PAUSE;
    }

    public void startSingleplayer() {
        gameState = GameState.SINGLEPLAYER;
        lastState = GameState.SINGLEPLAYER;
    }

    public void startMultiplayer() {
        gameState = GameState.LOADING;
        loadMP = true;
    }

    private void connectMultiplayer() {
        if (Multiplayer.connect(ipField.getText().toLowerCase(), Integer.parseInt(portField.getText()))) {
            if (nameField.getText().length()<=0) {
                nameField.setText("Player"+((int) (Math.random()*100)));
            }
            saveData();
            Multiplayer.send("connect "+nameField.getText());
            gameState = GameState.MULTIPLAYER;
            lastState = GameState.MULTIPLAYER;
        } else {
            gameState = GameState.ERROR;
        }
    }

    private void setupMainMenu() {
        width = (int) (getWindow().getWidth() / getWindow().getScale());
        height = (int) (getWindow().getHeight() / getWindow().getScale());
        scale = getWindow().getScale();

        singlePlayerButton = new Button(this, spButton, width/2-150, 275, 300, 100, this::startSingleplayer);
        singlePlayerButton.register(GameState.MAIN_MENU);
        multiPlayerButton = new Button(this, mpButton, width/2-150, 375, 300, 100, this::startMultiplayer);
        multiPlayerButton.register(GameState.MAIN_MENU);
        btmm = new Button(this, mainMenu, width/2-150, height/2-50, 256, 64, this::startMainMenu);
        btmm.register(GameState.PAUSE, GameState.ERROR);
        nameField = new TextInput(this, spriteText, width/2-128, 150, 256, 64, 0xff00ffff, null);
        nameField.register(GameState.MAIN_MENU);
        nameField.setDefaultText("Name");
        ipField = new TextInput(this, spriteText, width/2-128, 550, 256, 64, 0xffffffff, null);
        ipField.register(GameState.MAIN_MENU);
        ipField.setDefaultText("IP");
        portField = new TextInput(this, spriteText, width/2-128, 650, 256, 64, 0xffffffff, null);
        portField.register(GameState.MAIN_MENU);
        portField.setDefaultText("Port");
        tq = new TextQueue();
        tq.endAction = this::startMultiplayer;
        ipField.register(tq);
        portField.register(tq);
    }

    public Camera getCamera() {
        return camera;
    }

    public Player getPlayer() {
        return player;
    }

    public static Main getInstance() {
        return instance;
    }

    public static int getMidX() {
        return (int) (instance.getWindow().getWidth()/(instance.getWindow().getScale()*2));
    }

    public static int getMidY() {
        return (int) (instance.getWindow().getHeight()/(instance.getWindow().getScale()*2));
    }

    @SuppressWarnings("unchecked")
    private void saveData() {
        JSONObject root = new JSONObject();
        root.put("name", nameField.getText());
        root.put("ip", ipField.getText());
        root.put("port", portField.getText());
        FileIO.saveJSON("./config.json", root);
    }

    private void loadData() {
        JSONObject root = FileIO.loadJSON("./config.json");
        if (root == null) {
            return;
        }
        nameField.setText((String) root.get("name"));
        ipField.setText((String) root.get("ip"));
        portField.setText((String) root.get("port"));
    }

}

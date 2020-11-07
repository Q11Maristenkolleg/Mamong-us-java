package tk.mamong_us;

import com.siinus.Input;
import com.siinus.Renderer;
import com.siinus.Shader;
import com.siinus.Texture;
import org.lwjgl.glfw.GLFW;
import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.core.Scene;
import tk.mamong_us.core.scenes.MainMenu;
import tk.mamong_us.discord.DiscordRP;
import tk.mamong_us.gui.Stars;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.Player;

public class Program  extends com.siinus.Program {
    /*private ShaderRendererLite shaderRenderer;
    private Renderer normalRenderer;*/

    boolean loadMP = false;

    private static final DiscordRP discordRP = new DiscordRP();
    public static int width, height;
    public static float scale;

    //public PlayerSprite sprite = PlayerSprite.RED;
    public Player player;

    public Player getPlayer() {
        return player;
    }

    @Override
    public void start() {
        //Font.setStandard(Assets.bahnschrift32);
        //SplashScreen splashScreen = new SplashScreen();
        //splashScreen.progressBar.setString("Initializing program...");
        //Assets.pBpp(splashScreen, "Loading assets...");
        Assets.loadAssets(/*splashScreen*/);
        Shader.setStandard(Assets.shader);
        //splashScreen.setVisible(false);

        player = /*sprite.getNewPlayer(this);*/new Player(this, new Texture("/animation_red.png"), 300, 300, 0 , 0);
        player.register(GameState.SINGLEPLAYER, GameState.MULTIPLAYER);

        Scene.setActive(MainMenu.INSTANCE);

        discordRP.start();

        /*getWindow().setScaleOnResize(true);
        getWindow().getFrame().setState(Frame.MAXIMIZED_BOTH);
        getWindow().getFrame().setTitle("Mamong us");
        getWindow().getFrame().setFocusTraversalKeysEnabled(false);
        setCapFps(false);*/

        /*Assets.singlePlayerButton.setX(Main.getMidX()-150);
        Assets.multiPlayerButton.setX(Main.getMidX()-150);
        Assets.optionsButton.setX(Main.getMidX()-150);
        Assets.quitButton.setX(Main.getMidX()-150);
        Assets.btmm.setX(Main.getMidX()-150);
        Assets.nameField.setX(Main.getMidX()-128);
        Assets.ipField.setX(Main.getMidX()-128);

        InputUtils.setInput(getInput());

        setupMainMenu();*/

        Stars.init();

        Assets.loadData();

        GameState.MAIN_MENU.enter();
    }

    @Override
    public void update() {
        OutputChat.update();
        Stars.update();

        if (Input.isKey(GLFW.GLFW_KEY_ESCAPE) && Main.gameState != GameState.MAIN_MENU) {
            if (Main.gameState == GameState.PAUSE) {
                Main.gameState = Main.lastState;
            } else {
                System.out.println("pause");
                GameState.PAUSE.enter();
            }
        }

        if (loadMP) {
            loadMP = false;
            connectMultiplayer();
        }

        Scene.getActive().update();

        discordRP.update(Assets.ipField.getText());
    }

    @Override
    public void render() {
        Renderer.render(Assets.bahnschrift32, "Fps: "+String.valueOf(com.siinus.Main.getFps()));
        System.out.println(com.siinus.Main.getFps());
        if (Main.gameState==GameState.MAIN_MENU) {
            Stars.render();
            com.siinus.Main.setBgColor(0xff000000);
        } else {
            com.siinus.Main.setBgColor(0xffffffff);
        }
        //Handler.render();
        Scene.getActive().render();
        if (Main.gameState == GameState.LOADING) {
            //getRenderer().drawText("Loading...",100, 100,0xff000000, null);
        }
        if (Main.gameState == GameState.ERROR) {
            //getRenderer().drawText("Connection refused!",100, 100,0xffff0000, null);
        }
    }

    @Override
    public void stop() {
        Scene.getActive().unload();
        discordRP.shutdown();
    }

    public void quit() {
        stop();
    }

    private void connectMultiplayer() {
        String[] ipText = Assets.ipField.getText().split(":");
        String ip = null;
        int port = 2119;
        if (ipText.length < 1) {
            Main.gameState = GameState.MAIN_MENU;
            Main.lastState = GameState.MAIN_MENU;
            return;
        } else if (ipText.length < 2) {
            ip = ipText[0].toLowerCase();
        } else {
            ip = ipText[0].toLowerCase();
            port = Integer.parseInt(ipText[1]);
        }
        if (Multiplayer.connect(ip, port)) {
            if (Assets.nameField.getText().length()<=0) {
                Assets.nameField.setText("Player"+((int) (Math.random()*100)));
            }
            Assets.saveData();
            Multiplayer.send("connect "+Assets.nameField.getText());
            Main.gameState = GameState.MULTIPLAYER;
            Main.lastState = GameState.MULTIPLAYER;
        } else {
            Main.gameState = GameState.ERROR;
        }
    }

    /*private void setupMainMenu() {
        width = (int) (getWindow().getWidth() / getWindow().getScale());
        height = (int) (getWindow().getHeight() / getWindow().getScale());
        scale = getWindow().getScale();
    }*/

    public void make() {
        init("Mamong Us", 1280, 720, false);
    }

    /*@Override
    protected void init() {
        super.init();
        normalRenderer = renderer;
        shaderRenderer = new ShaderRendererLite(window);
        shaderRenderer.setBgColor(0xffffffff);
        shaderRenderer.setAmbientLight((byte) 0x3f);
    }

    public void icon(Image icon) {
        setIconImage(icon);
    }*/
}

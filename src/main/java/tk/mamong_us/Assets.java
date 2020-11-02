package tk.mamong_us;

import com.siinus.simpleGrafix.gfx.*;
import com.siinus.simpleGrafix.sfx.SoundClip;
import org.json.simple.JSONObject;
import tk.mamong_us.gui.Button;
import tk.mamong_us.gui.ColorChooser;
import tk.mamong_us.gui.TextInput;
import tk.mamong_us.gui.TextQueue;
import tk.mamong_us.io.FileIO;

import java.io.IOException;
import java.util.ArrayList;

public class Assets {
    public static LowRamImage theSkeld;

    public static ImageTile mainMenu;
    public static ImageTile mpButton;
    public static ImageTile spButton;
    public static ImageTile optButton;
    public static ImageTile qButton;
    public static ImageTile spriteText;
    public static Image icon = new Image("/icon.png");

    public static ImageGif shhhGif;
    public static SoundClip shhhSound;
    public static ImageGif emergencyGif;
    public static SoundClip emergencySound;
    public static ImageGif discussGif;
    public static ImageGif deadBodyGif;
    public static SoundClip deadBodySound;
    public static ArrayList<ImageGif> killGifs;
    public static ArrayList<SoundClip> killSounds;
    public static SoundClip killSoundImp;
    public static SoundClip killSoundCm;
    public static SoundClip joinSound;

    public static Button singlePlayerButton;
    public static Button multiPlayerButton;
    public static Button optionsButton;
    public static Button quitButton;
    public static Button btmm;
    public static TextInput ipField;
    public static TextInput nameField;
    public static TextQueue tq;


    public static ColorChooser colorChooser = null;

    public static Font bahnschrift32;
    public static Font consolas72;

    public static void loadAssets(SplashScreen splashScreen) {
        try {
            theSkeld = new LowRamImage("/map_resized.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        pBpp(splashScreen);
        mainMenu = new ImageTile("/mainMenu.png",300,100);
        pBpp(splashScreen);
        mpButton = new ImageTile("/mpButton.png",300,100);
        pBpp(splashScreen);
        spButton = new ImageTile("/spButton.png",300,100);
        pBpp(splashScreen);
        optButton = new ImageTile("/options.png", 300, 100);
        pBpp(splashScreen);
        qButton = new ImageTile("/quit.png", 300, 100);
        pBpp(splashScreen);
        spriteText = new ImageTile("/text.png", 256, 64);
        pBpp(splashScreen, "Loading gifs...");

        shhhGif = new ImageGif("/videos/shhh.gif");
        pBpp(splashScreen);
        emergencyGif = new ImageGif("/videos/emergency.gif");
        pBpp(splashScreen);
        discussGif = new ImageGif("/videos/discuss.gif");
        pBpp(splashScreen);
        deadBodyGif = new ImageGif("/videos/deadbody.gif");
        pBpp(splashScreen);
        killGifs = new ArrayList<>();
        killSounds = new ArrayList<>();
        pBpp(splashScreen);

        killGifs.add(new ImageGif("/videos/tonguekill.gif"));
        pBpp(splashScreen);
        killGifs.add(new ImageGif("/videos/knifekill.gif"));
        pBpp(splashScreen);
        killGifs.add(new ImageGif("/videos/neckkill.gif"));
        pBpp(splashScreen);
        killGifs.add(new ImageGif("/videos/gunkill.gif"));
        pBpp(splashScreen, "Loading sounds...");

        killSounds.add(new SoundClip("/sounds/tonguekill.wav"));
        pBpp(splashScreen);
        killSounds.add(new SoundClip("/sounds/knifekill.wav"));
        pBpp(splashScreen);
        killSounds.add(new SoundClip("/sounds/neckkill.wav"));
        pBpp(splashScreen);
        killSounds.add(new SoundClip("/sounds/gunkill.wav"));
        pBpp(splashScreen);
        shhhSound = new SoundClip("/sounds/shhh.wav");
        pBpp(splashScreen);
        deadBodySound = new SoundClip("/sounds/deadbody.wav");
        pBpp(splashScreen);
        emergencySound = new SoundClip("/sounds/emergency.wav");
        pBpp(splashScreen);
        killSoundImp = new SoundClip("/sounds/killImp.wav");
        pBpp(splashScreen);
        killSoundCm = new SoundClip("/sounds/killCm.wav");
        pBpp(splashScreen);
        joinSound = new SoundClip("/sounds/join.wav");
        pBpp(splashScreen, "Loading UI assets...");

        singlePlayerButton = new Button(Main.getProgram(), Assets.spButton, 0, 275, 300, 100, GameState.SINGLEPLAYER::enter);
        singlePlayerButton.register(GameState.MAIN_MENU);
        pBpp(splashScreen);
        multiPlayerButton = new Button(Main.getProgram(), Assets.mpButton, 0, 375, 300, 100, GameState.MULTIPLAYER::enter);
        multiPlayerButton.register(GameState.MAIN_MENU);
        pBpp(splashScreen);
        optionsButton = new Button(Main.getProgram(), Assets.optButton, 0, 475, 300, 100, GameState.OPTIONS::enter);
        optionsButton.register(GameState.MAIN_MENU, GameState.PAUSE);
        pBpp(splashScreen);
        quitButton = new Button(Main.getProgram(), Assets.qButton, 0, 800, 300, 100, Main.getProgram()::quit);
        pBpp(splashScreen);
        quitButton.register(GameState.MAIN_MENU);
        btmm = new Button(Main.getProgram(), Assets.mainMenu, 0, 650, 256, 64, GameState.MAIN_MENU::enter);
        btmm.register(GameState.PAUSE, GameState.ERROR);
        pBpp(splashScreen);
        nameField = new TextInput(Main.getProgram(), Assets.spriteText, 0, 150, 256, 64, 0xff00ffff, null);
        nameField.register(GameState.MAIN_MENU);
        nameField.setDefaultText("Name");
        pBpp(splashScreen);
        ipField = new TextInput(Main.getProgram(), Assets.spriteText, 0, 650, 256, 64, 0xffffffff, null);
        ipField.register(GameState.MAIN_MENU);
        ipField.setDefaultText("IP:Port");
        pBpp(splashScreen);
        tq = new TextQueue();
        tq.endAction = GameState.MULTIPLAYER::enter;
        ipField.register(Assets.tq);
        pBpp(splashScreen, "Loading fonts...");

        colorChooser = null;

        bahnschrift32 = new Font("/bahnschrift.png", 37, 40);
        pBpp(splashScreen);
        consolas72 = new Font("/consolas72.png",72,78);
        pBpp(splashScreen);
    }

    public static void pBpp(SplashScreen s, String action) {
        s.progressBar.setValue(s.progressBar.getValue()+1);
        s.progressBar.setString(action);
    }

    public static void pBpp(SplashScreen s) {
        s.progressBar.setValue(s.progressBar.getValue()+1);
    }

    @SuppressWarnings("unchecked")
    static void saveData() {
        JSONObject root = new JSONObject();
        String[] ipText = ipField.getText().split(":");
        root.put("name", nameField.getText());
        root.put("ip", ipText[0]);
        root.put("port", ipText[1]);
        FileIO.saveJSON("./config.json", root);
    }

    static void loadData() {
        JSONObject root = FileIO.loadJSON("./config.json");
        if (root == null) {
            return;
        }
        nameField.setText((String) root.get("name"));
        ipField.setText(root.get("ip") + ":" + root.get("port"));
    }
}

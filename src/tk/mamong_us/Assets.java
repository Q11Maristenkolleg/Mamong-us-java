package tk.mamong_us;

import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.gfx.ImageGif;
import com.siinus.simpleGrafix.gfx.ImageTile;
import com.siinus.simpleGrafix.sfx.SoundClip;
import org.json.simple.JSONObject;
import tk.mamong_us.gui.Button;
import tk.mamong_us.gui.ColorChooser;
import tk.mamong_us.gui.TextInput;
import tk.mamong_us.gui.TextQueue;
import tk.mamong_us.io.FileIO;

import java.util.ArrayList;

public class Assets {
    //public static final Image theSkeld = new Image("/map_rescaled.jpg");

    public static ImageTile mainMenu;
    public static ImageTile mpButton;
    public static ImageTile spButton;
    public static ImageTile optButton;
    public static ImageTile qButton;
    public static ImageTile spriteText;
    public static Image icon = new Image("/icon.png");

    public static ImageGif shhhGif;
    public static ImageGif emergencyGif;
    public static ImageGif discussGif;
    public static ImageGif deadBodyGif;
    public static ArrayList<ImageGif> killGifs;
    public static ArrayList<SoundClip> killSounds;

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

        shhhGif = new ImageGif("/shhh/shhh.gif");
        pBpp(splashScreen);
        emergencyGif = new ImageGif("/emergency/emergency.gif");
        pBpp(splashScreen);
        discussGif = new ImageGif("/discuss/discuss.gif");
        pBpp(splashScreen);
        deadBodyGif = new ImageGif("/deadbody/deadbody.gif");
        pBpp(splashScreen);
        killGifs = new ArrayList<>();
        killSounds = new ArrayList<>();
        pBpp(splashScreen);

        killGifs.add(new ImageGif("/tonguekill/tonguekill.gif"));
        pBpp(splashScreen);
        killGifs.add(new ImageGif("/knifekill/knifekill.gif"));
        pBpp(splashScreen);
        killGifs.add(new ImageGif("/neckkill/neckkill.gif"));
        pBpp(splashScreen);
        killGifs.add(new ImageGif("/gunkill/gunkill.gif"));
        pBpp(splashScreen, "Loading sounds...");

        killSounds.add(new SoundClip("/tonguekill/tonguekillsound.wav"));
        pBpp(splashScreen);
        killSounds.add(new SoundClip("/knifekill/knifekillsound.wav"));
        pBpp(splashScreen);
        killSounds.add(new SoundClip("/neckkill/neckkkillsound.wav"));
        pBpp(splashScreen);
        killSounds.add(new SoundClip("/gunkill/gunkillsound.wav"));
        pBpp(splashScreen, "Loading UI assets...");

        singlePlayerButton = new Button(Main.getProgram(), Assets.spButton, 0, 275, 300, 100, Main.getProgram()::startSingleplayer);
        singlePlayerButton.register(GameState.MAIN_MENU);
        pBpp(splashScreen);
        multiPlayerButton = new Button(Main.getProgram(), Assets.mpButton, 0, 375, 300, 100, Main.getProgram()::startMultiplayer);
        multiPlayerButton.register(GameState.MAIN_MENU);
        pBpp(splashScreen);
        optionsButton = new Button(Main.getProgram(), Assets.optButton, 0, 475, 300, 100, Main.getProgram()::startOptions);
        optionsButton.register(GameState.MAIN_MENU, GameState.PAUSE);
        pBpp(splashScreen);
        quitButton = new Button(Main.getProgram(), Assets.qButton, 0, 800, 300, 100, Main.getProgram()::quit);
        pBpp(splashScreen);
        quitButton.register(GameState.MAIN_MENU);
        btmm = new Button(Main.getProgram(), Assets.mainMenu, 0, 650, 256, 64, Main.getProgram()::startMainMenu);
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
        tq.endAction = Main.getProgram()::startMultiplayer;
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

package tk.mamong_us;

import com.siinus.Font;
import com.siinus.Program;
import com.siinus.Shader;
import com.siinus.Texture;
import org.json.simple.JSONObject;
import tk.mamong_us.gui.Button;
import tk.mamong_us.gui.ColorChooser;
import tk.mamong_us.gui.TextInput;
import tk.mamong_us.gui.TextQueue;
import tk.mamong_us.io.FileIO;

public class Assets {
    //public static LowRamImage theSkeld;

    public static Texture mainMenu;
    public static Texture mpButton;
    public static Texture spButton;
    public static Texture optButton;
    public static Texture qButton;
    public static Texture spriteText;
    public static Texture icon;

    /*public static ImageGif shhhGif;
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
    public static SoundClip joinSound;*/
    static {
        Shader.setShaderRoot("../src/main/resources/shaders/");
    }
    public static Shader shader = new Shader("basic");

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
    /*public static Font consolas72;*/

    public static void loadAssets(/*SplashScreen splashScreen*/) {
        /*try {
            theSkeld = new LowRamImage("/map_resized.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        icon = new Texture("/icon.png");
        com.siinus.Main.getWindow().setIcon(icon);
        //pBpp(splashScreen);
        mainMenu = new Texture("/mainMenu.png");
        //pBpp(splashScreen);
        mpButton = new Texture("/mpButton.png");
        //pBpp(splashScreen);
        spButton = new Texture("/spButton.png");
        //pBpp(splashScreen);
        optButton = new Texture("/options.png");
        //pBpp(splashScreen);
        qButton = new Texture("/quit.png");
        //pBpp(splashScreen);
        spriteText = new Texture("/text.png");
        //pBpp(splashScreen, "Loading gifs...");

        /*shhhGif = new ImageGif("/videos/shhh.gif");
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
        joinSound = new SoundClip("/sounds/join.wav");*/
        //pBpp(splashScreen, "Loading UI assets...");

        singlePlayerButton = new Button(Main.getProgram(), Assets.spButton, 0, 275, 300, 100, GameState.SINGLEPLAYER::enter);
        singlePlayerButton.register(GameState.MAIN_MENU);
        //pBpp(splashScreen);
        multiPlayerButton = new Button(Main.getProgram(), Assets.mpButton, 0, 375, 300, 100, GameState.MULTIPLAYER::enter);
        multiPlayerButton.register(GameState.MAIN_MENU);
        //pBpp(splashScreen);
        optionsButton = new Button(Main.getProgram(), Assets.optButton, 0, 475, 300, 100, GameState.OPTIONS::enter);
        optionsButton.register(GameState.MAIN_MENU, GameState.PAUSE);
        //pBpp(splashScreen);
        quitButton = new Button(Main.getProgram(), Assets.qButton, 0, 800, 300, 100, Main.getProgram()::quit);
        //pBpp(splashScreen);
        quitButton.register(GameState.MAIN_MENU);
        btmm = new Button(Main.getProgram(), Assets.mainMenu, 0, 650, 256, 64, GameState.MAIN_MENU::enter);
        btmm.register(GameState.PAUSE, GameState.ERROR);
        //pBpp(splashScreen);
        nameField = new TextInput(Main.getProgram(), Assets.spriteText, 0, 150, 256, 64, 0xff00ffff, null);
        nameField.register(GameState.MAIN_MENU);
        nameField.setDefaultText("Name");
        //pBpp(splashScreen);
        ipField = new TextInput(Main.getProgram(), Assets.spriteText, 0, 650, 256, 64, 0xffffffff, null);
        ipField.register(GameState.MAIN_MENU);
        ipField.setDefaultText("IP[:Port]");
        //pBpp(splashScreen);
        tq = new TextQueue();
        tq.endAction = GameState.MULTIPLAYER::enter;
        ipField.register(Assets.tq);
        //pBpp(splashScreen, "Loading fonts...");

        colorChooser = null;

        bahnschrift32 = new Font(new Texture("/bahnschrift.png"), 0xff, '\0');
        /*pBpp(splashScreen);
        consolas72 = new Font("/consolas72.png",72,78);
        pBpp(splashScreen);*/
    }

    /*public static void pBpp(SplashScreen s, String action) {
        s.progressBar.setValue(s.progressBar.getValue()+1);
        s.progressBar.setString(action);
    }

    public static void pBpp(SplashScreen s) {
        s.progressBar.setValue(s.progressBar.getValue()+1);
    }*/

    @SuppressWarnings("unchecked")
    static void saveData() {
        JSONObject root = new JSONObject();
        String[] ipText = ipField.getText().split(":");
        root.put("name", nameField.getText());
        root.put("ip", ipText[0]);
        if (ipText.length > 1) {
            root.put("port", ipText[1]);
        }
        FileIO.saveJSON("./config.json", root);
    }

    static void loadData() {
        JSONObject root = FileIO.loadJSON("./config.json");
        if (root == null) {
            return;
        }
        ipField.setText(root.get("port")!=null?(root.get("ip")+":"+root.get("port")):(String)root.get("ip"));
    }
}

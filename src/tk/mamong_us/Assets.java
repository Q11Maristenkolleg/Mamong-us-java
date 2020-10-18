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

    public static final ImageTile objectSheet = new ImageTile("/objectSheet.png",126,126);
    public static final ImageTile mainMenu = new ImageTile("/mainMenu.png",300,100);
    public static final ImageTile mpButton = new ImageTile("/mpButton.png",300,100);
    public static final ImageTile spButton = new ImageTile("/spButton.png",300,100);
    public static final ImageTile spriteText = new ImageTile("/text.png", 256, 64);
    public static final Image icon = new Image("/icon2.png");

    public static ImageGif shhhGif = new ImageGif("/tonguekill.gif");
    public static ImageGif emergencyGif = new ImageGif("/tenor.gif");
    public static ImageGif discussGif = new ImageGif("/tenor.gif");
    public static ImageGif deadBodyGif = new ImageGif("/tenor.gif");
    public static ArrayList<ImageGif> killGifs = new ArrayList<>();
    public static ArrayList<SoundClip> killSounds = new ArrayList<>();

    static {
        killGifs.add(new ImageGif("/tonguekill.gif"));
        killGifs.add(new ImageGif("/tenor.gif"));
        killGifs.add(new ImageGif("/tenor.gif"));
        killGifs.add(new ImageGif("/tenor.gif"));

        killSounds.add(new SoundClip("/tongueKillSound.wav"));
        killSounds.add(new SoundClip("/tongueKillSound.wav"));
        killSounds.add(new SoundClip("/tongueKillSound.wav"));
        killSounds.add(new SoundClip("/tongueKillSound.wav"));
    }

    public static Button singlePlayerButton;
    public static Button multiPlayerButton;
    public static Button btmm;
    public static TextInput ipField;
    public static TextInput portField;
    public static TextInput nameField;
    public static TextQueue tq;


    public static ColorChooser colorChooser = null;


    public static Font arial32 = new Font("/font.png", 37, 37);
    public static Font bahnschrift32 = new Font("/bahnschrift.png", 37, 40);
    public static Font consolas72 = new Font("/consolas72.png",72,78);

    @SuppressWarnings("unchecked")
    static void saveData() {
        JSONObject root = new JSONObject();
        root.put("name", nameField.getText());
        root.put("ip", ipField.getText());
        root.put("port", portField.getText());
        FileIO.saveJSON("./config.json", root);
    }

    static void loadData() {
        JSONObject root = FileIO.loadJSON("./config.json");
        if (root == null) {
            return;
        }
        nameField.setText((String) root.get("name"));
        ipField.setText((String) root.get("ip"));
        portField.setText((String) root.get("port"));
    }
}

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

    public static final ImageTile mainMenu = new ImageTile("/mainMenu.png",300,100);
    public static final ImageTile mpButton = new ImageTile("/mpButton.png",300,100);
    public static final ImageTile spButton = new ImageTile("/spButton.png",300,100);
    public static final ImageTile spriteText = new ImageTile("/text.png", 256, 64);
    public static final Image icon = new Image("/icon.png");

    public static ImageGif shhhGif = new ImageGif("/shhh/shhh.gif");
    public static ImageGif emergencyGif = new ImageGif("/emergency/emergency.gif");
    public static ImageGif discussGif = new ImageGif("/discuss/discuss.gif");
    public static ImageGif deadBodyGif = new ImageGif("/deadbody/deadbody.gif");
    public static ArrayList<ImageGif> killGifs = new ArrayList<>();
    public static ArrayList<SoundClip> killSounds = new ArrayList<>();

    static {
        killGifs.add(new ImageGif("/tonguekill/tonguekill.gif"));
        killGifs.add(new ImageGif("/knifekill/knifekill.gif"));
        killGifs.add(new ImageGif("/neckkill/neckkill.gif"));
        killGifs.add(new ImageGif("/gunkill/gunkill.gif"));

        killSounds.add(new SoundClip("/tonguekill/tonguekillsound.wav"));
        killSounds.add(new SoundClip("/knifekill/knifekillsound.wav"));
        killSounds.add(new SoundClip("/neckkill/neckkkillsound.wav"));
        killSounds.add(new SoundClip("/gunkill/gunkillsound.wav"));
    }

    public static Button singlePlayerButton;
    public static Button multiPlayerButton;
    public static Button btmm;
    public static TextInput ipField;
    public static TextInput portField;
    public static TextInput nameField;
    public static TextQueue tq;


    public static ColorChooser colorChooser = null;

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

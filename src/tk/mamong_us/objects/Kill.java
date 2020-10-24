package tk.mamong_us.objects;

import com.siinus.simpleGrafix.gfx.ImageGif;
import com.siinus.simpleGrafix.sfx.SoundClip;
import tk.mamong_us.Assets;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.core.Handler;
import tk.mamong_us.core.ProgramObject;

import java.io.IOException;
import java.util.Random;

public class Kill implements ProgramObject {
    private static int timer = -1;
    private static ImageGif killGif;
    private static SoundClip killSound;

    @Override
    public void update() {
        if (timer == 0) {
            timer = -1;
            Main.gameState = Main.lastState;
            Handler.deleteObject(this);
        } else if (timer > 0) {
            timer--;
            if (timer%2==0) {
                killGif.nextImage();
            }
        }
    }

    @Override
    public void render() {
        Main.getProgram().getRenderer().drawImage(killGif, 0, 0);
    }

    public static void getKilled(String killer) {
        int killType = new Random().nextInt(4);
        killGif = Assets.killGifs.get(killType);
        killGif.setFrame(0);
        killSound = Assets.killSounds.get(killType);
        killSound.play();
        timer = killGif.getCount()*2;
        new Kill().register(GameState.KILL);
        Main.gameState = GameState.KILL;
    }
}

package tk.mamong_us.video;

import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafix.sfx.SoundClip;

import java.util.ArrayList;

public class Video {
    private final int frames;
    private final int fps;
    private final ArrayList<Image> images = new ArrayList<>();

    public Video(String folderPath, int fps) throws IllegalArgumentException {
        this.fps = fps;
        for (int i=0; true; i++) {
            String nts = (i<10?"0":"")+i;
            String fileName = folderPath+folderPath+nts+".png";
            try {
                images.add(new Image(fileName));
            }catch (Exception e) {
                frames = i;
                break;
            }
        }
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public int getFps() {
        return fps;
    }

    public int getFrames() {
        return frames;
    }
}

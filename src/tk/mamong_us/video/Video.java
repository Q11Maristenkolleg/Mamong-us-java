package tk.mamong_us.video;

import com.siinus.simpleGrafix.Renderer;
import com.siinus.simpleGrafix.gfx.Image;

import java.util.Arrays;

public class Video {
    private final String folderPath;
    private final int frames;
    private final int fps;

    private int cFrame;
    private int bFrame;
    private Image cImage;

    private Image[] bufferedFrames;

    public Video(String folderPath, int fps, int frames) throws IllegalArgumentException {
        this.folderPath = folderPath;
        this.frames = frames;
        this.fps = fps;
        this.bufferedFrames = new Image[frames];
        constructor(folderPath, fps, frames);
    }

    public void constructor(String folderPath, int fps, int frames) {
        cFrame = 0;
        bFrame = 1;
        this.cImage = new Image(folderPath+folderPath+"00.png");
        bufferedFrames[0] = cImage;
        while (bFrame<Math.min(20, frames)) {
            String nts = ((bFrame < 10 ? "0" : "") + bFrame);
            String fileName = folderPath+folderPath+nts+".png";
            bufferedFrames[bFrame] = new Image(fileName);
            bFrame++;
        }
    }

    /*public ArrayList<Image> getImages() {
        return images;
    }*/

    public boolean loadFrame() {
        if (bFrame >= frames-1) {
            return false;
        }
        bFrame++;
        try {
            bufferedFrames[bFrame] = new Image(folderPath + folderPath + ((bFrame < 10 ? "0" : "") + bFrame) + ".png");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void renderFrame(Renderer renderer, int offX, int offY) {
        if (cFrame >= frames-1) {
            return;
        }
        System.out.println("Render "+cFrame);
        Image tImage = bufferedFrames[cFrame];
        System.out.println(tImage);
        if (tImage != null) {
            cImage = tImage;
        }
        renderer.drawImage(cImage, offX, offY);
        bufferedFrames[cFrame] = null;
        cFrame++;
    }

    public int getFrame() {
        return cFrame;
    }

    public Image getImage() {
        return cImage;
    }

    public int getFps() {
        return fps;
    }

    public int getFrames() {
        return frames;
    }

    public String getFolderPath() {
        return folderPath;
    }

    /*public Queue<Image> getBufferedFrames() {
        return bufferedFrames;
    }*/

    public Image[] getBufferedFrames() {
        return bufferedFrames;
    }
}

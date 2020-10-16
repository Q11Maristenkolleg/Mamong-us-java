package tk.mamong_us.video;

import com.siinus.simpleGrafix.Renderer;
import com.siinus.simpleGrafix.gfx.Image;

import java.util.LinkedList;

public class Video {
    private final String folderPath;
    private final int frames;
    private final int fps;

    private int cFrame;
    private int bFrame;
    private Image cImage;

    private LinkedList<Image> bufferedFrames = new LinkedList<>();

    public Video(String folderPath, int fps, int frames) throws IllegalArgumentException {
        this.folderPath = folderPath;
        this.frames = frames;
        this.fps = fps;
        constructor(folderPath, fps, frames);
    }

    public void constructor(String folderPath, int fps, int frames) {
        cFrame = 0;
        bFrame = 0;
        this.cImage = new Image(folderPath+folderPath+"00.png");
        bufferedFrames = new LinkedList<>();
        while (bFrame<Math.min(10, frames)) {
            String nts = ((bFrame < 10 ? "0" : "") + bFrame);
            String fileName = folderPath+folderPath+nts+".png";
            bFrame++;
            bufferedFrames.add(new Image(fileName));
        }
    }

    /*public ArrayList<Image> getImages() {
        return images;
    }*/

    public boolean nextFrame() {
        if (bFrame >= frames-1) {
            return false;
        }
        cFrame++;
        bFrame++;
        System.out.println("Load "+bFrame);
        Image tImage = bufferedFrames.poll();
        if (tImage != null) {
            cImage = tImage;
        }
        try {
            new Thread(()->bufferedFrames.add(new Image(folderPath + folderPath + ((bFrame < 10 ? "0" : "") + bFrame) + ".png"))).start();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void renderFrame(Renderer renderer, int offX, int offY) {
        System.out.println("Render "+cFrame);
        //cImage = bufferedFrames.poll();
        if (cImage == null) {
            return;
        }
        renderer.drawImage(cImage, offX, offY);
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
}

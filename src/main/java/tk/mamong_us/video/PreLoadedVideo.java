package tk.mamong_us.video;

import com.siinus.simpleGrafix.Renderer;
import com.siinus.simpleGrafix.gfx.Image;

public class PreLoadedVideo extends Video {

    public PreLoadedVideo(String folderPath, int fps, int frames) throws IllegalArgumentException {
        super(folderPath, fps, frames);
        cFrame = 0;
        bFrame = 0;
        this.cImage = new Image(folderPath+folderPath+"00.png");
        bufferedFrames[0] = cImage;
        while (true) {
            if (!loadFrame()) break;
        }
    }

    @Override
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
        cFrame++;
    }

    public void resetFrames() {
        cFrame = 0;
    }
}

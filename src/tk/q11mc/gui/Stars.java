package tk.q11mc.gui;

import com.siinus.simpleGrafix.Renderer;
import tk.q11mc.Main;

import java.util.Random;

public class Stars {
    public static final byte NUM_STARS = 50;
    private static int[] x = new int[NUM_STARS], y = new int[NUM_STARS], s = new int[NUM_STARS];
    private static final Random random = new Random();
    private static final Renderer renderer = Main.getInstance().getRenderer();

    public static void init() {
        for (int i=0; i<NUM_STARS; i++) {
            x[i] = random.nextInt(Main.width);
            y[i] = random.nextInt(Main.height);
            s[i] = random.nextInt(5);
        }
    }


    public static void update() {
        for (int i=0; i<NUM_STARS; i++) {
            x[i] += s[i];
            if (x[i] > Main.width) {
                x[i] = 0;
                y[i] = random.nextInt(Main.height);
            }
        }
    }

    public static void render() {
        for (int i=0; i<NUM_STARS; i++) {
            for (int xr=0; xr<s[i]; xr++) {
                for (int yr=0; yr<s[i]; yr++) {
                    renderer.setPixel(x[i]+xr, y[i]+yr, 0xffffffff);
                }
            }
        }
    }
}

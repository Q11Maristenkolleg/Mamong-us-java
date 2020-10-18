package tk.mamong_us.gui;

import com.siinus.simpleGrafix.Renderer;
import tk.mamong_us.Main;
import tk.mamong_us.Program;

import java.util.Random;

public class Stars {
    public static final int NUM_STARS = 150;
    public static final int SPEED = 1;
    private static final int[] x = new int[NUM_STARS], y = new int[NUM_STARS], s = new int[NUM_STARS];
    private static final Random random = new Random();
    private static final Renderer renderer = Main.getProgram().getRenderer();

    public static void init() {
        for (int i=0; i<NUM_STARS; i++) {
            x[i] = random.nextInt(Program.width);
            y[i] = random.nextInt(Program.height);
            s[i] = random.nextInt(5);
        }
    }


    public static void update() {
        for (int i=0; i<NUM_STARS; i++) {
            x[i] += s[i] * SPEED;
            if (x[i] > Program.width) {
                x[i] = 0;
                y[i] = random.nextInt(Program.height);
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

package tk.mamong_us.core;

import com.siinus.Input;
import org.lwjgl.glfw.GLFW;

import java.nio.FloatBuffer;

public class Controls {
    public static float x, y;

    public static void init() {
        x = y = 0;
    }

    public static void poll() {
        if (Input.isJoystickConnected(0)) {
            joystickControls();
        } else {
            keyboardControls();
        }
    }

    public static void shutdown() {

    }

    private static void keyboardControls() {
        x = (float) ((int) x);
        y = (float) ((int) y);
        if (Input.isKey(GLFW.GLFW_KEY_W)) {
            y++;
        }
        if (Input.isKey(GLFW.GLFW_KEY_A)) {
            x--;
        }
        if (Input.isKey(GLFW.GLFW_KEY_S)) {
            y--;
        }
        if (Input.isKey(GLFW.GLFW_KEY_D)) {
            x++;
        }
    }

    private static void joystickControls() {
        FloatBuffer axis = Input.getJoystickAxis(0);
        x += axis.get();
        y += axis.get();
    }
}

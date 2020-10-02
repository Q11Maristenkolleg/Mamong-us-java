package tk.q11mc;

import com.siinus.simpleGrafix.input.Input;
import org.jetbrains.annotations.NotNull;

public class InputUtils {
    public static Input input;
    private static boolean kp = false;
    private static boolean kl = false;
    private static byte kc = 0;
    private static boolean bp = false;
    private static boolean bl = false;

    public static void update(@NotNull Input input) {
        kl = kp;
        bl = bp;
        kp = input.isKeyPressed();
        bp = input.isButtonPressed();
    }

    public static void setInput(Input input) {
        InputUtils.input = input;
    }

    public static boolean isKeyDown() {
        return kp && !kl;
    }

    public static boolean isButtonDown() {
        return bp && !bl;
    }

    public static boolean isKeyDown(int keyCode) {
        return input.isKeyPressed(keyCode) && isKeyDown();
    }

    public static boolean isKeyPressed(int keyCode) {
        return input.isKeyPressed(keyCode);
    }

    public static boolean isKeyUp(int keyCode) {
        return !input.isKeyPressed(keyCode) && isKeyUp();
    }

    public static boolean isButtonPressed(int button) {
        return input.isButtonPressed(button);
    }

    public static boolean isButtonDown(int button) {
        return input.isButtonPressed(button) && isButtonDown();
    }

    public static boolean isButtonUp(int button) {
        return !input.isButtonPressed(button) && isButtonUp();
    }

    public static boolean isKeyPressed() {
        return kp;
    }

    private static boolean isKeyUp() {
        return !kp && kl;
    }

    public static boolean isButtonPressed() {
        return bp;
    }

    public static boolean isButtonUp() {
        return !bp && bl;
    }

    public static boolean isKeyTyped() {
        if (isKeyDown()) {
            kc = (byte) 20;
            return true;
        }
        if (isKeyPressed()) {
            if (kc > 0) {
                kc--;
                return false;
            }
            kc = (byte) 2;
            return true;
        }
        return false;
    }

    public static boolean isKeyTyped(int keyCode) {
        return isKeyTyped() && input.isKeyPressed(keyCode);
    }

    public static char getLastKey() {
        return input.getLastKey();
    }
}

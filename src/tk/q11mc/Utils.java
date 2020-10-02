package tk.q11mc;

import com.siinus.simpleGrafix.input.Input;

public class Utils {

    public static int getKey(Input input) {
        if (!input.isKeyDown()) {
            return 0;
        }
        for (int k = 0x2c; k<=0x5a; k++) {
            if (input.isKeyDown(k)) {
                return k;
            }
        }
        return 0;
    }
}

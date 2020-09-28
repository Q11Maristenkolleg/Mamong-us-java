package tk.q11mc.objects;

import java.awt.*;

public interface Collideable {

    Rectangle getBounds();

    default boolean intersects(Rectangle other) {
        return getBounds().intersects(other);
    }
}

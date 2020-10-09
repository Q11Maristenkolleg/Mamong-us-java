package tk.mamong_us.objects;

import java.awt.*;

public interface Collideable {

    Rectangle getBounds();

    default boolean intersects(Rectangle other) {
        return getBounds().intersects(other);
    }
}

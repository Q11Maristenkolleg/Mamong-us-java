package tk.q11mc;

public interface ICollideable {

    Side collides(GameObject other);

    enum Side {
        TOP, LEFT, BOTTOM, RIGHT, NONE
    }
}

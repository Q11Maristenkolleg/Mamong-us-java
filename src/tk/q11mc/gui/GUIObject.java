package tk.q11mc.gui;

import com.siinus.simpleGrafix.gfx.Image;
import tk.q11mc.Handleable;
import tk.q11mc.Main;
import tk.q11mc.core.Handler;

public abstract class GUIObject implements Handleable {
    protected Main program;
    protected Image sprite;
    protected int x, y;
    protected int width, height;

    /**
     * Creates a new GUI object.
     *
     * @param program The associated program
     * @param sprite The texture
     * @param x The offset x
     * @param y The offset y
     * @param width The width of the bounding box
     * @param height The width of the bounding box
     */
    public GUIObject(Main program, Image sprite, int x, int y, int width, int height) {
        this.program = program;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        Handler.addObject(this);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isMouseOver() {
        int mx = program.getInput().getMouseX();
        int my = program.getInput().getMouseY();
        return mx >= x && mx <= (x + width) && my >= y && my <= (y + height);
    }
}

package tk.mamong_us.gui;

import com.siinus.Input;
import com.siinus.Texture;
import tk.mamong_us.Program;
import tk.mamong_us.core.ProgramObject;

public abstract class GUIObject implements ProgramObject {
    protected Program program;
    protected Texture sprite;
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
    public GUIObject(Program program, Texture sprite, int x, int y, int width, int height) {
        this.program = program;
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        int mx = (int) Input.getMouseX();
        int my = (int) Input.getMouseY();
        return mx >= x && mx <= (x + width) && my >= y && my <= (y + height);
    }
}

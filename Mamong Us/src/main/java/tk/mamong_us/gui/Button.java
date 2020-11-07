package tk.mamong_us.gui;

import com.siinus.Input;
import com.siinus.Renderer;
import com.siinus.Texture;
import tk.mamong_us.Program;

public class Button extends GUIObject {
    private boolean isMouseOver= false;
    private final Texture texture;
    private final Runnable click;


    /**
     * Creates a new Button object.
     *  @param program The associated program
     * @param sprite  The texture
     * @param x       The offset x
     * @param y       The offset y
     * @param width   The width of the bounding box
     * @param height  The width of the bounding box
     */
    public Button(Program program, Texture sprite, int x, int y, int width, int height, Runnable action) {
        super(program, sprite, x, y, width, height);
        this.texture = sprite;
        this.click = action;
    }


    @Override
    public void update() {
        if (isMouseOver = isMouseOver()) {
            if (Input.isMouseButton(1)) {
                click.run();
            }
        }
    }

    @Override
    public void render() {
        Renderer.render(texture/*x, y, 0, isMouseOver?(InputUtils.isButtonPressed(1)?2:0):1*/);
    }
}

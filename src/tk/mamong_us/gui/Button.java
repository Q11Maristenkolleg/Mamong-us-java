package tk.mamong_us.gui;

import com.siinus.simpleGrafix.gfx.ImageTile;
import tk.mamong_us.GameState;
import tk.mamong_us.InputUtils;
import tk.mamong_us.Main;

public class Button extends GUIObject {
    private boolean isMouseOver= false;
    private final ImageTile image;
    private final Runnable click;


    /**
     * Creates a new Button object.
     *
     * @param program The associated program
     * @param sprite  The texture
     * @param x       The offset x
     * @param y       The offset y
     * @param width   The width of the bounding box
     * @param height  The width of the bounding box
     */
    public Button(Main program, ImageTile sprite, int x, int y, int width, int height, Runnable action) {
        super(program, sprite, x, y, width, height);
        this.image = sprite;
        this.click = action;
    }


    @Override
    public void update() {
        if (isMouseOver = isMouseOver()) {
            if (InputUtils.isButtonUp(1)) {
                click.run();
            }
        }
    }

    @Override
    public void render() {
        program.getRenderer().drawImageTile(image, x, y, 0, isMouseOver?(InputUtils.isButtonPressed(1)?2:0):1);
    }
}

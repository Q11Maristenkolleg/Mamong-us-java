package tk.q11mc.gui;

import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.q11mc.Main;
import tk.q11mc.Utils;

public class TextInput extends GUIObject {
    private final ImageTile image;
    private final int color;
    private final Font font;

    private boolean isMouseOver =  false;
    private boolean activated = false;
    private StringBuilder text = new StringBuilder();

    @Nullable TextQueue queue = null;

    /**
     * Creates a new GUI object.
     *
     * @param program The associated program
     * @param sprite  The texture
     * @param x       The offset x
     * @param y       The offset y
     * @param width   The width of the bounding box
     * @param height  The width of the bounding box
     * @param textColor The color of the text
     */
    public TextInput(Main program, ImageTile sprite, int x, int y, int width, int height, int textColor, Font font) {
        super(program, sprite, x, y, width, height);
        this.image = sprite;
        color = textColor;
        this.font = font;
    }

    @Override
    public void update() {
        isMouseOver = isMouseOver();
        if (program.getInput().isButtonDown(1)) {
            activated = isMouseOver;
        }
        if (activated) {
            if (program.getInput().isKeyDown('\b')) {
                text.deleteCharAt(text.length()-1);
                return;
            }
            if (program.getInput().isKeyDown('\t') || program.getInput().isKeyDown(0x70)) {
                if (queue != null) {
                    try {
                        queue.fields.get(queue.fields.indexOf(this)+1).activated = true;
                    } catch (IndexOutOfBoundsException e) {
                        return;
                    }
                    activated = false;
                }
                return;
            }
            if (program.getInput().isKeyDown('\n')) {
                if (queue != null) {
                    if (queue.fields.size()-1 <= queue.fields.indexOf(this)) {
                        queue.endAction.run();
                        activated = false;
                    }
                }
                return;
            }
            int kd;
            if ((kd = Utils.getKey(program.getInput())) > 0) {
                text.append((char) kd);
            }
        }
    }

    @Override
    public void render() {
        program.getRenderer().drawImageTile(image, x, y, 0, activated?2:(isMouseOver?1:0));
        program.getRenderer().drawText(text.toString(), x + 10, y + 10, color, font);
    }

    public StringBuilder getText() {
        return text;
    }

    public boolean register(@NotNull TextQueue queue) {
        if (queue.fields.contains(this)) {
            return false;
        }
        if (this.queue != null) {
            return false;
        }
        this.queue = queue;
        queue.fields.add(this);
        return true;
    }
}

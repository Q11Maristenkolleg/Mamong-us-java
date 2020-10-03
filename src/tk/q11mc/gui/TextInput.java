package tk.q11mc.gui;

import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.ImageTile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.q11mc.InputUtils;
import tk.q11mc.Main;

public class TextInput extends GUIObject {
    private final ImageTile image;
    private final int color;
    private final Font font;
    @Nullable
    private String defaultText = null;

    private boolean isMouseOver = false;
    private boolean activated = false;
    private StringBuilder text = new StringBuilder();
    private boolean shift = false;

    @Nullable TextQueue queue = null;

    /**
     * Creates a new GUI object.
     *
     * @param program   The associated program
     * @param sprite    The texture
     * @param x         The offset x
     * @param y         The offset y
     * @param width     The width of the bounding box
     * @param height    The width of the bounding box
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
            char kd = program.getInput().getLastKey();
            if (((byte) kd) == (byte) -1) {
                shift = true;
                return;
            }
            if (InputUtils.isKeyTyped()){
                if (kd == '\b') {
                    if (text.length() <= 0) {
                        return;
                    }
                    text.deleteCharAt(text.length() - 1);
                    return;
                }
                if (kd == '\t') {
                    if (queue != null) {
                        try {
                            queue.fields.get(queue.fields.indexOf(this) + 1).activated = true;
                        } catch (IndexOutOfBoundsException e) {
                            return;
                        }
                        activated = false;
                    }
                    return;
                }
                if (kd == '\n') {
                    if (queue != null) {
                        if (queue.fields.size() - 1 <= queue.fields.indexOf(this)) {
                            queue.endAction.run();
                            activated = false;
                        }
                    }
                    return;
                }
                text.append(shift ? String.valueOf(kd) : (String.valueOf(kd).toLowerCase()));
            }
        }
    }

    @Override
    public void render() {
        program.getRenderer().drawImageTile(image, x, y, 0, activated ? 2 : (isMouseOver ? 1 : 0));
        program.getRenderer().drawText(text.length() <= 0 ? (defaultText == null ? "" : defaultText) : text.toString(), x + 10, y + 10, text.length() <= 0 ? 0xff7f7f7f : color, font);
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String text) {
        this.text = new StringBuilder(text);
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
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

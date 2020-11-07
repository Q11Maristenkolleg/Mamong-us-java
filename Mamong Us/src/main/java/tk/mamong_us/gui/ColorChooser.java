package tk.mamong_us.gui;

import com.siinus.Input;
import com.siinus.Model;
import com.siinus.Renderer;
import com.siinus.Texture;
import tk.mamong_us.Assets;
import tk.mamong_us.Program;

public class ColorChooser extends GUIObject {
    public static Texture sprite = new Texture("/color_chooser.png");
    public static Texture outline = new Texture("/chooser_outline.png");

    private int cx, cy;

    /**
     * Creates a new Color Chooser object.
     *
     * @param program The associated program
     */
    public ColorChooser(Program program/*, @NotNull PlayerSprite color*/) {
        super(program, sprite, 0, 0, 380, 500/*(int) (program.getWindow().getWidth()/(program.getWindow().getScale()*2)-190), (int) (program.getWindow().getHeight()/(program.getWindow().getScale()*2)-250), 380, 500*/);
        cx = x + 17/* + 120 * color.cx*/;
        cy = y + 17/* + 120 * color.cy*/;
    }

    @Override
    public void update() {
        if (isMouseOver() && Input.isMouseButton(1)) {
            int mx = (int) (Input.getMouseX()-x-20);
            if (mx%120>100) {
                return;
            }
            int my = (int) (Input.getMouseY()-y-20);
            if (my%120>100) {
                return;
            }
            mx /= 120;
            my /= 120;
            /*program.sprite = PlayerSprite.fromCxCy(mx, my);
            if (program.sprite != null) {
                program.getPlayer().setSprite(program.sprite);
                Multiplayer.send("color " + program.sprite.name());
            }*/
            cx = x + 17 + mx * 120;
            cy = y + 17 + my * 120;
        } else if (Input.isMouseButton(1)) {
            //Handler.deleteObject(this);
            Assets.colorChooser = null;
        }
    }

    @Override
    public void render() {
        /*program.getRenderer().drawText("Choose a color!", x+20, y-40, 0xff000000, null);
        program.getRenderer().drawImage(sprite, x, y);
        program.getRenderer().drawImage(outline, cx, cy);*/
        Renderer.render(sprite);
        Renderer.render(outline);
        /*if (program.getRenderer() instanceof ShaderRendererLite) {
            ((ShaderRendererLite) program.getRenderer()).makeLightImage(sprite, x, y);
        }*/
    }
}

package tk.q11mc;

import com.siinus.simpleGrafixShader.ShaderImage;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    public static List<GameObject> objects = new ArrayList<>();

    protected Main program;
    protected ShaderImage sprite;
    protected int width, height;
    protected int x=0, y=0;

    public GameObject(Main program, ShaderImage sprite, int width, int height) {
        this.program = program;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
    }

    public abstract void update();

    public abstract void render();
}

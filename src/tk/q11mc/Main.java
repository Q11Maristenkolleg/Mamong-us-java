package tk.q11mc;

import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafixShader.Light;
import com.siinus.simpleGrafixShader.ShaderProgram;

public class Main extends ShaderProgram {
    Image grassBlock = new Image("/grass.png");
    Light light = new Light(150, 0xffffffff);

    int x = 0;

    public static void main(String[] args) {
        new Main().initShader();
    }

    public Main() {
        setIconImage(grassBlock);
    }

    @Override
    public void start() {

    }

    @Override
    public void update() {
        x++;
    }

    @Override
    public void render() {
        getRenderer().drawImage(grassBlock, x, 100);
        getShaderRenderer().drawLight(light, getInput().getMouseX(), getInput().getMouseY());
    }

    @Override
    public void stop() {

    }
}

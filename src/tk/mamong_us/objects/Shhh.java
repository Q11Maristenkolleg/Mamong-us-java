package tk.mamong_us.objects;

import com.siinus.simpleGrafix.gfx.Image;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.core.ProgramObject;
import tk.mamong_us.game.MamongUsGame;

public class Shhh implements ProgramObject {


    private static int shhhBuffer = -1;
    private static boolean shhhImp = false;

    public static Image shhhImage = new Image("/shhh.png");

    @Override
    public void update() {
        if (shhhBuffer > 0) {
            shhhBuffer--;
        } else if (shhhBuffer == 0){
            Main.gameState = Main.lastState;
            shhhBuffer = -1;
        }
    }

    @Override
    public void render() {
        if (shhhBuffer > 0) {
            Main.getInstance().getRenderer().setBgColor(0xff000000);
            if (shhhBuffer > 100) {
                Main.getInstance().getRenderer().drawImage(shhhImage, 450, 0);
            } else {
                Main.getInstance().getRenderer().drawText(shhhImp?"Impostor":"Crewmate", 900, 250, shhhImp?0xffff0000:0xff00ffff, null);
                if (!shhhImp) {
                    Main.getInstance().getRenderer().drawText("There are " + MamongUsGame.vars.impostors + " Impostors among us", 700, 300, 0xffff0000, null);
                }
            }
        }
    }

    public static void shhh(boolean imp) {
        Main.gameState = GameState.SHHH;
        shhhImp = imp;
        shhhBuffer = 250;
        new Shhh().register(GameState.SHHH);
    }

}

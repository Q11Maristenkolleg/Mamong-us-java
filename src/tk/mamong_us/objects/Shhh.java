package tk.mamong_us.objects;

import com.siinus.simpleGrafix.gfx.Font;
import tk.mamong_us.Assets;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.core.Handler;
import tk.mamong_us.core.ProgramObject;
import tk.mamong_us.game.MamongUsGame;

import java.io.IOException;
import java.util.ArrayList;

public class Shhh implements ProgramObject {
    private static int shhhBuffer = -1;
    private static boolean shhhImp = false;
    private static ArrayList<OtherPlayer> shhhMates = new ArrayList<>();

    @Override
    public void update() {
        if (shhhBuffer > 0) {
            if (shhhBuffer%3==0)
            try {
                Assets.shhhGif.nextImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            shhhBuffer--;
        } else if (shhhBuffer == 0){
            Main.gameState = Main.lastState;
            Handler.deleteObject(this);
        }
    }

    @Override
    public void render() {
        if (shhhBuffer > 0) {
            Main.getProgram().getRenderer().setBgColor(0xff000000);
            if (shhhBuffer > 100) {
                Main.getProgram().getRenderer().drawImage(Assets.shhhGif, 0, 0);
            } else {
                Main.getProgram().getRenderer().drawText(shhhImp?"Impostor":"Crewmate", Main.getMidX()-Font.getStandard().getPixelsOfText(shhhImp?"Impostor":"Crewmate"), 220, shhhImp?0xffff0000:0xff00ffff, Assets.consolas72);
                Main.getProgram().getRenderer().drawImageTile(Main.getProgram().player.spriteSheet,Main.getMidX()-150, Main.getMidY()-150, 0, 4);
                int mox = 1;
                if (shhhMates.size()>0) {
                    for (OtherPlayer mate : shhhMates) {
                        if (mate == null) continue;
                        Main.getProgram().getRenderer().drawImageTile(mate.spriteSheet, Main.getMidX() - 150 + (150 * mox), Main.getMidY() - 150 - (50-Math.abs(mox)*25), mox>0?0:1, 4);
                        if (shhhImp) {
                            mate.nameColor = 0xffff0000;
                        }
                        mox = mox > 0 ? -mox : -mox + 1;
                    }
                }
                if (!shhhImp) {
                    Main.getProgram().getRenderer().drawText("There are " + MamongUsGame.vars.impostors + " Impostors mamong us", Main.getMidX()-Font.getStandard().getPixelsOfText("There are " + MamongUsGame.vars.impostors + " Impostors mamong us")/2, 300, 0xffff0000, null);
                }
            }
        }
    }

    public static void shhh(boolean imp, ArrayList<OtherPlayer> mates) {
        Assets.shhhGif.setFrame(0);
        Main.gameState = GameState.SHHH;
        shhhImp = imp;
        shhhMates = mates;
        shhhBuffer = Assets.shhhGif.getCount()*3+100;
        new Shhh().register(GameState.SHHH);
    }

}

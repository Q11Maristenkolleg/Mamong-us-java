package tk.mamong_us.objects;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.core.Handler;
import tk.mamong_us.core.ProgramObject;
import tk.mamong_us.game.MamongUsGame;

import java.util.ArrayList;

public class Shhh implements ProgramObject {
    private static int shhhBuffer = -1;
    private static boolean shhhImp = false;
    private static ArrayList<OtherPlayer> shhhMates = new ArrayList<>();

    private static boolean load = false;

    @Override
    public void update() {
        System.out.println(shhhBuffer);
        if (shhhBuffer == Main.shhhVideo.getFrames()+100) {
            //Main.shhhVideo.getSoundTrack().play();
        }
        if (load) {
            if (!Main.shhhVideo.nextFrame()) {
                load = false;
                new Thread(()->Main.shhhVideo.constructor(Main.shhhVideo.getFolderPath(), Main.shhhVideo.getFps(), Main.shhhVideo.getFrames())).start();
            }
        }
        if (shhhBuffer > 0) {
            shhhBuffer--;
        } else if (shhhBuffer == 0){
            Main.gameState = Main.lastState;
            Handler.deleteObject(this);
        }
    }

    @Override
    public void render() {
        if (shhhBuffer > 0) {
            Main.getInstance().getRenderer().setBgColor(0xff000000);
            if (shhhBuffer > 100) {
                Main.shhhVideo.renderFrame(Main.getInstance().getRenderer(), 0, -50);
            } else {
                Main.getInstance().getRenderer().drawText(shhhImp?"Impostor":"Crewmate", 900, 250, shhhImp?0xffff0000:0xff00ffff, null);
                Main.getInstance().getRenderer().drawImageTile(Main.getInstance().player.spriteSheet,Main.getMidX()-150, Main.getMidY()-150, 0, 4);
                int mox = 1;
                if (shhhMates.size()>0) {
                    for (OtherPlayer mate : shhhMates) {
                        if (mate == null) continue;
                        Main.getInstance().getRenderer().drawImageTile(mate.spriteSheet, Main.getMidX() - 150 + (150 * mox), Main.getMidY() - 150, 0, 4);
                        mox = mox > 0 ? -mox : -mox + 1;
                    }
                }
                if (!shhhImp) {
                    Main.getInstance().getRenderer().drawText("There are " + MamongUsGame.vars.impostors + " Impostors among us", 700, 300, 0xffff0000, null);
                }
            }
        }
    }

    public static void shhh(boolean imp, ArrayList<OtherPlayer> mates) {
        Main.gameState = GameState.SHHH;
        shhhImp = imp;
        shhhMates = mates;
        load = true;
        shhhBuffer = Main.shhhVideo.getFrames()+100;
        new Shhh().register(GameState.SHHH);
    }

}

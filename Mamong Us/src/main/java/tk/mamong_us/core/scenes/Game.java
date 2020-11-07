package tk.mamong_us.core.scenes;

import com.siinus.Input;
import org.lwjgl.glfw.GLFW;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.core.Scene;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.gui.Button;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.OtherPlayer;
import tk.mamong_us.objects.Player;

public class Game extends Scene {
    public static final Game INSTANCE = new Game();

    public Player player;

    @Override
    public void load() {

    }

    @Override
    public void update() {
        if (Main.mpState == GameState.MultiplayerState.GAME) {
            if (MamongUsGame.impostor) {
                if (MamongUsGame.killCd > 0) {
                    if (MamongUsGame.killCdB == 0) {
                        MamongUsGame.killCdB = 60;
                        MamongUsGame.killCd--;
                    } else {
                        MamongUsGame.killCdB--;
                    }
                } else {
                    MamongUsGame.killCdB = 60;
                }
            }
            if (Input.isKey(GLFW.GLFW_KEY_F) && MamongUsGame.impostor && MamongUsGame.killCd == 0) {
                boolean canKill = false;
                String toKill = null;
                int tpx = 0, tpy = 0;
                int distanceToTarget = MamongUsGame.vars.kill_dst.dist;
                for (OtherPlayer o : OtherPlayer.onlinePlayers) {
                    if (Math.hypot(o.getX() - player.getX(), o.getY() - player.getY()) < distanceToTarget && !MamongUsGame.mates.contains(o)) {
                        canKill = true;
                        toKill = o.getIp();
                        tpx = o.getX();
                        tpy = o.getY();
                        distanceToTarget = (int) Math.hypot(o.getX() - player.getX(), o.getY() - player.getY());
                    }
                }
                if (canKill) {
                    Multiplayer.send("kill " + toKill);
                    //Assets.killSoundImp.play(); TODO
                    player.setX(tpx);
                    player.setY(tpy);
                    MamongUsGame.killCd = (int) MamongUsGame.vars.kill_cd;
                    MamongUsGame.killCdB = ((int) (MamongUsGame.vars.kill_cd * 100)) % 100;
                }
            }
        }
        if (Input.isKey(GLFW.GLFW_KEY_B)) {
            Multiplayer.send("create");
        }
        if (Main.mpState == GameState.MultiplayerState.LOBBY && Input.isKey(GLFW.GLFW_KEY_E)) {
            Multiplayer.send("start");
        }
        /*if (Input.isKey(GLFW.GLFW_KEY_C)) {
            if (Assets.colorChooser == null) {
                Assets.colorChooser = new ColorChooser(this/*, sprite == null ? sprite = PlayerSprite.RED : sprite);
                Assets.colorChooser.register(GameState.MULTIPLAYER);
            } else {
                //Handler.deleteObject(Assets.colorChooser);
                Assets.colorChooser = null;
            }
        }*/
        if (Input.isKey(GLFW.GLFW_KEY_H)) {
            Multiplayer.send("stop");
        }
        if (MamongUsGame.optionText != null) {
            for (Button button : MamongUsGame.configButtons) {
                button.update();
            }
        }
        double ping = (Multiplayer.getPing() * 1000);
        //getRenderer().drawText("Ping: " + ((int) ping) + " ms", 1700, 10, 0xff000000, null);
        //getRenderer().drawText(OutputChat.text(), 1400, 200, 0xff007f3f, null);
        if (Main.mpState == GameState.MultiplayerState.GAME) {
            //getRenderer().drawText(MamongUsGame.taskText(), 100, 100, 0xff000000, null);
            if (MamongUsGame.impostor) {
                //getRenderer().drawText("Kill cooldown: " + MamongUsGame.killCd, 100, 900, 0xff00007f, null);
            }
        } else {
            //getRenderer().drawText("Press [B] to create a game and [E] to start.", 700, 800, 0xff000000, null);
            if (MamongUsGame.optionText != null) {
                //getRenderer().drawText(MamongUsGame.optionText, 100, 100, 0xff000000, null);
                for (Button button : MamongUsGame.configButtons) {
                    button.render();
                }
            }
        }
    }

    @Override
    public void render() {

    }

    @Override
    public void unload() {

    }
}

package tk.mamong_us.net.handlers;

import tk.mamong_us.Main;
import tk.mamong_us.game.MamongUsGame;

public class StopHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        Main.mpState = null;
        MamongUsGame.vars = null;
        MamongUsGame.optionText = null;
        MamongUsGame.impostor = false;
        MamongUsGame.tasks = null;
    }
}

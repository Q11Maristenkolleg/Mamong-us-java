package tk.mamong_us.net.handlers;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;

public class StartHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        Main.mpState = GameState.MultiplayerState.GAME;
    }
}

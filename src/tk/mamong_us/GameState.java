package tk.mamong_us;

public enum GameState {
    MAIN_MENU,
    SINGLEPLAYER,
    MULTIPLAYER,
    OPTIONS,
    LOADING,
    ERROR,
    PAUSE,
    SHHH,
    KILL;

    public enum MultiplayerState {
        LOBBY,
        GAME
    }
}

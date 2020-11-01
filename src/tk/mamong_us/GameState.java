package tk.mamong_us;

import tk.mamong_us.core.Handler;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.net.server.IntegratedServer;

public enum GameState {
    MAIN_MENU {
        @Override
        public void enter() {
            if (Main.lastState == MULTIPLAYER) {
                MULTIPLAYER.exit();
            } else if (Main.lastState == SINGLEPLAYER) {
                SINGLEPLAYER.exit();
            }
            Main.gameState = GameState.MAIN_MENU;
            Main.lastState = GameState.MAIN_MENU;
        }
    },
    SINGLEPLAYER {
        @Override
        public void enter() {
            IntegratedServer.connect();
            Main.gameState = GameState.SINGLEPLAYER;
            Main.lastState = GameState.SINGLEPLAYER;
        }

        @Override
        public void exit() {
            IntegratedServer.disconnect();
        }
    },
    MULTIPLAYER {
        @Override
        public void enter() {
            Main.gameState = GameState.LOADING;
            Main.getProgram().loadMP = true;
        }

        @Override
        public void exit() {
            Multiplayer.disconnect();
            if (Assets.colorChooser != null) {
                Handler.deleteObject(Assets.colorChooser);
                Assets.colorChooser = null;
            }
        }
    },
    OPTIONS {
        @Override
        public void enter() {
            Main.gameState = GameState.OPTIONS;
            Main.lastState = GameState.OPTIONS;
        }
    },
    LOADING,
    ERROR,
    PAUSE {
        @Override
        public void enter() {
            Main.gameState = GameState.PAUSE;
        }
    },
    SHHH,
    KILL;

    public void enter() {

    }

    public void exit() {

    }

    public enum MultiplayerState {
        LOBBY,
        GAME
    }
}

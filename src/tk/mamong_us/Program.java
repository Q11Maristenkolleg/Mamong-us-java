package tk.mamong_us;

import com.siinus.simpleGrafix.gfx.Font;
import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.core.Camera;
import tk.mamong_us.core.Handler;
import tk.mamong_us.discord.DiscordRP;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.gui.*;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.OtherPlayer;
import tk.mamong_us.objects.Player;

import java.awt.event.KeyEvent;

public class Program  extends com.siinus.simpleGrafix.Program {
    boolean loadMP = false;

    private static final DiscordRP discordRP = new DiscordRP();
    public static int width, height;
    public static float scale;

    public PlayerSprite sprite = PlayerSprite.RED;
    public Player player;

    Camera camera;

    public Program() {
        Font.setStandard(Assets.bahnschrift32);
        setIconImage(Assets.icon);
        player = sprite.getNewPlayer(this);
        player.register(GameState.SINGLEPLAYER, GameState.MULTIPLAYER);

        camera = new Camera(player);
    }

    public Camera getCamera() {
        return camera;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void start() {
        discordRP.start();

        getWindow().setScaleOnResize(true);
        getWindow().getFrame().setTitle("Mamong us");
        getWindow().getFrame().setFocusTraversalKeysEnabled(false);
        setCapFps(false);


        InputUtils.setInput(getInput());

        setupMainMenu();

        Stars.init();

        Assets.loadData();
    }

    @Override
    public void update() {
        InputUtils.update(getInput());
        OutputChat.update();
        Stars.update();

        if (InputUtils.isKeyDown(KeyEvent.VK_ESCAPE) && Main.gameState != GameState.MAIN_MENU) {
            if (Main.gameState == GameState.PAUSE) {
                Main.gameState = Main.lastState;
            } else {
                System.out.println("pause");
                startPause();
            }
        }
        if (Main.gameState == GameState.MULTIPLAYER) {
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
                if (InputUtils.isKeyDown(0x46) && MamongUsGame.impostor && MamongUsGame.killCd == 0) {
                    boolean canKill = false;
                    String toKill = null;
                    int distanceToTarget = MamongUsGame.vars.kill_dst.dist;
                    for (OtherPlayer o : OtherPlayer.onlinePlayers) {
                        if (Math.hypot(o.getX() - player.getX(), o.getY() - player.getY()) < distanceToTarget && !MamongUsGame.mates.contains(o)) {
                            canKill = true;
                            toKill = o.getIp();
                            distanceToTarget = (int) Math.hypot(o.getX() - player.getX(), o.getY() - player.getY());
                        }
                    }
                    if (canKill) {
                        Multiplayer.send("kill " + toKill);
                        MamongUsGame.killCd = (int) MamongUsGame.vars.kill_cd;
                        MamongUsGame.killCdB = ((int) (MamongUsGame.vars.kill_cd*100))%100;
                    }
                }
            }
            if (InputUtils.isKeyDown(0x42)) {
                Multiplayer.send("create");
            }
            if (Main.mpState == GameState.MultiplayerState.LOBBY && InputUtils.isKeyDown(0x45)) {
                Multiplayer.send("start");
            }
            if (InputUtils.isKeyDown(0x43)) {
                if (Assets.colorChooser == null) {
                    Assets.colorChooser = new ColorChooser(this, sprite == null ? sprite = PlayerSprite.RED : sprite);
                    Assets.colorChooser.register(GameState.MULTIPLAYER);
                } else {
                    Handler.deleteObject(Assets.colorChooser);
                    Assets.colorChooser = null;
                }
            }
            if (InputUtils.isKeyDown(0x48)) {
                Multiplayer.send("stop");
            }
            if (MamongUsGame.optionText != null) {
                for (Button button : MamongUsGame.configButtons) {
                    button.update();
                }
            }
        }
        if (loadMP) {
            loadMP = false;
            connectMultiplayer();
        }

        Handler.update();

        camera.update();
        discordRP.update(Assets.ipField.getText());
    }

    @Override
    public void render() {
        getRenderer().drawText("Fps: "+gameLoop.getFps(), 10, 10, 0xffff0000, null);
        if (Main.gameState==GameState.MAIN_MENU) {
            Stars.render();
            getRenderer().setBgColor(0xff000000);
        } else {
            getRenderer().setBgColor(0xffffffff);
        }
        Handler.render();
        if (Main.gameState == GameState.MULTIPLAYER) {
            double ping = (Multiplayer.getPing() * 1000);
            getRenderer().drawText("Ping: " + ((int) ping) + " ms", 1700, 10, 0xff000000, null);
            getRenderer().drawText(OutputChat.text(), 1400, 200, 0xff007f3f, null);
            if (Main.mpState == GameState.MultiplayerState.GAME) {
                getRenderer().drawText(MamongUsGame.taskText(), 100, 100, 0xff000000, null);
                if (MamongUsGame.impostor) {
                    getRenderer().drawText("Kill cooldown: " + MamongUsGame.killCd, 100, 900, 0xff00007f, null);
                }
            } else {
                getRenderer().drawText("Press [B] to create a game and [E] to start.", 700, 800, 0xff000000, null);
                if (MamongUsGame.optionText != null) {
                    getRenderer().drawText(MamongUsGame.optionText, 100, 100, 0xff000000, null);
                    for (Button button : MamongUsGame.configButtons) {
                        button.render();
                    }
                }
            }
        }
        if (Main.gameState == GameState.LOADING) {
            getRenderer().drawText("Loading...",10, 10,0xff000000, null);
        }
        if (Main.gameState == GameState.ERROR) {
            getRenderer().drawText("Connection refused!",10, 10,0xffff0000, null);
        }
    }

    @Override
    public void stop() {
        discordRP.shutdown();
    }

    public void startMainMenu() {
        if (Main.lastState == GameState.MULTIPLAYER) {
            Multiplayer.disconnect();
            if (Assets.colorChooser != null) {
                Handler.deleteObject(Assets.colorChooser);
                Assets.colorChooser = null;
            }
        }
        Main.gameState = GameState.MAIN_MENU;
        Main.lastState = GameState.MAIN_MENU;
    }

    public void startPause() {
        Main.gameState = GameState.PAUSE;
    }

    public void startSingleplayer() {
        Main.gameState = GameState.SINGLEPLAYER;
        Main.lastState = GameState.SINGLEPLAYER;
    }

    public void startMultiplayer() {
        Main.gameState = GameState.LOADING;
        loadMP = true;
    }

    private void connectMultiplayer() {
        if (Multiplayer.connect(Assets.ipField.getText().toLowerCase(), Integer.parseInt(Assets.portField.getText()))) {
            if (Assets.nameField.getText().length()<=0) {
                Assets.nameField.setText("Player"+((int) (Math.random()*100)));
            }
            Assets.saveData();
            Multiplayer.send("connect "+Assets.nameField.getText());
            Main.gameState = GameState.MULTIPLAYER;
            Main.lastState = GameState.MULTIPLAYER;
        } else {
            Main.gameState = GameState.ERROR;
        }
    }

    private void setupMainMenu() {
        width = (int) (getWindow().getWidth() / getWindow().getScale());
        height = (int) (getWindow().getHeight() / getWindow().getScale());
        scale = getWindow().getScale();

        Assets.singlePlayerButton = new Button(this, Assets.spButton, width/2-150, 275, 300, 100, this::startSingleplayer);
        Assets.singlePlayerButton.register(GameState.MAIN_MENU);
        Assets.multiPlayerButton = new Button(this, Assets.mpButton, width/2-150, 375, 300, 100, this::startMultiplayer);
        Assets.multiPlayerButton.register(GameState.MAIN_MENU);
        Assets.btmm = new Button(this, Assets.mainMenu, width/2-150, height/2-50, 256, 64, this::startMainMenu);
        Assets.btmm.register(GameState.PAUSE, GameState.ERROR);
        Assets.nameField = new TextInput(this, Assets.spriteText, width/2-128, 150, 256, 64, 0xff00ffff, null);
        Assets.nameField.register(GameState.MAIN_MENU);
        Assets.nameField.setDefaultText("Name");
        Assets.ipField = new TextInput(this, Assets.spriteText, width/2-128, 550, 256, 64, 0xffffffff, null);
        Assets.ipField.register(GameState.MAIN_MENU);
        Assets.ipField.setDefaultText("IP");
        Assets.portField = new TextInput(this, Assets.spriteText, width/2-128, 650, 256, 64, 0xffffffff, null);
        Assets.portField.register(GameState.MAIN_MENU);
        Assets.portField.setDefaultText("Port");
        Assets.tq = new TextQueue();
        Assets.tq.endAction = this::startMultiplayer;
        Assets.ipField.register(Assets.tq);
        Assets.portField.register(Assets.tq);
    }




    public void make() {
        init();
    }
}

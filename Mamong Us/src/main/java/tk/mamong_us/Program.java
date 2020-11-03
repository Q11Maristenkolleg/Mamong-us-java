package tk.mamong_us;

import com.siinus.simpleGrafix.Renderer;
import com.siinus.simpleGrafix.gfx.Font;
import com.siinus.simpleGrafix.gfx.Image;
import com.siinus.simpleGrafixShader.ShaderRenderer;
import com.siinus.simpleGrafixShader.ShaderRendererLite;
import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.core.Camera;
import tk.mamong_us.core.Handler;
import tk.mamong_us.discord.DiscordRP;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.gui.*;
import tk.mamong_us.gui.Button;
import tk.mamong_us.net.Multiplayer;
import tk.mamong_us.objects.OtherPlayer;
import tk.mamong_us.objects.Player;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Program  extends com.siinus.simpleGrafix.Program {
    private ShaderRendererLite shaderRenderer;
    private Renderer normalRenderer;

    boolean loadMP = false;

    private static final DiscordRP discordRP = new DiscordRP();
    public static int width, height;
    public static float scale;

    public PlayerSprite sprite = PlayerSprite.RED;
    public Player player;

    Camera camera;

    public Program() {
    }

    public Camera getCamera() {
        return camera;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void start() {
        Font.setStandard(Assets.bahnschrift32);
        player = sprite.getNewPlayer(this);
        player.register(GameState.SINGLEPLAYER, GameState.MULTIPLAYER);

        camera = new Camera(player);

        discordRP.start();

        getWindow().setScaleOnResize(true);
        getWindow().getFrame().setState(Frame.MAXIMIZED_BOTH);
        getWindow().getFrame().setTitle("Mamong us");
        getWindow().getFrame().setFocusTraversalKeysEnabled(false);
        setCapFps(false);

        Assets.singlePlayerButton.setX(Main.getMidX()-150);
        Assets.multiPlayerButton.setX(Main.getMidX()-150);
        Assets.optionsButton.setX(Main.getMidX()-150);
        Assets.quitButton.setX(Main.getMidX()-150);
        Assets.btmm.setX(Main.getMidX()-150);
        Assets.nameField.setX(Main.getMidX()-128);
        Assets.ipField.setX(Main.getMidX()-128);

        InputUtils.setInput(getInput());

        setupMainMenu();

        Stars.init();

        Assets.loadData();

        GameState.MAIN_MENU.enter();
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
                GameState.PAUSE.enter();
            }
        }
        //if (Main.gameState == GameState.MULTIPLAYER) {
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
                        Assets.killSoundImp.play();
                        player.setX(tpx);
                        player.setY(tpy);
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
        //}
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
        if (Main.gameState == GameState.SINGLEPLAYER || Main.gameState == GameState.MULTIPLAYER) {
            if (!(renderer instanceof ShaderRendererLite)) {
                renderer = shaderRenderer;
                gameLoop.setRenderer(renderer);
            }
        } else {
            if (renderer instanceof ShaderRendererLite) {
                renderer = normalRenderer;
                gameLoop.setRenderer(renderer);
            }
        }
        getRenderer().drawText("Fps: "+gameLoop.getFps(), 10, 10, 0xffff0000, null);
        if (Main.gameState==GameState.MAIN_MENU) {
            Stars.render(getRenderer());
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
            getRenderer().drawText("Loading...",100, 100,0xff000000, null);
        }
        if (Main.gameState == GameState.ERROR) {
            getRenderer().drawText("Connection refused!",100, 100,0xffff0000, null);
        }
    }

    @Override
    public void stop() {
        discordRP.shutdown();
    }

    public void quit() {
        gameLoop.stop();
    }

    private void connectMultiplayer() {
        String[] ipText = Assets.ipField.getText().split(":");
        String ip = null;
        int port = 2119;
        if (ipText.length < 1) {
            Main.gameState = GameState.MAIN_MENU;
            Main.lastState = GameState.MAIN_MENU;
            return;
        } else if (ipText.length < 2) {
            ip = ipText[0].toLowerCase();
        } else {
            ip = ipText[0].toLowerCase();
            port = Integer.parseInt(ipText[1]);
        }
        if (Multiplayer.connect(ip, port)) {
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
    }

    public void make() {
        init();
    }

    @Override
    protected void init() {
        super.init();
        normalRenderer = renderer;
        shaderRenderer = new ShaderRendererLite(window);
        shaderRenderer.setBgColor(0xffffffff);
        shaderRenderer.setAmbientLight((byte) 0x3f);
    }

    public void icon(Image icon) {
        setIconImage(icon);
    }
}

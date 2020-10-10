package tk.mamong_us.net;

import com.siinus.client.ClientHandler;
import org.jetbrains.annotations.NotNull;
import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.chat.OutputChat;
import tk.mamong_us.game.GameVariables;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.game.Task;
import tk.mamong_us.objects.Shhh;

import java.net.SocketException;
import java.util.ArrayList;

public class Protocol implements ClientHandler {

    @Override
    public void onConnect(String s) {
        System.out.println("--- IP: " + s + " ---");
        Multiplayer.ip = s;
        Main.mpState = GameState.MultiplayerState.LOBBY;
    }

    @Override
    public synchronized String processInput(@NotNull String s) {
        String[] msg = s.split(" ");
        String ip = msg[0];
        if (msg.length<2) {

        } else {

            String task = msg[1];

            switch (task) {
                case "connect" -> {
                    if (msg.length >= 3) {
                        OutputChat.add(msg[2] + " joined the game!");
                        if (!Multiplayer.names.containsKey(ip)) {
                            Multiplayer.names.put(ip, msg[2]);
                            Multiplayer.spawnPlayer(ip);
                        }
                    }
                }
                case "disconnect" -> {
                    if (!Multiplayer.ip.equals(ip)) {
                        OutputChat.add(Multiplayer.names.get(ip) + " left the game!");
                        Multiplayer.names.remove(ip);
                        Multiplayer.players.get(ip).destroy();
                        Multiplayer.players.remove(ip);
                    }
                }
                case "pos" -> {
                    if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 4 && Multiplayer.players.containsKey(ip)) {
                        Multiplayer.players.get(ip).setX(Integer.parseInt(msg[2]));
                        Multiplayer.players.get(ip).setY(Integer.parseInt(msg[3]));
                    }
                }
                case "move" -> {
                    if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 3 && Multiplayer.players.containsKey(ip)) {
                        Multiplayer.players.get(ip).setMoving(msg[2].equals("start"));
                    }
                }
                case "face" -> {
                    if (Multiplayer.ip!=null && !Multiplayer.ip.equals(ip) && msg.length >= 3 && Multiplayer.players.containsKey(ip)) {
                        Multiplayer.players.get(ip).setLeft(msg[2].equals("left"));
                    }
                }
                case "impostor" -> {
                    if (msg.length >= 3) {
                        MamongUsGame.impostor = Boolean.parseBoolean(msg[2]);
                        Shhh.shhh(MamongUsGame.impostor);
                    }
                }
                case "data" -> {
                    if (msg.length >= 3) {
                        Main.mpState = GameState.MultiplayerState.LOBBY;
                        StringBuilder data = new StringBuilder();
                        MamongUsGame.vars = new GameVariables();
                        MamongUsGame.tasks = new ArrayList<>();
                        for (int i=2; i<msg.length; i++) {
                            switch (msg[i]) {
                                case "Impostors:" -> MamongUsGame.vars.impostors = Integer.parseInt(msg[i+1]);
                                case "Emergency" -> {
                                    if (msg[i+1].equals("Meetings:")) MamongUsGame.vars.emergencies = Integer.parseInt(msg[i+2]);
                                    else if (msg[i+1].equals("Cooldown:")) MamongUsGame.vars.emergency_cd = Float.parseFloat(msg[i+2]);
                                }
                                case "Discussion" -> MamongUsGame.vars.discussion_time = Integer.parseInt(msg[i+2]);
                                case "Voting" -> MamongUsGame.vars.voting_time = Integer.parseInt(msg[i+2]);
                                case "Player" -> MamongUsGame.vars.speed = Float.parseFloat(msg[i+2]);
                                case "Crewmate" -> MamongUsGame.vars.vision_cm = Float.parseFloat(msg[i+2]);
                                case "Impostor" -> MamongUsGame.vars.vision_imp = Float.parseFloat(msg[i+2]);
                                case "Kill" -> {
                                    if (msg[i+1].equals("Cooldown:")) MamongUsGame.vars.kill_cd = Float.parseFloat(msg[i+2]);
                                    else if (msg[i+1].equals("Distance:")) MamongUsGame.vars.kill_dst = GameVariables.KillDistance.valueOf(msg[i+2].toUpperCase());
                                }
                                case "Common" -> MamongUsGame.tasks.addAll(Task.generate(Task.TaskLength.COMMON, Integer.parseInt(msg[i+2])));
                                case "Long" -> MamongUsGame.tasks.addAll(Task.generate(Task.TaskLength.LONG, Integer.parseInt(msg[i+2])));
                                case "Short" -> MamongUsGame.tasks.addAll(Task.generate(Task.TaskLength.SHORT, Integer.parseInt(msg[i+2])));
                            }
                            data.append(msg[i]);
                            data.append(' ');
                        }
                        System.out.println(MamongUsGame.tasks);
                        MamongUsGame.optionText = data.toString().replace('&', '\n');
                    }
                }
                case "start" -> {
                    Main.mpState = GameState.MultiplayerState.GAME;
                }
                case "stop" -> {
                    Main.mpState = null;
                    MamongUsGame.vars = null;
                    MamongUsGame.optionText = null;
                    MamongUsGame.impostor = false;
                    MamongUsGame.tasks = null;
                }
            }

        }

        return "+ "+s;
    }

    @Override
    public void handleException(@NotNull Throwable throwable) {
        if (throwable instanceof SocketException) {
            if (throwable.getMessage().equals("Connection reset")) {
                Main.gameState = GameState.ERROR;
            }
        }
        throwable.printStackTrace();
    }

    @Override
    public void onDisconnect() {
        disconnect();
    }

    public static void disconnect() {
        Multiplayer.disconnect();
    }
}

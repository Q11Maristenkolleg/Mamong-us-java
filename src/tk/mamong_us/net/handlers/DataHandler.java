package tk.mamong_us.net.handlers;

import tk.mamong_us.GameState;
import tk.mamong_us.Main;
import tk.mamong_us.game.GameVariables;
import tk.mamong_us.game.MamongUsGame;
import tk.mamong_us.game.Task;

import java.util.ArrayList;

public class DataHandler implements Handler {
    @Override
    public void handle(String ip, String[] msg) {
        if (msg.length >= 3) {
            Main.mpState = GameState.MultiplayerState.LOBBY;
            StringBuilder data = new StringBuilder();
            MamongUsGame.vars = new GameVariables();
            MamongUsGame.tasks = new ArrayList<>();
            MamongUsGame.killCd = 10;
            MamongUsGame.killCdB = 60;
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
}

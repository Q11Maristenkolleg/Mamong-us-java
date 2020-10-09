package tk.mamong_us.game;

import org.jetbrains.annotations.NotNull;

public class GameVariables {
    Map map;
    byte impostors;
    byte emergencies;
    byte emergency_cd;
    byte discussion_time;
    byte voting_time;
    float speed;
    byte vision_cm;
    byte vision_imp;
    byte kill_cd;
    KillDistance kill_dst;
    byte common_tasks;
    byte long_tasks;
    byte short_tasks;


    public static @NotNull GameVariables createDefault() {
        GameVariables vars = new GameVariables();
        vars.map = Map.SKELD;
        vars.impostors = 2;
        vars.emergencies = 1;
        vars.emergency_cd = 30;
        vars.discussion_time = 30;
        vars.voting_time = 15;
        vars.speed = 64;
        vars.vision_cm = 4;
        vars.vision_imp = 6;
        vars.kill_cd = 35;
        vars.kill_dst = KillDistance.NORMAL;
        vars.common_tasks = 1;
        vars.long_tasks = 1;
        vars.short_tasks = 2;
        return vars;
    }

    public String print() {
        return "Custom Settings: " + " & " +
                "Map: " + map.getName() + " & " +
                "# Impostors: " + impostors + " & " +
                "# Emergency Meetings: " + emergencies + " & " +
                "Emergency Cooldown: " + (emergency_cd/2f) + " & " +
                "Discussion Time: " + discussion_time + " & " +
                "Voting Time: " + voting_time + " & " +
                "Player Speed: " + (speed/8f) + " & " +
                "Crewmate Vision: " + (vision_cm/4f) + " & " +
                "Impostor Vision: " + (vision_imp/4f) + " & " +
                "Kill Cooldown: " + (kill_cd/2f) + " & " +
                "Kill Distance: " + kill_dst.name().substring(0,1) + kill_dst.name().substring(1).toLowerCase() + " & " +
                "# Common Tasks: " + common_tasks + " & " +
                "# Long Tasks: " + long_tasks + " & " +
                "# Short Tasks: " + short_tasks;
    }

    public enum KillDistance {
        SHORT, NORMAL, LONG;
    }
}

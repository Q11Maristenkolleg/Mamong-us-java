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
        vars.speed = 8;
        vars.vision_cm = 4;
        vars.vision_imp = 6;
        vars.kill_cd = 35;
        vars.kill_dst = KillDistance.NORMAL;
        vars.common_tasks = 1;
        vars.long_tasks = 1;
        vars.short_tasks = 2;
        return vars;
    }

    public enum KillDistance {
        SHORT, NORMAL, LONG;
    }
}

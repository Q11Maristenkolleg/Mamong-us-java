package tk.mamong_us.game;

import org.jetbrains.annotations.NotNull;

public class GameVariables {
    Map map;
    byte impostors;
    boolean confirm_ejects;
    byte emergencies;
    byte emergency_cd;
    byte discussion_time;
    byte voting_time;
    float speed;
    byte vision_cm;
    byte vision_imp;
    byte kill_cd;
    KillDistance kill_dst;
    boolean visual_tasks;
    byte common_tasks;
    byte long_tasks;
    byte short_tasks;

    public void set(int id, boolean down) {
        switch (id) {
            case 1 -> {
                if (down && impostors>1) impostors--;
                else if (!down && impostors<3) impostors++;
            }
            case 2 -> confirm_ejects = !confirm_ejects;
            case 3 -> {
                if (down && emergencies>1) emergencies--;
                else if (!down && emergencies<127) emergencies++;
            }
            case 4 -> {
                if (down && emergency_cd>1) emergency_cd--;
                else if (!down && emergency_cd<127) emergency_cd++;
            }
            case 5 -> {
                if (down && discussion_time>1) discussion_time--;
                else if (!down && discussion_time<127) discussion_time++;
            }
            case 6 -> {
                if (down && voting_time>1) voting_time--;
                else if (!down && voting_time<127) voting_time++;
            }
            case 7 -> {
                if (down && speed>8) speed-=2;
                else if (!down && speed<64) speed+=2;
            }
            case 8 -> {
                if (down && vision_cm>4) vision_cm--;
                else if (!down && vision_cm<16) vision_cm++;
            }
            case 9 -> {
                if (down && vision_imp>4) vision_imp--;
                else if (!down && vision_imp<16) vision_imp++;
            }
            case 10 -> {
                if (down && kill_cd>1) kill_cd--;
                else if (!down && kill_cd<127) kill_cd++;
            }
            case 11 -> {
                if (down) {
                    if (kill_dst == KillDistance.NORMAL) {
                        kill_dst = KillDistance.SHORT;
                    } else if (kill_dst == KillDistance.LONG) {
                        kill_dst = KillDistance.NORMAL;
                    }
                } else {
                    if (kill_dst == KillDistance.NORMAL) {
                        kill_dst = KillDistance.LONG;
                    } else if (kill_dst == KillDistance.SHORT) {
                        kill_dst = KillDistance.NORMAL;
                    }
                }
            }
            case 12 -> visual_tasks = !visual_tasks;
            case 13 -> {
                if (down && common_tasks>0) common_tasks--;
                else if (!down && common_tasks<1) common_tasks++;
            }
            case 14 -> {
                if (down && long_tasks>0) long_tasks--;
                else if (!down && long_tasks<2) long_tasks++;
            }
            case 15 -> {
                if (down && short_tasks>0) short_tasks--;
                else if (!down && short_tasks<3) short_tasks++;
            }
        }
    }

    public static @NotNull GameVariables createDefault() {
        GameVariables vars = new GameVariables();
        vars.map = Map.SKELD;
        vars.impostors = 2;
        vars.confirm_ejects = true;
        vars.emergencies = 1;
        vars.emergency_cd = 30;
        vars.discussion_time = 30;
        vars.voting_time = 15;
        vars.speed = 8;
        vars.vision_cm = 4;
        vars.vision_imp = 6;
        vars.kill_cd = 35;
        vars.kill_dst = KillDistance.NORMAL;
        vars.visual_tasks = true;
        vars.common_tasks = 1;
        vars.long_tasks = 1;
        vars.short_tasks = 2;
        return vars;
    }

    public String print() {
        return "Custom Settings: " + " & " +
                "Map: " + map.getName() + " & " +
                "# Impostors: " + impostors + " & " +
                "Confirm Ejects: " + (confirm_ejects?"On":"Off") + " & " +
                "# Emergency Meetings: " + emergencies + " & " +
                "Emergency Cooldown: " + (emergency_cd/2f) + " & " +
                "Discussion Time: " + discussion_time + " & " +
                "Voting Time: " + voting_time + " & " +
                "Player Speed: " + (speed/8f) + " & " +
                "Crewmate Vision: " + (vision_cm/4f) + " & " +
                "Impostor Vision: " + (vision_imp/4f) + " & " +
                "Kill Cooldown: " + (kill_cd/2f) + " & " +
                "Kill Distance: " + kill_dst.name().substring(0,1) + kill_dst.name().substring(1).toLowerCase() + " & " +
                "Visual Tasks: " + (visual_tasks?"On":"Off") + " & " +
                "# Common Tasks: " + common_tasks + " & " +
                "# Long Tasks: " + long_tasks + " & " +
                "# Short Tasks: " + short_tasks;
    }

    public enum KillDistance {
        SHORT, NORMAL, LONG;
    }
}

package tk.mamong_us.game;

import tk.mamong_us.objects.OtherPlayer;

import java.util.ArrayList;

public class GameVariables {
    public int impostors;
    public int emergencies;
    public float emergency_cd;
    public int discussion_time;
    public int voting_time;
    public float speed;
    public float vision_cm;
    public float vision_imp;
    public float kill_cd;
    public KillDistance kill_dst;

    public ArrayList<OtherPlayer> impostorPlayers = new ArrayList<>();

    public enum KillDistance {
        SHORT(500), NORMAL(1000), LONG(1500);

        public int dist;

        KillDistance(int d) {
            dist=d;
        }
    }
}

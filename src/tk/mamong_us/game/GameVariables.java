package tk.mamong_us.game;

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

    public enum KillDistance {
        SHORT, NORMAL, LONG;
    }
}

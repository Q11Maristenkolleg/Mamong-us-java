package tk.mamong_us.net.handlers;

public enum Handlers {
    CONNECT(new ConnectHandler()),
    DISCONNECT(new DisconnectHandler()),

    POS(new PosHandler()),
    MOVE(new MoveHandler()),
    FACE(new FaceHandler()),
    COLOR(new ColorHandler()),
    KILL(new KillHandler()),

    IMPOSTOR(new ImpostorHandler()),
    MATES(new MatesHandler()),
    DATA(new DataHandler()),

    START(new StartHandler()),
    STOP(new StopHandler());

    private final Handler handler;

    Handlers(Handler h) {
        handler = h;
    }

    public void handle(String ip, String[] msg) {
        handler.handle(ip, msg);
    }
}

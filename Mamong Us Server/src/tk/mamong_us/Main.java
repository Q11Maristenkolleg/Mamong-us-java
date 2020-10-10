package tk.mamong_us;

import com.siinus.server.JavaServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tk.mamong_us.game.MamongUsGame;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static JavaServer server;
    public static HashMap<String, String> names = new HashMap<>();
    public static List<String> operators = Collections.singletonList("127.0.0.1");

    @Nullable public static MamongUsGame game = null;

    public static void main(String @NotNull [] args) throws IOException {
        server = new JavaServer();
        server.start(Integer.parseInt(args[0]));
        JavaServer.setHandler(new Protocol());
        new Console().start();
    }
}

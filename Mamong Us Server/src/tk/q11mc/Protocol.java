package tk.q11mc;

import com.siinus.server.ServerChannel;
import com.siinus.server.ServerHandler;
import org.jetbrains.annotations.NotNull;

public class Protocol implements ServerHandler {

    @Override
    public void onConnect(ServerChannel serverChannel) {

    }

    @Override
    public String processInput(ServerChannel serverChannel, @NotNull String s) {
        String r = null;
        Main.server.broadcast(serverChannel.getName()+" "+s);
        return r;
    }

    @Override
    public void handleException(@NotNull ServerChannel serverChannel, @NotNull Throwable throwable) {
        System.out.println("--- " + serverChannel.getName() + " EXCEPTION ---");
        throwable.printStackTrace();
    }

    @Override
    public void onDisconnect(ServerChannel serverChannel) {

    }
}

package tk.q11mc;

import com.siinus.server.ServerChannel;
import com.siinus.server.ServerHandler;
import org.jetbrains.annotations.NotNull;

public class Protocol implements ServerHandler {

    @Override
    public void onConnect(@NotNull ServerChannel serverChannel) {

    }

    @Override
    public synchronized String processInput(@NotNull ServerChannel serverChannel, @NotNull String s) {
        String r = null;

        String[] msg = s.split(" ");

        String task = msg[0];

        switch (task) {
            case "connect" -> {
                Main.names.remove(serverChannel.getName());
                Main.names.put(serverChannel.getName(), msg[1]);
                for (String other : Main.names.keySet()) {
                    if (serverChannel.getName().equals(other)) {
                        continue;
                    }
                    Main.server.send(serverChannel, other + " connect " + Main.names.get(other));
                }
            }
        }

        Main.server.broadcast(serverChannel.getName() + " " + s);
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

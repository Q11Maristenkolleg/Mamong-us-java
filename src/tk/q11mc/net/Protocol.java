package tk.q11mc.net;

import com.siinus.client.ClientHandler;
import org.jetbrains.annotations.NotNull;
import tk.q11mc.chat.OutputChat;

public class Protocol implements ClientHandler {

    @Override
    public void onConnect(String s) {
        System.out.println("--- IP: " + s + " ---");
        Multiplayer.ip = s;
    }

    @Override
    public String processInput(@NotNull String s) {
        String[] msg = s.split(" ");
        String ip = msg[0];
        if (msg.length<2) {

        } else {

            String task = msg[1];

            if (task.equals("connect")) {
                System.out.println("___");
                OutputChat.add(ip + " connected.");
            }

        }

        return "+ "+s;
    }

    @Override
    public void handleException(@NotNull Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onDisconnect() {

    }
}

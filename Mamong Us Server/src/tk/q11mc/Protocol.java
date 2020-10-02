package tk.q11mc;

import com.siinus.server.ServerChannel;
import com.siinus.server.ServerProtocol;

public class Protocol implements ServerProtocol {

    @Override
    public String processInput(ServerChannel serverChannel, String s) {
        String r = null;
        if (s.startsWith("connect")) {
            if (!Main.names.containsKey(serverChannel.getName())) {
                for (String name : Main.names.keySet()) {
                    Main.server.send(serverChannel, name + " connect " + Main.names.get(name));
                }
                Main.names.put(serverChannel.getName(), s.split(" ")[1]);
                r = serverChannel.getName() + " name";
            }
        }
        Main.server.broadcast(serverChannel.getName()+" "+s);
        return r;
    }
}

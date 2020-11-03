package tk.mamong_us;

import com.siinus.server.ServerChannel;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Console extends Thread {

    @SuppressWarnings("unchecked")
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.startsWith("/")) {
                if (input.equals("/names")) {
                    for (Map.Entry<String, String> entry : Main.names.entrySet()) {
                        Logger.log("|| " + entry.getKey() + " : " + entry.getValue());
                    }
                    continue;
                }
                if (input.equals("/stop")) {
                    try {
                        Main.server.stop();
                    } catch (IOException e) {
                        e.printStackTrace();
                        e.printStackTrace(Logger.getStream());
                    }
                    continue;
                }
                if (input.startsWith("/op")) {
                    if (input.split(" ").length>1)
                    for (ServerChannel channel : Main.server.getChannels()) {
                        if (channel.getName().equals(input.split(" ")[1])) {
                            if (!Main.operators.contains(channel.getName())) {
                                Main.operators.add(channel.getName());
                            }
                            JSONObject root = new JSONObject();
                            root.put("operators", Main.operators);
                            FileIO.saveJSON("./operators.json", root);
                            Logger.log("Made " + channel.getName() + " an operator.");
                        }
                    }
                }
                if (input.startsWith("/deop")) {
                    if (input.split(" ").length>1)
                        for (ServerChannel channel : Main.server.getChannels()) {
                            if (channel.getName().equals(input.split(" ")[1])) {
                                Main.operators.remove(channel.getName());
                                JSONObject root = new JSONObject();
                                root.put("operators", Main.operators);
                                FileIO.saveJSON("./operators.json", root);
                                Logger.log("Made " + channel.getName() + " not an operator.");
                            }
                        }
                }
                if (input.equals("/toggleallowjoin")) {
                    Main.allowJoin = !Main.allowJoin;
                }

                continue;
            }

            Main.server.broadcast(input);
        }
    }
}

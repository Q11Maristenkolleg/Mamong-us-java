package tk.mamong_us;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Console extends Thread {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            if (input.startsWith("/")) {
                if (input.equals("/names")) {
                    for (Map.Entry<String, String> entry : Main.names.entrySet()) {
                        System.out.println("|| " + entry.getKey() + " : " + entry.getValue());
                    }
                    continue;
                }
                if (input.equals("/stop")) {
                    try {
                        Main.server.stop();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                continue;
            }

            Main.server.broadcast(input);
        }
    }
}

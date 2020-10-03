package tk.q11mc;

import java.util.Scanner;

public class Console extends Thread {

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();

            Main.server.broadcast(input);
        }
    }
}

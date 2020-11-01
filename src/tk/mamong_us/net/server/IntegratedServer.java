package tk.mamong_us.net.server;

import tk.mamong_us.net.Protocol;

public class IntegratedServer {
    public static Server server = new Server();
    public static Client client = new Client();
    public static Protocol protocol = new Protocol();

    public static void connect() {
        protocol.onConnect("localhost");
    }

    public static void disconnect() {
        protocol.onDisconnect();
    }

    public static class Server {
        public void receive(String msg) {
            client.receive(ServerProtocol.processInput(msg));
        }
    }

    public static class Client {
        public void send(String msg) {
            server.receive(msg);
        }

        public void receive(String msg) {
            protocol.processInput(msg);
        }
    }
}

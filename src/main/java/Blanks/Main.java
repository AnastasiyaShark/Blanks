package Blanks;

public class Main {
    public static void main(String[] args) {
        int port = 23444;
        String host = "127.0.0.1";
        String hostName = "localhost";
        String magicEnd = "end";

        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            server.serverJob(port, hostName, magicEnd);
        });
        serverThread.start();
        Client client = new Client();
        client.clientJob(host, port, magicEnd);
    }
}

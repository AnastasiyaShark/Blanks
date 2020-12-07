package Blanks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {

    public void serverJob(int port, String hostName, String magicEnd) {
        try {
            final ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(hostName, port));
            while (true) {
                try (SocketChannel socketChannel = serverChannel.accept()) {
                    final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                    while (socketChannel.isConnected()) {
                        int bytesCount = socketChannel.read(inputBuffer);
                        if (bytesCount == -1) break;
                        final String msg = new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8);
                        inputBuffer.clear();
                        if (magicEnd.equals(msg)) return;
                        System.out.println("Получено сообщение от клиента: \'" + msg + "\'");
                        String serversAnswer = msg.replaceAll("\\s", "");
                        socketChannel.write(ByteBuffer.wrap(("The server calculated. The answer is: " +
                                serversAnswer).getBytes(StandardCharsets.UTF_8)));
                    }
                }
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }
}

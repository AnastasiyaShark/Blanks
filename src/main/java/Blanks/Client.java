package Blanks;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public void clientJob(String host, int port, String magicEnd) {
        SocketChannel socketChannel = null;
        try {
            InetSocketAddress socketAddress = new InetSocketAddress(host, port);
            socketChannel = SocketChannel.open();
            socketChannel.connect(socketAddress);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            Scanner scanner = new Scanner(System.in);
            String msg;
            while (true) {
                System.out.println("Enter message for server...");
                msg = scanner.nextLine();
                socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
                if (magicEnd.equals(msg)) return;
                Thread.sleep(1000);
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
                inputBuffer.clear();
            }
        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}

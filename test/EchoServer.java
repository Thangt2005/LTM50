
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        int port = 7;
        ServerSocket soc = new ServerSocket(port);

        while (true) {
            Socket socket = soc.accept();

            new Thread(() -> {
                try {
                    BufferedReader netIn = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                    PrintWriter netOut = new PrintWriter(
                        socket.getOutputStream(), true);

                    netOut.println("hello");

                    String line;
                    while ((line = netIn.readLine()) != null) {
                        if (line.equalsIgnoreCase("quit")) {
                            break;
                        }
                        netOut.println("Echo" + line);
                    }

                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

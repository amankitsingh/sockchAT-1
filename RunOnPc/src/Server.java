import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

class Server {
    ServerSocket serverSocket;
    Server() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        }).start();

    }

    void startServer() {
        try {
            serverSocket = new ServerSocket(ChatProgram.myPort);
            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                String messageFromClient = s.hasNext() ? s.next() : "";
                System.out.println("He:"+messageFromClient);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
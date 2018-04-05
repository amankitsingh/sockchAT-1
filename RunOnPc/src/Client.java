import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

class Client {
    Client(String message) {
        sendMessage(message);
    }

    void sendMessage(String message) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Socket socket = new Socket(ChatProgram.otherIpAddress, ChatProgram.otherPort);
                    OutputStream outputStream = socket.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter br = new BufferedWriter(outputStreamWriter);
                    br.write(message);
                    br.close();
                    socket.close();
                } catch (Exception u) {
                    u.printStackTrace();
                }
            }
        }).start();

    }
}
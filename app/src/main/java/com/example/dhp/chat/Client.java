package com.example.dhp.chat;

import android.util.Log;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;

    Client(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(MainActivity.customLog, MainActivity.otherServerIP + "    " + MainActivity.otherServerPort);
                    socket = new Socket(MainActivity.otherServerIP, MainActivity.otherServerPort);
                    Log.d(MainActivity.customLog, "Connected");
                    outputStream = socket.getOutputStream();
                    outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter br = new BufferedWriter(outputStreamWriter);
                    br.write(message);
                    br.close();
                    Log.d(MainActivity.customLog, "Message sent from try blk Client:" + message);
                    socket.close();

                } catch (Exception u) {
                    u.printStackTrace();
                    Log.d(MainActivity.customLog, "error sent from try blk Client");
                }
            }
        }).start();
    }

}

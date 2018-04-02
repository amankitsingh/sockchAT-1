package com.example.dhp.chatapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client extends AsyncTask<Void, Void, Void> {
    public MainActivity activity;
    public String response = "";
    public TextView textResponse;

    String otherServerIP;
    int otherServerPort;

    Client(MainActivity activity, String addr, int port, TextView textResponse) {
        otherServerIP = addr;
        otherServerPort = port;
        this.activity = activity;
        this.textResponse = textResponse;
        MainActivity.otherServerIP=otherServerIP;
        MainActivity.otherServerPort=otherServerPort;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            OutputStream outputStream;
            socket = new Socket(otherServerIP, otherServerPort);
            outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.print(MainActivity.myServerIP+"@"+MainActivity.myServerPort);
            printStream.close();
            socket.close();
            socket = new Socket(otherServerIP, otherServerPort);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            Log.d(MainActivity.logTag, "this is result:" + result);
            /*
             * notice: inputStream.read() will block if no data return
             */
            String[] temp = result.split("@");
            String ipAddress = temp[0];
            String port = temp[1];
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    activity.startActivity(new Intent(activity, ChatActivity.class));
                }
            });

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }

}

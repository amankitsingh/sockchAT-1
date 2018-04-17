package com.example.dhp.chat;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

public class Server {
    MainActivity mainActivity;
    ChatActivity chatActivity;
    private ServerSocket serverSocket;

    Server(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        ServerMainActivity serverMainActivity = new ServerMainActivity();
        serverMainActivity.start();
    }

    Server(ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
        ServerChatActivity serverChatActivity = new ServerChatActivity();
        serverChatActivity.start();
    }

    public String getIpAddress() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress
                            .nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress();
                    }
                }
            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private class ServerMainActivity extends Thread {
        @Override
        public void run() {
            try {
                MainActivity.myServerIP = getIpAddress();
                Log.d(MainActivity.customLog, getIpAddress());
                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainActivity.runningAddress.setText(MainActivity.myServerIP + ":" + MainActivity.myServerPort);
                    }
                });
                serverSocket = new ServerSocket(MainActivity.myServerPort);
                while (true) {
                    Socket socket = serverSocket.accept();
                    mainActivity.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(mainActivity.getApplicationContext(), "Server accepting...", Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.d(MainActivity.customLog, "accept post");
                    InputStream inputStream = socket.getInputStream();

                    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                    String messageFromClient = s.hasNext() ? s.next() : "";
                    Log.d(MainActivity.customLog, "message received from client:::" + messageFromClient);
                    Log.d(MainActivity.customLog, "message received from client");
                    String[] temp = messageFromClient.split("@");
//                    if (temp.length == 1) {
//                        serverSocket.close();
//                        mainActivity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mainActivity.startActivity(new Intent(mainActivity, ChatActivity.class));
//                                mainActivity.finish();
//                            }
//                        });
//                    } else {
                        MainActivity.otherServerIP = temp[0];
                        MainActivity.otherServerPort = Integer.parseInt(temp[1]);
                        socket.close();
                        serverSocket.close();
                        mainActivity.runOnUiThread(new Runnable() {
                                                       @Override
                                                       public void run() {
                                                           Toast.makeText(mainActivity.getApplicationContext(),
                                                                   "myServerIP:" + MainActivity.myServerIP +
                                                                           "\notherServer:" + MainActivity.otherServerIP +
                                                                           "\nmyServerPort:" + MainActivity.myServerPort +
                                                                           "\notherServerPort" + MainActivity.otherServerPort,
                                                                      Toast.LENGTH_LONG).show();
                                                           mainActivity.startActivity(new Intent(mainActivity, ChatActivity.class));
                                                           mainActivity.finish();

                                                       }
                                                   }
                        );

                    //}

                }
            } catch (IOException e) {
                Log.d(MainActivity.customLog, "err server ma");
                e.printStackTrace();
            }
        }
    }

    private class ServerChatActivity extends Thread {
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(MainActivity.myServerPort);
                while (true) {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                    final String messageFromClient = s.hasNext() ? s.next() : "";
                    socket.close();
                    chatActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(MainActivity.customLog, "message received from client");
                            chatActivity.messagehistoy.append("\nHE :" + messageFromClient);
                            ChatActivity.messageList.add(new Message(messageFromClient, MessageListAdapter.VIEW_TYPE_MESSAGE_RECEIVED));
                        }
                    });


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}

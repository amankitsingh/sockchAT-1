package com.example.dhp.chat;

import android.util.Log;

import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Client {
    ChatActivity chatActivity;
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Client(final String message, final ChatActivity chatActivity) {
        this.chatActivity = chatActivity;
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
                    Message temp = new Message(message, MessageListAdapter.VIEW_TYPE_MESSAGE_SENT);


                    Map<Double, String> hashMap;
                    hashMap = new HashMap<>();
                    ToneAnalyzer toneAnalyzer = new ToneAnalyzer(
                            "2017-09-21",
                            "812e5f45-2e44-4808-aab3-6d7f0c5bb93d",
                            "MKCmNaIOKIYI");
                    toneAnalyzer.setEndPoint("https://gateway.watsonplatform.net/tone-analyzer/api");
                    ToneOptions toneOptions = new ToneOptions.Builder().text(message).build();
                    ToneAnalysis tone = toneAnalyzer.tone(toneOptions).execute();
                    System.out.println(tone);
                    try {
                        JSONObject jsonObject = new JSONObject(tone.toString());
                        JSONObject doc = jsonObject.getJSONObject("document_tone");
                        JSONArray tones = doc.getJSONArray("tones");
                        for (int i = 0; i < tones.length(); i++) {
                            JSONObject obj = tones.getJSONObject(i);
                            String score = obj.getString("score");
                            Double d = Double.parseDouble(score);
                            String tone_name = obj.getString("tone_name");
                            hashMap.put(d, tone_name);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    temp.sorted.putAll(hashMap);
                    String forToast = "Emotion is:\n";
                    for (Map.Entry<Double, String> entry : temp.sorted.entrySet()) {
                        forToast += "KEY" + entry.getKey() + "  VALUE  " + entry.getValue() + "\n";
                    }
                    final String finalToast = forToast;
                    chatActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatActivity.messagehistoy.append(finalToast);
                        }
                    });


                    ChatActivity.messageList.add(temp);
                    Log.d(MainActivity.customLog, "Message sent from try blk Client:" + message);
                    socket.close();

                } catch (Exception u) {
                    u.printStackTrace();
                    Log.d(MainActivity.customLog, "error sent from try blk Client");
                }
            }
        }).start();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();
    }

}

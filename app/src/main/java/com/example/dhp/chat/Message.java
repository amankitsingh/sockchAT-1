package com.example.dhp.chat;

import android.util.Log;

import java.util.Date;
import java.util.TreeMap;

public class Message {
    Date date;
    String message;
    String name;
    TreeMap<Double, String> sorted;
    int belongTo;//1:sent,2:received

    Message(String message, int belongsTo) {
        this.date = new Date();
        this.message = message;
        this.belongTo = belongsTo;
        sorted = new TreeMap<>();//this contains the emotion in sorted order of score
    }

    String getMessageTime() {
        int hours = this.date.getHours();
        int minutes = this.date.getMinutes();
        Log.d(MainActivity.customLog, Integer.toString(hours) + ":" + Integer.toString(minutes));
        return Integer.toString(hours) + ":" + Integer.toString(minutes);
    }

    String getName() {
        return this.name;
    }
}

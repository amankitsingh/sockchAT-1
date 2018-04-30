package com.example.dhp.chat;

import android.util.Log;

import java.util.Date;

public class Message {
    Date date;
    String message;
    String name;
    int belongTo;//1:sent,2:received

    Message(String message, int belongsTo) {
        this.date = new Date();
        this.message = message;
        this.belongTo = belongsTo;
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

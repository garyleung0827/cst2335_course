package com.example.garysandroidlabs;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="message")
    protected String message;

    @ColumnInfo(name="TimeSent")
    protected String timeSent;

    @ColumnInfo(name="SendOrReceive")
    protected int isSentButton;

    ChatMessage(){}

    ChatMessage(String m, String t, int sent){
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    ChatMessage(int id, String m, String t, int sent){
        this.id = id;
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public int getId(){return id;}
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public int isSentButton() {
        return isSentButton;
    }

    public void setSentButton(int sentButton) {
        isSentButton = sentButton;
    }
}

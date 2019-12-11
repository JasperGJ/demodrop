package com.hexagon.demodrop.object;

import com.hexagon.demodrop.model.Message;

import java.util.Date;

public class MessageData {
    public long id;
    public String title;
    public String text;
    public byte[] from;
    public Date date;
    public boolean seen;

    public MessageData(Message message) {
        this.id = message.getId();
        this.title = message.getTitle();
        this.text = message.getText();
        this.from = message.getUser().getPhoto();
        this.date = message.getDate();
        this.seen = message.isSeen();
    }
}


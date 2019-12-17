package com.hexagon.demodrop.object;

import com.hexagon.demodrop.model.Demo;

import java.util.Date;

public class InboxItemData {
    public long id;
    public String artist;
    public byte[] thumbnail;
    public String title;
    public Date uploaded;
    public String status;


    public InboxItemData(Demo demo) {
        this.id = demo.getId();
        this.artist = demo.getUser().getName();
        this.title = demo.getTitle();
        this.uploaded = demo.getUploaded();
        this.thumbnail = demo.getUser().getThumbnail();
        this.status = demo.getStatus();
    }
}

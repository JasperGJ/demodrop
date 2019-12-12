package com.hexagon.demodrop.object;

import com.hexagon.demodrop.model.Demo;

import java.util.Date;

public class InboxItemData {
    public long id;
    public String artist;
    public String title;
    public Date uploaded;

    public InboxItemData(Demo demo) {
        this.id = demo.getId();
        this.artist = demo.getUser().getName();
        this.title = demo.getTitle();
        this.uploaded = demo.getUploaded();
    }
}

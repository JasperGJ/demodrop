package com.hexagon.demodrop.object;

import com.hexagon.demodrop.model.Demo;

public class DemoData {
    public String artist;
    public String title;
    public byte[] audio;
    public Long length;
    public String type;

    public String status;
    public String comment;


    public DemoData(Demo demo) {
        this.artist = demo.getUser().getName();
        this.title = demo.getTitle();
        this.audio = demo.getAudio();
        this.status = demo.getStatus();
    }
}

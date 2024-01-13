package com.notes;

public class Note {
    String title;
    String desc;

    String date;

    public Note(String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}

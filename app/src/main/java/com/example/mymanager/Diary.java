package com.example.mymanager;

public class Diary {

    private String title;
    private String entry;
    private String dateTime;
    private String imagePath;

    public Diary() {
    }

    public Diary(String title, String entry) {
        this.title = title;
        this.entry = entry;
        this.dateTime = dateTime;
        this.imagePath = imagePath;
    }

    public String getTitle() {

        return title;
    }

    public String getEntry() {

        return entry;
    }
    public String getDateTime() {
        return dateTime;
    }

    public String getImagePath() {
        return imagePath;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }


    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}

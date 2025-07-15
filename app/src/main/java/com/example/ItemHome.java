package com.example;

public class ItemHome {

    private String date;
    private String title;
    private int icon;

    public ItemHome(String date, String title, int icon) {
        this.date = date;
        this.title = title;
        this.icon = icon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

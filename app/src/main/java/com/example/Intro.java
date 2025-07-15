package com.example;

public class Intro {

    private int imageSrc;
    private String title;
    private String content;
    private boolean isImageMessage;

    public Intro(int imageSrc, String title, String content) {
        this.imageSrc = imageSrc;
        this.title = title;
        this.content = content;
    }

    public Intro(String content, int imageSrc, String title, boolean isImageMessage) {
        this.content = content;
        this.imageSrc = imageSrc;
        this.title = title;
        this.isImageMessage = isImageMessage;
    }

    public boolean isImageMessage() {
        return isImageMessage;
    }

    public void setImageMessage(boolean imageMessage) {
        isImageMessage = imageMessage;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(int imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Intro{" +
                "imageSrc=" + imageSrc +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}



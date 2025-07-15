package com.example.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class DiaryEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;
    private String content;
    private byte[] img;
    private long dateTime;

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {return content;}

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getImg() {return img;}

    public void setImg(byte[] img) {
        this.img = img;
    }

    public long getDateTime() { return dateTime;}

    public void setDateTime(long dateTime) {this.dateTime = dateTime;}


}

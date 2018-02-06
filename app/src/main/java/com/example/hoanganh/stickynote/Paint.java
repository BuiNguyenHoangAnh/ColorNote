package com.example.hoanganh.stickynote;

import java.io.Serializable;

/**
 * Created by gilzard123 on 03/01/2018.
 */

public class Paint implements Serializable {
    //private variables
    int PaintId;
    String PaintTitle;
    byte[] PaintImg;



    // Empty constructor
    public Paint(){

    }
    // constructor
    public Paint(int id,String title, byte[] img){
        this.PaintId = id;
        this.PaintTitle = title;
        this.PaintImg = img;

    }

    // constructor
    public Paint(String title, byte[] img){
        this.PaintTitle = title;
        this.PaintImg = img;

    }

    public int getPaintId() {
        return PaintId;
    }

    public void setPaintId(int paintId) {
        PaintId = paintId;
    }

    public String getPaintTitle() {
        return PaintTitle;
    }

    public void setPaintTitle(String paintTitle) {
        PaintTitle = paintTitle;
    }

    public byte[] getPaintImg() {
        return PaintImg;
    }

    public void setPaintImg(byte[] paintImg) {
        PaintImg = paintImg;
    }
}


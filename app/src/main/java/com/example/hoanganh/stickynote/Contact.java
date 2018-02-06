package com.example.hoanganh.stickynote;

import java.io.Serializable;

/**
 * Created by gilzard123 on 21/12/2017.
 */

public class Contact implements Serializable {
    //private variables
    int CameraId;
    String CameraTitle;
    String CameraContent;
    String CameraDate;
    byte[] CameraImg;



    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id,String title,String content, String fname, byte[] img){
        this.CameraId = id;
        this.CameraTitle = title;
        this.CameraContent = content;
        this.CameraDate = fname;
        this.CameraImg = img;

    }

    // constructor
    public Contact(String title,String content,String fname, byte[] img){
        this.CameraTitle = title;
        this.CameraContent = content;
        this.CameraDate = fname;
        this.CameraImg = img;

    }

    public int getCameraId() {
        return CameraId;
    }

    public void setCameraId(int cameraId) {
        CameraId = cameraId;
    }

    public String getCameraTitle() {
        return CameraTitle;
    }

    public void setCameraTitle(String cameraTitle) {
        CameraTitle = cameraTitle;
    }

    public String getCameraContent() {
        return CameraContent;
    }

    public void setCameraContent(String cameraContent) {
        CameraContent = cameraContent;
    }

    public String getCameraDate() {
        return CameraDate;
    }

    public void setCameraDate(String cameraName) {
        CameraDate = cameraName;
    }

    public byte[] getCameraImg() {
        return CameraImg;
    }

    public void setCameraImg(byte[] cameraImg) {
        CameraImg = cameraImg;
    }
}

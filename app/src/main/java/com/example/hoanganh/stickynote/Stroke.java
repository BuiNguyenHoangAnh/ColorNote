package com.example.hoanganh.stickynote;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by gilzard123 on 21/12/2017.
 */

public class Stroke extends Object{
    int color;
    Paint paint;
    Path path;
    float brushSize;
    public Stroke() {
        paint = new Paint();
        color = 0xFFFFFFFF;
        path = new Path();
        brushSize = 20;
    }

    public void setColor(int c) {

        color = c;
    }

    public void setPaint(Paint p) {

        paint = p;
    }

    public void setPath(Path p) {

        path = p;
    }

    public void setBrushSize (float val) {
        paint.setStrokeWidth(val);
        brushSize = val;
    }
}
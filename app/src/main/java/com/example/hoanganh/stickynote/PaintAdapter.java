package com.example.hoanganh.stickynote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.Paint;
import com.example.hoanganh.stickynote.R;

/**
 * Created by gilzard123 on 03/01/2018.
 */

public class PaintAdapter extends ArrayAdapter<Paint> {
    private final List<Paint> contactList = new ArrayList<Paint>();
    MyDatabaseHelper db;

    public PaintAdapter(Context context, int resource, List<Paint> s) {
        super(context, resource, s);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_paint, null);
        }
        db = new MyDatabaseHelper(getContext());

        Paint contact = getItem(position);

        if(contact != null) {
            TextView title = (TextView) convertView.findViewById(R.id.txtpainttitle);
            ImageView image = (ImageView) convertView.findViewById(R.id.paintView);
            title.setText(contact.getPaintTitle());
            image.setImageBitmap(convertToBitmap(contact.getPaintImg()));
        }
        return convertView;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
}


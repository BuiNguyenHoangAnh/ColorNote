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

import com.example.hoanganh.stickynote.Contact;
import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.R;

/**
 * Created by gilzard123 on 21/12/2017.
 */

public class CameraAdapter extends ArrayAdapter<Contact>{

    private final List<Contact> contactList = new ArrayList<Contact>();
    MyDatabaseHelper db;

    public CameraAdapter(Context context, int resource, List<Contact> s) {
        super(context, resource, s);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_contact, null);
        }
        db = new MyDatabaseHelper(getContext());

        Contact contact = getItem(position);

        if(contact != null) {
            TextView name = (TextView) convertView.findViewById(R.id.txtView);
            TextView title = (TextView) convertView.findViewById(R.id.txtcameratitle);
            TextView content = (TextView) convertView.findViewById(R.id.txtcameracontent);
            ImageView image = (ImageView) convertView.findViewById(R.id.imgView);
            name.setText(contact.getCameraDate());
            title.setText(contact.getCameraTitle());
            if(contact.getCameraContent().length() > 50) {
                content.setText(contact.getCameraContent().substring(0,50));
            } else {
                content.setText(contact.getCameraContent());
            }
            image.setImageBitmap(convertToBitmap(contact.getCameraImg()));
        }
        return convertView;
    }

    private Bitmap convertToBitmap(byte[] b){

        return BitmapFactory.decodeByteArray(b, 0, b.length);

    }
}

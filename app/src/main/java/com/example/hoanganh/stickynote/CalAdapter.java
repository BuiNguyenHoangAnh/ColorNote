package com.example.hoanganh.stickynote;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.Note;
import com.example.hoanganh.stickynote.R;

/**
 * Created by gilzard123 on 15/12/2017.
 */


public class CalAdapter extends ArrayAdapter<String> {

    MyDatabaseHelper db;
    String color;
    public CalAdapter(Context context, int resource, List<String> s , String x) {
        super(context, resource, s);
        color=x;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.day_gridcell, null);
        }
        db = new MyDatabaseHelper(getContext());
        String[] d = getItem(position).split("-");
        String theday = d[0];
        String themonth = d[2];
        String theyear = d[3];
        int dd = Integer.parseInt(theday);
        int mm = Integer.parseInt(themonth);
        int yy = Integer.parseInt(theyear);
        List<Note> listcal=  db.findbyday(dd,mm,yy,color);
        if (d != null) {
            TextView title = (TextView) convertView.findViewById(R.id.num_events_per_day);
            if (d[1].equals("GREY"))
            {
                convertView.setBackgroundColor(Color.GRAY);
                title.setTextColor(Color.LTGRAY);
            }
            if (d[1].equals("WHITE"))
            {
                convertView.setBackgroundColor(Color.WHITE);
            }
            if(!listcal.isEmpty() && d[1].equals("WHITE"))
            {
                title.setBackgroundColor(Color.RED);
            }
            title.setText(theday);
        }
        return convertView;
    }
}

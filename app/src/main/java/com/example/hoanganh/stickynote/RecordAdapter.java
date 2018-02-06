package com.example.hoanganh.stickynote;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.R;

/**
 * Created by gilzard123 on 03/01/2018.
 */

public class RecordAdapter extends ArrayAdapter<Record> {
    private MediaPlayer mediaPlayer;
    MyDatabaseHelper db;
    final ViewHolder viewHolder = new ViewHolder();
    private List<Record> recordList = new ArrayList<Record>();
    public RecordAdapter(Context context, int resource, List<Record> records ) {
        super(context, resource, records);
        db=new MyDatabaseHelper(context);
        recordList=records;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Record record = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_record,null);

            viewHolder.name = (TextView) convertView.findViewById(R.id.record);
            viewHolder.btnplay = (ImageView) convertView.findViewById(R.id.btn_play);
            viewHolder.btnpause = (ImageView) convertView.findViewById(R.id.btn_pause);
            viewHolder.btndelete = (ImageView) convertView.findViewById(R.id.btn_delete);

            if(record != null) {

                viewHolder.name.setText(record.getRecordName());
                viewHolder.btnplay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(record.getRecordFilepath());
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                });
                viewHolder.btnpause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();
                        if (mediaPlayer != null) {
                            mediaPlayer.pause();
                        }
                    }
                });
                viewHolder.btndelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteRecord(record);
                        recordList.remove(position);
                        // Refresh ListView.
                        notifyDataSetChanged();
                        File file = new File(record.getRecordFilepath());
                        file.delete();
                    }
                });
            }
        }


        return convertView;
    }
    public class ViewHolder{
        TextView name;
        ImageView btnplay,btnpause,btndelete;
    }
}

package com.example.hoanganh.stickynote;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import com.example.hoanganh.stickynote.Note;
import com.example.hoanganh.stickynote.R;

/**
 * Created by gilzard123 on 22/12/2017.
 */

public class NoteAdapterDetail extends ArrayAdapter<Note> {

    public NoteAdapterDetail(Context context, int resource, List<Note> notes ) {
        super(context, resource, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_note,null);
        }

        Note note = getItem(position);

        if(note != null) {
            TextView title = (TextView) convertView.findViewById(R.id.list_note_title);
            TextView content = (TextView) convertView.findViewById(R.id.list_note_content);
            TextView Date = (TextView) convertView.findViewById(R.id.list_note_date);
            title.setText(note.getNoteTitle());
            Date.setText(note.getNoteDate());
            if(note.getNoteContent().length() > 50) {
                content.setText(note.getNoteContent().substring(0,50));
            } else {
                content.setText(note.getNoteContent());
            }
            if(note.getNoteColor().equals("Red")) {
                int color = ContextCompat.getColor(getContext(),R.color.red);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Orange"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.orange);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Yellow"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.yellow);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Green"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.green);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Blue"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.blue);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Purple"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.purple);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Black"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.black);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("Grey"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.grey);
                convertView.setBackgroundColor(color);
            }else if(note.getNoteColor().equals("White"))
            {
                int color = ContextCompat.getColor(getContext(),R.color.white);
                convertView.setBackgroundColor(color);
            }
        }
        return convertView;
    }
}

package com.example.hoanganh.stickynote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.Note;
import com.example.hoanganh.stickynote.R;

public class SearchCal extends AppCompatActivity {

    private EditText SearchText;
    private Button btnSearch;
    private ListView listView;
    private MyDatabaseHelper db;
    private final List<Note> searchList = new ArrayList<Note>();
    private ArrayAdapter<Note> listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_cal);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Init();
        ButtonClick();
    }

    public void Init()
    {
        SearchText = (EditText) findViewById(R.id.Searchtext);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        listView = (ListView) findViewById(R.id.list_search);
        db = new MyDatabaseHelper(this);
    }

    public void ViewList(String s){
        searchList.clear();
        List<Note> list = db.searchcalnote(s);
        searchList.addAll(list);
        listViewAdapter = new NoteAdapterCal(this, R.layout.item_note_list, searchList);
        listView.setAdapter(listViewAdapter);
    }

    public void ButtonClick()
    {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = SearchText.getText().toString();
                ViewList(s);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Note selectedNote = (Note) listView.getItemAtPosition(i);
                Intent intent = new Intent(SearchCal.this, AddEditNoteActivityCal.class);
                intent.putExtra("notecal", selectedNote);

                // Start AddEditNoteActivity, có phản hồi.
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String s = SearchText.getText().toString();
        if(!s.equals(""))
            ViewList(s);
    }
}

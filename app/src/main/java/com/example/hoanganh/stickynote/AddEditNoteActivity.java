package com.example.hoanganh.stickynote;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.Note;
import com.example.hoanganh.stickynote.R;

public class AddEditNoteActivity extends AppCompatActivity {

    Note note;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;
    private int mode;
    private EditText textTitle;
    private EditText textContent;
    private TextView textDate;
    private Calendar cal;
    SimpleDateFormat sdf;
    String Date;
    private String textcolor="White";
    private LinearLayout linearLayout;
    int color;
    private boolean needRefresh;
    private ImageButton red,orange,yellow,green,blue,purple,black,grey,white;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.textTitle = (EditText)this.findViewById(R.id.text_note_title);
        this.textContent = (EditText)this.findViewById(R.id.text_note_content);
        this.textDate =(TextView)this.findViewById(R.id.text_note_date);

        cal = Calendar.getInstance();
        sdf =new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date =sdf.format(cal.getTime());

        this.linearLayout =(LinearLayout) this.findViewById(R.id.edit_note);
        Intent intent = this.getIntent();
        this.note = (Note) intent.getSerializableExtra("note");
        if(note== null)  {
            this.mode = MODE_CREATE;
            textDate.setText(Date);
        } else  {
            this.mode = MODE_EDIT;
            this.textTitle.setText(note.getNoteTitle());
            this.textContent.setText(note.getNoteContent());
            this.textDate.setText(note.getNoteDate());
            this.textcolor = note.getNoteColor();
            if(this.textcolor.equals("Red"))
            {
                //Toast.makeText(getApplicationContext(),"red", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.red);
                linearLayout.setBackgroundColor(color);
            }
            if(this.textcolor.equals("Orange")) {
                //Toast.makeText(getApplicationContext(), "orange", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.orange);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("Yellow")) {
                //Toast.makeText(getApplicationContext(),"yellow", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.yellow);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("Green")) {
                //Toast.makeText(getApplicationContext(),"green", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.green);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("Blue")) {
                //Toast.makeText(getApplicationContext(),"green", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.blue);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("Purple")) {
                //Toast.makeText(getApplicationContext(),"purple", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.purple);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("Black")) {
                //Toast.makeText(getApplicationContext(),"black", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.black);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("Grey")) {
                //Toast.makeText(getApplicationContext(),"grey", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.grey);
                linearLayout.setBackgroundColor(color);
            }
            if (this.textcolor.equals("White")) {
                //Toast.makeText(getApplicationContext(),"white", Toast.LENGTH_SHORT).show();
                color = ContextCompat.getColor(this, R.color.white);
                linearLayout.setBackgroundColor(color);
            }
        }

    }


    // Người dùng Click vào nút Save.
    public void buttonSaveClicked(View view)  {
        MyDatabaseHelper db = new MyDatabaseHelper(this);

        String title = this.textTitle.getText().toString();

        String content = this.textContent.getText().toString();
        String date = this.textDate.getText().toString();
        String color = textcolor;
        if(title.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter title", Toast.LENGTH_LONG).show();
            return;
        }

        if(mode==MODE_CREATE ) {
            this.note= new Note(title,content,date,color);
            db.addNote(note);
        } else  {
            this.note.setNoteTitle(title);
            this.note.setNoteContent(content);
            this.note.setNoteDate(date);
            this.note.setNoteColor(color);
            db.updateNote(note);
        }

        this.needRefresh = true;
        // Trở lại MainActivity.
        this.onBackPressed();
    }

    // Khi người dùng Click vào button Cancel.
    public void buttonCancelClicked(View view)  {
        // Không làm gì, trở về MainActivity.
        this.onBackPressed();
    }

    // Khi Activity này hoàn thành,
    // có thể cần gửi phản hồi gì đó về cho Activity đã gọi nó.
    @Override
    public void finish() {

        // Chuẩn bị dữ liệu Intent.
        Intent data = new Intent();
        // Yêu cầu MainActivity refresh lại ListView hoặc không.
        data.putExtra("needRefresh", needRefresh);

        // Activity đã hoàn thành OK, trả về dữ liệu.
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mn) {
        getMenuInflater().inflate(R.menu.editnote, mn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();
//Xử lý khi click vào sẽ show ra title của item đó
        if(id==R.id.color) {
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.color_dialog);
            red = (ImageButton) dialog.findViewById(R.id.red);
            orange = (ImageButton) dialog.findViewById(R.id.orange);
            yellow = (ImageButton) dialog.findViewById(R.id.yellow);
            blue = (ImageButton) dialog.findViewById(R.id.blue);
            green = (ImageButton) dialog.findViewById(R.id.green);
            purple = (ImageButton) dialog.findViewById(R.id.purple);
            black = (ImageButton) dialog.findViewById(R.id.black);
            grey = (ImageButton) dialog.findViewById(R.id.grey);
            white = (ImageButton) dialog.findViewById(R.id.white);
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Red";
                    color = ContextCompat.getColor(getBaseContext(), R.color.red);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Orange";
                    color = ContextCompat.getColor(getBaseContext(), R.color.orange);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Yellow";
                    color = ContextCompat.getColor(getBaseContext(), R.color.yellow);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Blue";
                    color = ContextCompat.getColor(getBaseContext(), R.color.blue);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Green";
                    color = ContextCompat.getColor(getBaseContext(), R.color.green);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            purple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Purple";
                    color = ContextCompat.getColor(getBaseContext(), R.color.purple);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Black";
                    color = ContextCompat.getColor(getBaseContext(), R.color.black);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            grey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Grey";
                    color = ContextCompat.getColor(getBaseContext(), R.color.grey);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="White";
                    color = ContextCompat.getColor(getBaseContext(), R.color.white);
                    linearLayout.setBackgroundColor(color);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}


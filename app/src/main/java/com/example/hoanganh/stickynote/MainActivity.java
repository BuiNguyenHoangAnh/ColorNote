package com.example.hoanganh.stickynote;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.hoanganh.stickynote.Display_Camera;
import com.example.hoanganh.stickynote.AddEditNoteActivity;
import com.example.hoanganh.stickynote.CameraAdapter;
import com.example.hoanganh.stickynote.NoteAdapter;
import com.example.hoanganh.stickynote.NoteAdapterDetail;
import com.example.hoanganh.stickynote.Paint;
import com.example.hoanganh.stickynote.PaintNote;
import com.example.hoanganh.stickynote.PaintAdapter;
import com.example.hoanganh.stickynote.R;
import com.example.hoanganh.stickynote.calendar;
import com.example.hoanganh.stickynote.Record;
import com.example.hoanganh.stickynote.RecordAdapter;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private ImageButton noteButton,recordButton,addbutton,cameraButton,paintButton,viewList,viewDetail,btnStartRecord, btnStopRecord;;
    private Button addNote,addRecord,addPaint,addCamera;

    private ListView listView;
    //change notelist,checklist,paintlist,cameralist
    private int x=1 ;
    //flag view list,detail;
    private boolean Styleview = true;

    private final List<Note> noteList = new ArrayList<Note>();
    private ArrayAdapter<Note> listViewAdapter;

    private static final int MENU_ITEM_EDIT = 111;
    private static final int MENU_ITEM_DELETE = 222;

    private ImageButton red,orange,yellow,green,blue,purple,black,grey,white,allcolor;
    private String textcolor="";

    private String name;
    private MyDatabaseHelper db;
    private byte[] photo;
    private Bitmap bp;

    private final List<Contact> contactList = new ArrayList<Contact>();
    private ArrayAdapter<Contact> data;

    private final List<Paint> paintList = new ArrayList<Paint>();
    private ArrayAdapter<Paint> dataPaint;

    private final List<Record> recordList = new ArrayList<Record>();
    private ArrayAdapter<Record> recordAdapter;

    private String pathSave = "" ;
    private MediaRecorder mediaRecorder;

    final int REQUEST_PERMISSION_CODE = 1000;
    private EditText recordName;

    private GestureDetectorCompat gestureObject;
    private ImageView avt;
    private TextView username;

    TextView txtTimer;
    long lStartTime, lPauseTime, lSystemTime = 0L;
    Handler handler = new Handler();
    boolean isRun;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            lSystemTime = SystemClock.uptimeMillis() - lStartTime;
            long lUpdateTime = lPauseTime + lSystemTime;
            long secs = (long)(lUpdateTime/1000);
            long mins= secs/60;
            secs = secs %60;
            long milliseconds = (long)(lUpdateTime%1000);
            txtTimer.setText(""+mins+":" + String.format("%02d",secs) + ":" + String.format("%03d",milliseconds));
            handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkPermissionFromDevice())
            requestPermission();

        init();
        //Add NavigationView
        drawerlayout();
        //Button Click
        buttonClick();
        //View note as default
        ViewList();
        //register ContextMenu
        registerForContextMenu(this.listView);


    }

    private void init()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        noteButton = (ImageButton) findViewById(R.id.imageNote);
        recordButton=(ImageButton) findViewById(R.id.imageRecord);
        addbutton =(ImageButton) findViewById(R.id.imageAdd);
        paintButton = (ImageButton) findViewById(R.id.imagePaint);
        cameraButton=(ImageButton) findViewById(R.id.imageCamera);
        listView = (ListView) findViewById(R.id.noteListview);
        db = new MyDatabaseHelper(this);
        gestureObject = new GestureDetectorCompat(this,new LearnGesture(this));
        Utils.onActivityCreateSetTheme(this);
    }
    //Add NavigationView
    private  void drawerlayout()
    {
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        avt = (ImageView) header.findViewById(R.id.imgAvt);
        username = (TextView) header.findViewById(R.id.user_name);
    }
    //Button Click
    public void buttonClick(){
        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Styleview == true)
                {
                    ViewList();
                }
                else
                    ViewDetail();

                x=1;

            }
        });
        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewRecord();
                x=2;
            }
        });
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.select_note);
                dialog.setTitle("Select Add");
                addNote = (Button) dialog.findViewById(R.id.Add1);
                addRecord = (Button) dialog.findViewById(R.id.Add2);
                addPaint = (Button) dialog.findViewById(R.id.Add3);
                addCamera = (Button) dialog.findViewById(R.id.Add4);

                addNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                        // Start AddEditNoteActivity, có phản hồi.
                        startActivityForResult(intent,1);
                        x=1;
                        dialog.dismiss();
                    }
                });
                addRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialogre = new Dialog(MainActivity.this);
                        dialogre.setContentView(R.layout.record_dialog);
                        btnStartRecord = (ImageButton) dialogre.findViewById(R.id.btn_record);
                        btnStopRecord = (ImageButton) dialogre.findViewById(R.id.btn_stop);
                        recordName = (EditText) dialogre.findViewById(R.id.record_name);
                        txtTimer = (TextView) dialogre.findViewById(R.id.txtTimer);
                        btnStopRecord.setEnabled(false);
                        btnStartRecord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!recordName.getText().toString().equals("")) {
                                    btnStopRecord.setEnabled(true);
                                    btnStartRecord.setEnabled(false);
                                    if (checkPermissionFromDevice()) {
                                        pathSave = Environment.getExternalStorageDirectory()
                                                .getAbsolutePath() + "/"
                                                + recordName.getText().toString() + "_audio_record.3gp";
                                        setupMediaRecorder();
                                        try {
                                            mediaRecorder.prepare();
                                            mediaRecorder.start();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if(isRun)
                                            return;
                                        isRun = true;
                                        lStartTime = SystemClock.uptimeMillis();
                                        handler.postDelayed(runnable, 0);
                                        Toast.makeText(MainActivity.this, "Recording", Toast.LENGTH_SHORT).show();
                                    } else {
                                        requestPermission();
                                    }
                                }
                                else
                                    Toast.makeText(getApplicationContext(),"Please Enter Record Name",Toast.LENGTH_SHORT).show();
                            }
                        });
                        btnStopRecord.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(!recordName.getText().toString().equals("")) {
                                    mediaRecorder.stop();
                                    Record record = new Record(recordName.getText().toString(), pathSave);
                                    db.addRecord(record);
                                    dialogre.dismiss();
                                    ViewRecord();
                                    if(!isRun)
                                        return;
                                    isRun = false;
                                    lPauseTime = 0;
                                    handler.removeCallbacks(runnable);
                                }
                                else
                                    Toast.makeText(getApplicationContext(),"Please Enter Record Name",Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialogre.show();
                        x=2;
                        dialog.dismiss();

                    }
                });
                addPaint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(MainActivity.this, PaintNote.class);
                        startActivityForResult(myIntent, 3);
                        x=3;
                        dialog.dismiss();
                    }
                });
                addCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 10);
                        x=4;
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        paintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"paint click", Toast.LENGTH_SHORT).show();
                DrawList();
                x=3;
            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"camera click", Toast.LENGTH_SHORT).show();
                ShowRecords();
                x=4;

            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureObject.onTouchEvent(motionEvent);
                return false;
            }
        });
        avt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 6);
            }
        });
        username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final EditText taskEditText = new EditText(MainActivity.this);
                android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                        .setTitle("User's name")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"user name", Toast.LENGTH_SHORT).show();
                                String task = String.valueOf(taskEditText.getText());
                                username.setText(task);
//                                SearchList(task);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
            }
        });
    }
    //View List Note
    private void ViewList(){
        noteList.clear();
        db.createDefaultNotesIfNeed();
        List<Note> list=  db.getAllNotes();
        this.noteList.addAll(list);
        this.listViewAdapter = new NoteAdapter(this, R.layout.item_note_list, noteList);
        this.listView.setAdapter(this.listViewAdapter);
    }
    //Search Note
    private void SearchList(String s){
        if(x==1) {
            noteList.clear();
            List<Note> list =new ArrayList<Note>();
            list = db.searchnote(s);
            this.noteList.addAll(list);
            this.listViewAdapter = new NoteAdapter(this, R.layout.item_note_list, noteList);
            this.listView.setAdapter(this.listViewAdapter);
        }
        else if(x==2) {
            recordList.clear();
            List<Record> list= new ArrayList<Record>()  ;
            list = db.searchrecordnote(s);
            this.recordList.addAll(list);
            this.recordAdapter = new RecordAdapter(this, R.layout.item_record, recordList);
            this.listView.setAdapter(this.recordAdapter);
        }

    }
    //Sort Note
    private void SortNote(){
        if(x==1) {
            noteList.clear();
            List<Note> list =new ArrayList<Note>();
            list = db.sortnoteTitle();
            this.noteList.addAll(list);
            this.listViewAdapter = new NoteAdapter(this, R.layout.item_note_list, noteList);
            this.listView.setAdapter(this.listViewAdapter);
        }
        else if(x==2) {
            recordList.clear();
            List<Record> list= new ArrayList<Record>()  ;
            list = db.sortrecordTitle();
            this.recordList.addAll(list);
            this.recordAdapter = new RecordAdapter(this, R.layout.item_record, recordList);
            this.listView.setAdapter(this.recordAdapter);
        }
        else if(x==3){
            paintList.clear();
            List<Paint> list = db.sortpaintDate();
            this.paintList.addAll(list);
            this.dataPaint = new PaintAdapter(this, R.layout.item_paint, paintList);
            this.listView.setAdapter(dataPaint);
        }
        else if(x==4){
            contactList.clear();
            List<Contact> list = db.sortcameraDate();
            this.contactList.addAll(list);
            this.data = new CameraAdapter(this,R.layout.item_contact,contactList);
            this.listView.setAdapter(data);
        }

    }
    //Search Color
    private void SearchColor(String s){
        if(x==1) {
            noteList.clear();
            List<Note> list =new ArrayList<Note>();
            list = db.searchcolornote(s);
            this.noteList.addAll(list);
            this.listViewAdapter = new NoteAdapter(this, R.layout.item_note_list, noteList);
            this.listView.setAdapter(this.listViewAdapter);
        }
    }
    //View Detail
    private void ViewDetail(){
        noteList.clear();
        db.createDefaultNotesIfNeed();
        List<Note> list=  db.getAllNotes();
        this.noteList.addAll(list);
        this.listViewAdapter = new NoteAdapterDetail(this, R.layout.item_note, noteList);
        this.listView.setAdapter(this.listViewAdapter);
    }
    //View Record
    private void ViewRecord(){
        recordList.clear();
        List<Record> list=  db.getAllRecord();
        this.recordList.addAll(list);
        this.recordAdapter = new RecordAdapter(this, R.layout.item_record, recordList);
        this.listView.setAdapter(this.recordAdapter);
    }
    //View Note Camera
    private void ShowRecords(){
        contactList.clear();
        List<Contact> list = db.getAllContact();
        this.contactList.addAll(list);
        this.data = new CameraAdapter(this,R.layout.item_contact,contactList);
        this.listView.setAdapter(data);
    }
    //View Note Paint
    private void DrawList(){
        paintList.clear();
        List<Paint> list = db.getAllPaint();
        this.paintList.addAll(list);
        this.dataPaint = new PaintAdapter(this, R.layout.item_paint, paintList);

        this.listView.setAdapter(dataPaint);

    }
    //Add Image to Database
    private void addImage(){
        Date currentTime = Calendar.getInstance().getTime();
        name = currentTime.toString();
        photo = profileImage(bp);
        //db.addContact(new Contact(name, photo));
        Intent intent = new Intent(this, Display_Camera.class);
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putByteArray("photo", photo);
        intent.putExtras(bundle);
        this.startActivityForResult(intent,4);

        //Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_LONG).show();
    }
    //Delete Note
    private void deleteNote(Note note)  {
        db.deleteNote(note);
        this.noteList.remove(note);
        // Refresh ListView.
        this.listViewAdapter.notifyDataSetChanged();
    }
    //Delete Camera Note
    private void deleteContact(Contact contact)  {
        db.deleteContact(contact);
        this.contactList.remove(contact);
        // Refresh ListView.
        this.data.notifyDataSetChanged();
    }
    //Delete Paint Note
    private void deletePaint(Paint paint)  {
        db.deletePaint(paint);
        this.paintList.remove(paint);
        // Refresh ListView.
        this.dataPaint.notifyDataSetChanged();
    }
    //Convert bitmap to bytes
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title{
            menu.add(0, MENU_ITEM_EDIT, 0, "Edit Note");
            menu.add(0, MENU_ITEM_DELETE, 1, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(x==1) {
            final Note selectedNote = (Note) this.listView.getItemAtPosition(info.position);
            if (item.getItemId() == MENU_ITEM_EDIT) {
                Intent intent = new Intent(this, AddEditNoteActivity.class);
                intent.putExtra("note", selectedNote);
                this.startActivityForResult(intent, 1);
            } else if (item.getItemId() == MENU_ITEM_DELETE) {
                new AlertDialog.Builder(this)
                        .setMessage(selectedNote.getNoteTitle() + ". Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteNote(selectedNote);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                return false;
            }
        }
        else if(x==3){
            final Paint selectedPaint = (Paint) this.listView.getItemAtPosition(info.position);
            if (item.getItemId() == MENU_ITEM_EDIT) {
//                Intent intent = new Intent(this, Display_Camera.class);
//                intent.putExtra("contact", selectedContact);
//                this.startActivityForResult(intent,4);
                Toast.makeText(getApplicationContext(),"You cannot edit this", Toast.LENGTH_SHORT).show();
            } else if (item.getItemId() == MENU_ITEM_DELETE) {
                new AlertDialog.Builder(this)
                        .setMessage(selectedPaint.getPaintTitle() + ". Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deletePaint(selectedPaint);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                return false;
            }
        }
        else if(x==4) {
            final Contact selectedContact = (Contact) this.listView.getItemAtPosition(info.position);
            if (item.getItemId() == MENU_ITEM_EDIT) {
                Intent intent = new Intent(this, Display_Camera.class);
                intent.putExtra("contact", selectedContact);
                this.startActivityForResult(intent,4);
            } else if (item.getItemId() == MENU_ITEM_DELETE) {
                new AlertDialog.Builder(this)
                        .setMessage(selectedContact.getCameraDate() + ". Are you sure you want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteContact(selectedContact);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mn) {
        getMenuInflater().inflate(R.menu.menu, mn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(mToggle.onOptionsItemSelected(item))
        {

            return true;
        }
//Xử lý khi click vào sẽ show ra title của item đó
        if(id==R.id.menusearch) {
            //Toast.makeText(getApplicationContext(), "choose menu search", Toast.LENGTH_SHORT).show();
            final EditText taskEditText = new EditText(this);
            android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Search")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(taskEditText.getText());
                            SearchList(task);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
            return true;
        }
        if (id == R.id.color && x==1) {
           // Toast.makeText(getApplicationContext(),"choose color", Toast.LENGTH_SHORT).show();
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.color_cal_dialog);
            red = (ImageButton) dialog.findViewById(R.id.red);
            orange = (ImageButton) dialog.findViewById(R.id.orange);
            yellow = (ImageButton) dialog.findViewById(R.id.yellow);
            blue = (ImageButton) dialog.findViewById(R.id.blue);
            green = (ImageButton) dialog.findViewById(R.id.green);
            purple = (ImageButton) dialog.findViewById(R.id.purple);
            black = (ImageButton) dialog.findViewById(R.id.black);
            grey = (ImageButton) dialog.findViewById(R.id.grey);
            white = (ImageButton) dialog.findViewById(R.id.white);
            allcolor=(ImageButton) dialog.findViewById(R.id.allcolor);
            red.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Red";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Orange";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Yellow";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Blue";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Green";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            purple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Purple";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Black";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            grey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Grey";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="White";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            allcolor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="";
                    dialog.dismiss();
                    SearchColor(textcolor);
                }
            });
            dialog.show();
        }
        if (id == R.id.sort) {
            Toast.makeText(getApplicationContext(),"sort", Toast.LENGTH_SHORT).show();
               SortNote();
        }
        if (id == R.id.view && x==1) {
            //Toast.makeText(getApplicationContext(),"view", Toast.LENGTH_SHORT).show();
            final Dialog dialog1 = new Dialog(this);
            dialog1.setContentView(R.layout.view_dialog);
            viewList =(ImageButton) dialog1.findViewById(R.id.view_list);
            viewDetail = (ImageButton) dialog1.findViewById(R.id.view_detail);
            viewList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (x == 1) {
                        ViewList();

                    }
                    Styleview = true;
                }
            });
            viewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (x == 1) {
                        ViewDetail();

                    }
                    Styleview = false;
                }
            });
            dialog1.show();
        }
        if (id == R.id.bubble) {
            Toast.makeText(getApplicationContext(),"bubble", Toast.LENGTH_SHORT).show();
            startService(new Intent(MainActivity.this, HeadService.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.calendar) {
            Intent intent = new Intent(MainActivity.this, calendar.class);
            startActivityForResult(intent,99);
        } else if (id == R.id.search) {
            //Toast.makeText(getApplicationContext(),"search select", Toast.LENGTH_SHORT).show();
            final EditText taskEditText = new EditText(this);
            android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Search")
                    .setView(taskEditText)
                    .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String task = String.valueOf(taskEditText.getText());
                            SearchList(task);
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
        } else if (id == R.id.theme){
            //Toast.makeText(getApplicationContext(),"theme select", Toast.LENGTH_SHORT).show();
            final Dialog themeDialog = new Dialog(this);
            themeDialog.setTitle("Theme");
            themeDialog.setContentView(R.layout.select_theme);
            themeDialog.show();
            Button theme1 = (Button) themeDialog.findViewById(R.id.Theme1);
            theme1.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Utils.changeToTheme(MainActivity.this, Utils.THEME1);
                    Toast.makeText(getApplicationContext(),"Default", Toast.LENGTH_SHORT).show();
                    themeDialog.dismiss();
                }
            });
            Button theme2 = (Button) themeDialog.findViewById(R.id.Theme2);
            theme2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Utils.changeToTheme(MainActivity.this, Utils.THEME2);
                    Toast.makeText(getApplicationContext(),"Light", Toast.LENGTH_SHORT).show();
                    themeDialog.dismiss();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 1 ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);
            // Refresh ListView
            if(needRefresh) {
                // Thông báo dữ liệu thay đổi (Để refresh ListView).
                this.listViewAdapter.notifyDataSetChanged();
                if(Styleview == true)
                {
                    ViewList();
                }
                else
                    ViewDetail();
            }
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 4 ) {
            boolean needRefresh = data.getBooleanExtra("needRefresh",true);
            // Refresh ListView
            if(needRefresh) {
                // Thông báo dữ liệu thay đổi (Để refresh ListView).
                this.data.notifyDataSetChanged();
                ShowRecords();
            }
        }
        else if (resultCode == Activity.RESULT_OK && requestCode == 10 ) {
            bp = (Bitmap) data.getExtras().get("data");
            addImage();
            //ShowRecords();
        }
        else if (requestCode == 6){
            if(resultCode == RESULT_OK){
                Uri choosenImage = data.getData();

                if(choosenImage !=null){

                    bp=decodeUri(choosenImage, 400);
                    avt.setImageBitmap(bp);
                }
            }
        }
        else if(requestCode == 3){
            DrawList();
        }

    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void setupMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        },REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch ( requestCode )
        {
            case REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }

    }

    private boolean checkPermissionFromDevice() {
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                record_audio_result == PackageManager.PERMISSION_GRANTED;
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{

        private Context context;

        public LearnGesture(Context context) {
            this.context = context;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > 50){
                Intent intent = new Intent(MainActivity.this,calendar.class);
                //finish();
                startActivity(intent);
                return  true;
            }
            return true;
        }
    }
}


package com.example.hoanganh.stickynote;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.Note;
import com.example.hoanganh.stickynote.R;

public class calendar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private Calendar cal;
    private ImageButton btnPrev,btnPrevDialog;
    private ImageButton btnNext,btnNextDialog;
    public TextView txtDate, txtDateDialog;

    public GridView grid;
    public ArrayAdapter adapter;
    public ArrayList<String> list =new ArrayList<>();

    private MyDatabaseHelper db;

    private String textcolor="";
    private int daysInMonth, month, year, day, m,color;

    private static final int MY_REQUEST_CODE = 1000;
    private Button btnAdd;

    private ListView listView;
    private final List<Note> noteList = new ArrayList<Note>();
    private ArrayAdapter<Note> listViewAdapter;

    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final String[] th={"CN","T.2","T.3","T.4","T.5","T.6","T.7"};
    private ImageButton red,orange,yellow,green,blue,purple,black,grey,white,allcolor;

    private GestureDetectorCompat gestureObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
        initControl();
        drawerlayout();

        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        m = month - 1;
        txtDate.setText(" Tháng "+month+" Năm "+year);

        printCalendar(month,year);
        buttonClick();

    }

    private  void drawerlayout()
    {
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
    }
    //find day
    private String dayofweek(int d,int m,int y)
    {
        if(m<3)
        {
            m=m+12;
            y=y-1;
        }
        int n=(d+2*m+(3*(m+1))/5+y+(y/4))%7;
        return th[n];
    }

    private void initControl()
    {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        btnPrev = (ImageButton) findViewById(R.id.calendar_prev_button);
        btnNext = (ImageButton) findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
        db = new MyDatabaseHelper(this);
        gestureObject = new GestureDetectorCompat(this,new LearnGesture(this));
    }

    private void buttonClick()
    {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month > 11) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
                list.clear();
                printCalendar(month, year);
                txtDate.setText(" Tháng "+month+" Năm "+year);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month <= 1) {
                    month = 12;
                    year--;
                } else {
                    month--;
                }
                list.clear();
                printCalendar(month, year);
                txtDate.setText(" Tháng "+month+" Năm "+year);
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDate();
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String[] x = list.get(i).split("-");
                if (x[1].equals("WHITE")) {
                    day = Integer.parseInt(x[0]);
                    final Dialog dialog = new Dialog(calendar.this);
                    dialog.setContentView(R.layout.note_calendar);
                    txtDateDialog=(TextView) dialog.findViewById(R.id.calendar_date_display_dialog);
                    btnNextDialog =(ImageButton) dialog.findViewById(R.id.calendar_next_button_dialog);
                    btnPrevDialog = (ImageButton) dialog.findViewById(R.id.calendar_prev_button_dialog);
                    btnAdd = (Button) dialog.findViewById(R.id.add);
                    listView = (ListView) dialog.findViewById(R.id.callist);
                    String th = dayofweek(day, month, year);
                    m = month - 1;
                    txtDateDialog.setText(th + ", " + day + " Tháng " + month + ", " + year);

                    noteList.clear();
                    List<Note> listcal=  db.findbyday(day,month,year,textcolor);
                    noteList.addAll(listcal);
                    listViewAdapter = new NoteAdapterCal(calendar.this, R.layout.item_cal_note, noteList);
                    listView.setAdapter(listViewAdapter);

                    btnNextDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            GregorianCalendar cale = new GregorianCalendar(year, m, 1);
                            if (cale.isLeapYear(cale.get(Calendar.YEAR)) && m == 1) {
                                daysOfMonth[1] = 29;
                            }
                            if (day >= daysOfMonth[m]) {
                                day = 1;
                                month++;
                                m++;
                                if (month > 12) {
                                    month = 1;
                                    m = 0;
                                    year++;
                                }
                            } else {
                                day++;
                            }
                            String th = dayofweek(day, month, year);
                            txtDateDialog.setText(th + ", " + day + " Tháng " + month + ", " + year);

                           refresh();

                            noteList.clear();
                            List<Note> listcal=  db.findbyday(day,month,year,textcolor);
                            noteList.addAll(listcal);
                            listViewAdapter = new NoteAdapterCal(calendar.this, R.layout.item_cal_note, noteList);
                            listView.setAdapter(listViewAdapter);
                        }
                    });
                    btnPrevDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            GregorianCalendar cale = new GregorianCalendar(year, m, 1);
                            if (cale.isLeapYear(cale.get(Calendar.YEAR)) && m == 1) {
                                daysOfMonth[1] = 29;
                            }
                            if (day <= 1) {
                                m--;
                                month--;
                                if (month < 1) {
                                    month = 12;
                                    m = 11;
                                    year--;
                                }
                                day = daysOfMonth[m];
                            } else {
                                day--;
                            }
                            String th = dayofweek(day, month, year);
                            txtDateDialog.setText(th + ", " + day + " Tháng " + month + ", " + year);

                            refresh();

                            noteList.clear();
                            List<Note> listcal=  db.findbyday(day,month,year,textcolor);
                            noteList.addAll(listcal);
                            listViewAdapter = new NoteAdapterCal(calendar.this, R.layout.item_cal_note, noteList);
                            listView.setAdapter(listViewAdapter);
                        }
                    });
                    btnAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(calendar.this, AddEditNoteActivityCal.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("day", day);
                            bundle.putInt("month", month);
                            bundle.putInt("year", year);
                            intent.putExtras(bundle);
                            // Start AddEditNoteActivity, có phản hồi.
                            startActivityForResult(intent,MY_REQUEST_CODE);
                            dialog.dismiss();
                        }
                    });
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final Note selectedNote = (Note) listView.getItemAtPosition(i);
                            Intent intent = new Intent(calendar.this, AddEditNoteActivityCal.class);
                            intent.putExtra("notecal", selectedNote);

                            // Start AddEditNoteActivity, có phản hồi.
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            final Note selectedNote = (Note) listView.getItemAtPosition(i);
                            new android.app.AlertDialog.Builder(calendar.this)
                                    .setMessage(selectedNote.getNoteTitle()+". Are you sure you want to delete?")
                                    .setCancelable(false)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            deleteNote(selectedNote);
                                        }
                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                            return true;
                        }
                    });
                    dialog.show();
                }
            }
        });
        grid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    gestureObject.onTouchEvent(motionEvent);
                return false;
            }
        });
    }
    //delete Note
    private void deleteNote(Note note)  {
        db.deleteNoteCal(note);
        noteList.remove(note);
        listViewAdapter.notifyDataSetChanged();
        refresh();
    }
    //Update Note
    private void updateDate()
    {
        DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int Year, int Month, int dayOfMonth) {
                year= Year;
                month=Month+1;
                list.clear();
                printCalendar(month, year);
                grid.setAdapter(adapter);
                txtDate.setText(" Tháng "+month+" Năm "+year);
            }
        };
        new DatePickerDialog(this,d,year,month-1,cal.get(Calendar.DAY_OF_MONTH)).show();

    }
    //Show Calendar
    public void printCalendar(int mm,int yy)
    {
        int trailingSpaces = 0;
        int leadSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;
        int currentMonth = mm - 1;
        daysInMonth=daysOfMonth[currentMonth];
        if (currentMonth == 11)
        {
            prevMonth = currentMonth - 1;
            daysInPrevMonth = daysOfMonth[prevMonth];
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        }
        else if (currentMonth == 0)
        {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = daysOfMonth[prevMonth];
            nextMonth = 1;
        }
        else
        {
            prevMonth = currentMonth - 1;
            nextMonth = currentMonth + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = daysOfMonth[prevMonth];
        }
        GregorianCalendar cale = new GregorianCalendar(yy, currentMonth, 1);
        int currentWeekDay = cale.get(Calendar.DAY_OF_WEEK)-1;
        trailingSpaces = currentWeekDay;

        if (cale.isLeapYear(cale.get(Calendar.YEAR)) && currentMonth == 1)
        {
            ++daysInMonth;
        }
        for (int i = 0; i < trailingSpaces ; i++)
        {
            list.add(String.valueOf((daysInPrevMonth - trailingSpaces + 1) + i)+"-GREY"+"-"+(prevMonth+1)+"-"+prevYear);
        }
        for (int i = 1; i <= daysInMonth; i++)
        {
            list.add(String.valueOf(i)+"-WHITE"+"-"+ (currentMonth+1) +"-"+yy);
        }
        for (int i = 0; i < list.size() % 7; i++)
        {
            list.add(String.valueOf(i + 1)+"-GREY" +"-"+(nextMonth+1)+"-"+nextYear);
        }
        adapter = new CalAdapter(this,R.layout.day_gridcell,list,textcolor);
        grid.setAdapter(adapter);
    }
    //Refresh calendar
    public void refresh(){
        list.clear();
        printCalendar(month,year);
        adapter = new CalAdapter(this,R.layout.day_gridcell,list,textcolor);
        grid.setAdapter(adapter);
        txtDate.setText(" Tháng "+month+" Năm "+year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mn) {
        getMenuInflater().inflate(R.menu.calmenu, mn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (mToggle.onOptionsItemSelected(item)) {

            return true;
        }
//Xử lý khi click vào sẽ show ra title của item đó
        if (id == R.id.menusearch) {
            //Toast.makeText(getApplicationContext(),"choose menu search", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(calendar.this, SearchCal.class);
            startActivity(intent);
        }
        if (id == R.id.color) {
            //Toast.makeText(getApplicationContext(),"choose color", Toast.LENGTH_SHORT).show();
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
                    refresh();
                }
            });
            orange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Orange";
                    dialog.dismiss();
                    refresh();
                }
            });
            yellow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Yellow";
                    dialog.dismiss();
                    refresh();
                }
            });
            blue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Blue";
                    dialog.dismiss();
                    refresh();
                }
            });
            green.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Green";
                    dialog.dismiss();
                    refresh();
                }
            });
            purple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Purple";
                    dialog.dismiss();
                    refresh();
                }
            });
            black.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Black";
                    dialog.dismiss();
                    refresh();
                }
            });
            grey.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="Grey";
                    dialog.dismiss();
                    refresh();
                }
            });
            white.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="White";
                    dialog.dismiss();
                    refresh();
                }
            });
            allcolor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    textcolor="";
                    dialog.dismiss();
                    refresh();
                }
            });
            dialog.show();
        }
        if (id == R.id.today) {
            //Toast.makeText(getApplicationContext(),"today", Toast.LENGTH_SHORT).show();
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH)+1;
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notes) {
            //Intent intent = new Intent(calendar.this, MainActivity.class);
            finish();
            //startActivityForResult(intent,99);
        } else if (id == R.id.search) {
            //Toast.makeText(getApplicationContext(),"search select", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(calendar.this, SearchCal.class);
            startActivity(intent);
        } else if (id == R.id.theme){
            Toast.makeText(getApplicationContext(),"theme select", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        refresh();
        super.onStart();
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{

        private Context context;

        public LearnGesture(Context context) {
            this.context = context;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX() - e1.getX() > 50){
                finish();
                return  true;
            }
            return true;
        }
    }
}


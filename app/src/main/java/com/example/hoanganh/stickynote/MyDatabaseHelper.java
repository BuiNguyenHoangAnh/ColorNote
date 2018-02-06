package com.example.hoanganh.stickynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.hoanganh.stickynote.Paint;
import com.example.hoanganh.stickynote.Record;


public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "Note_Manager";



    private static final String TABLE_NOTE = "Note";
    private static final String TABLE_CAL = "Cal";
    private static final String TABLE_CONTACTS = "Contacts";
    private static final String TABLE_RECORD = "Record";
    private static final String TABLE_PAINT = "Drawing";

    private static final String COLUMN_NOTE_ID ="Note_Id";
    private static final String COLUMN_NOTE_TITLE ="Note_Title";
    private static final String COLUMN_NOTE_CONTENT = "Note_Content";
    private static final String COLUMN_NOTE_DATE = "Note_Date";
    private static final String COLUMN_NOTE_COLOR = "Note_Color";

    private static final String COLUMN_CAL_ID ="Cal_Id";
    private static final String COLUMN_CAL_TITLE ="Cal_Title";
    private static final String COLUMN_CAL_CONTENT = "Cal_Content";
    private static final String COLUMN_CAL_DATE = "Cal_Date";
    private static final String COLUMN_CAL_COLOR = "Cal_Color";

    private static final String COLUMN_CAMERA_ID = "Camera_Id";
    private static final String COLUMN_CAMERA_TITLE = "Camera_title";
    private static final String COLUMN_CAMERA_CONTENT = "Camera_content";
    private static final String COLUMN_CAMERA_NAME = "Camera_Name";
    private static final String COLUMN_CAMERA_IMG = "Camera_Img";

    private static final String COLUMN_RECORD_ID = "Record_Id";
    private static final String COLUMN_RECORD_NAME = "Record_Name";
    private static final String COLUMN_RECORD_FILEPATH = "Record_Filepath";

    private static final String COLUMN_PAINT_ID = "Paint_Id";
    private static final String COLUMN_PAINT_TITLE = "Paint_title";
    private static final String COLUMN_PAINT_IMG = "Paint_Img";

    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String script = "CREATE TABLE " + TABLE_NOTE + "("
                + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_CONTENT + " TEXT,"
                + COLUMN_NOTE_DATE + " TEXT,"
                + COLUMN_NOTE_COLOR + " TEXT" + ")";

        String script1 = "CREATE TABLE " + TABLE_CAL + "("
                + COLUMN_CAL_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_CAL_TITLE + " TEXT,"
                + COLUMN_CAL_CONTENT + " TEXT,"
                + COLUMN_CAL_DATE + " TEXT,"
                + COLUMN_CAL_COLOR + " TEXT" + ")";

        String script2 = "CREATE TABLE " + TABLE_CONTACTS + "("
                + COLUMN_CAMERA_ID +" INTEGER PRIMARY KEY,"
                + COLUMN_CAMERA_TITLE +" TEXT,"
                + COLUMN_CAMERA_CONTENT +" TEXT,"
                + COLUMN_CAMERA_NAME +" TEXT,"
                + COLUMN_CAMERA_IMG  +" BLOB" + ")";

        String script4 = "CREATE TABLE " + TABLE_RECORD + "("
                + COLUMN_RECORD_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_RECORD_NAME + " TEXT,"
                + COLUMN_RECORD_FILEPATH + " TEXT" + ")";

        String script3 = "CREATE TABLE " + TABLE_PAINT + "("
                + COLUMN_PAINT_ID +" INTEGER PRIMARY KEY,"
                + COLUMN_PAINT_TITLE +" TEXT,"
                + COLUMN_PAINT_IMG  +" BLOB" + ")";
        db.execSQL(script);
        db.execSQL(script1);
        db.execSQL(script2);
        db.execSQL(script3);
        db.execSQL(script4);
    }
    //Update Table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAL);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAINT);
        onCreate(db);
    }
    //Default notes
    public void createDefaultNotesIfNeed()  {
        int count = this.getNotesCount();
        if(count ==0 ) {
            Note note1 = new Note("Firstly see Android ListView",
                    "See Android ListView Example in o7planning.org","20/11/2017 10:30","White");
            Note note2 = new Note("Learning Android SQLite",
                    "See Android SQLite Example in o7planning.org","30/11/2017 11:00","Blue");
            this.addNote(note1);
            this.addNote(note2);
        }
    }
    //Add Note
    public void addNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());
        values.put(COLUMN_NOTE_DATE, note.getNoteDate());
        values.put(COLUMN_NOTE_COLOR, note.getNoteColor());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_NOTE, null, values);


        // Đóng kết nối database.
        db.close();
    }
    //Add Calendar Note
    public void addNoteCal(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CAL_TITLE, note.getNoteTitle());
        values.put(COLUMN_CAL_CONTENT, note.getNoteContent());
        values.put(COLUMN_CAL_DATE, note.getNoteDate());
        values.put(COLUMN_CAL_COLOR, note.getNoteColor());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_CAL, null, values);


        // Đóng kết nối database.
        db.close();
    }
    //Add Camera Note
    public void addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CAMERA_TITLE, contact.getCameraTitle());
        values.put(COLUMN_CAMERA_CONTENT, contact.getCameraContent());
        values.put(COLUMN_CAMERA_NAME, contact.getCameraDate());
        values.put(COLUMN_CAMERA_IMG, contact.getCameraImg());

        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_CONTACTS, null, values);


        // Đóng kết nối database.
        db.close();
    }
    //Add Record
    public void addRecord(Record record) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECORD_NAME, record.getRecordName());
        values.put(COLUMN_RECORD_FILEPATH, record.getRecordFilepath());
        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_RECORD, null, values);
        // Đóng kết nối database.
        db.close();
    }
    //Add paint Note
    public void addPaint(Paint paint) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PAINT_TITLE, paint.getPaintTitle());
        values.put(COLUMN_PAINT_IMG, paint.getPaintImg());

        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_PAINT, null, values);


        // Đóng kết nối database.
        db.close();
    }
    //Get Note
    public Note getNote(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[] { COLUMN_NOTE_ID,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT,COLUMN_NOTE_DATE,COLUMN_NOTE_COLOR }, COLUMN_NOTE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return note
        return note;
    }
    //Get Calendar Note
    public Note getNoteCal(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CAL, new String[] { COLUMN_CAL_ID,
                        COLUMN_CAL_TITLE, COLUMN_CAL_CONTENT,COLUMN_CAL_DATE,COLUMN_CAL_COLOR }, COLUMN_CAL_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
        // return note
        return note;
    }
    //Get Camera Note
    public Contact getContact(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { COLUMN_CAMERA_ID,COLUMN_CAMERA_TITLE,COLUMN_CAMERA_CONTENT,
                        COLUMN_CAMERA_NAME, COLUMN_CAMERA_IMG }, COLUMN_CAMERA_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getBlob(4));
        // return note
        return contact;
    }
    //Get Record
    public Record getRecord(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_RECORD, new String[] { COLUMN_RECORD_ID,
                        COLUMN_RECORD_NAME, COLUMN_RECORD_FILEPATH}, COLUMN_CAL_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Record record = new Record(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return note
        return record;
    }
    //Get Paint Note
    public Paint getPaint(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PAINT, new String[] { COLUMN_PAINT_ID,COLUMN_PAINT_TITLE,
                        COLUMN_PAINT_IMG }, COLUMN_PAINT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Paint paint = new Paint(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getBlob(2));
        // return note
        return paint;
    }
    //Get All Note
    public List<Note> getAllNotes() {

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        return noteList;

    }
    //Get All Calendar Note
    public List<Note> getAllNotesCal() {

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CAL;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        // return note list
        return noteList;
    }
    //Get All Camera Note
    public List<Contact> getAllContact() {

        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setCameraId(Integer.parseInt(cursor.getString(0)));
                contact.setCameraTitle(cursor.getString(1));
                contact.setCameraContent(cursor.getString(2));
                contact.setCameraDate(cursor.getString(3));
                contact.setCameraImg(cursor.getBlob(4));
                // Thêm vào danh sách.
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return note list
        return contactList;
    }
    //Get All Record
    public List<Record> getAllRecord() {

        List<Record> RecordList = new ArrayList<Record>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_RECORD;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setRecordId(Integer.parseInt(cursor.getString(0)));
                record.setRecordName(cursor.getString(1));
                record.setRecordFilepath(cursor.getString(2));
                // Thêm vào danh sách.
                RecordList.add(record);
            } while (cursor.moveToNext());
        }

        // return note list
        return RecordList;
    }
    //Get All Paint Note
    public List<Paint> getAllPaint() {

        List<Paint> paintList = new ArrayList<Paint>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_PAINT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Paint paint = new Paint();
                paint.setPaintId(Integer.parseInt(cursor.getString(0)));
                paint.setPaintTitle(cursor.getString(1));
                paint.setPaintImg(cursor.getBlob(2));
                // Thêm vào danh sách.
                paintList.add(paint);
            } while (cursor.moveToNext());
        }

        // return note list
        return paintList;
    }
    //Get Note Count
    public int getNotesCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    //Get Note Calendar Count
    public int getNotesCountCal() {

        String countQuery = "SELECT  * FROM " + TABLE_CAL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    //Get Note Camera Count
    public int getContactCount() {

        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    //Get Record Count
    public int getRecordCount() {

        String countQuery = "SELECT  * FROM " + TABLE_RECORD;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    //Get Note Paint Count
    public int getPaintCount() {

        String countQuery = "SELECT  * FROM " + TABLE_PAINT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    //Update Note
    public int updateNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getNoteTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getNoteContent());
        values.put(COLUMN_NOTE_COLOR, note.getNoteColor());

        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
    }
    //Update Calendar Note
    public int updateNoteCal(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CAL_TITLE, note.getNoteTitle());
        values.put(COLUMN_CAL_CONTENT, note.getNoteContent());
        values.put(COLUMN_CAL_COLOR, note.getNoteColor());

        // updating row
        return db.update(TABLE_CAL, values, COLUMN_CAL_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
    }
    //Update Camera Note
    public int updateContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CAMERA_TITLE, contact.getCameraTitle());
        values.put(COLUMN_CAMERA_CONTENT, contact.getCameraContent());
        values.put(COLUMN_CAMERA_NAME, contact.getCameraDate());
        values.put(COLUMN_CAMERA_IMG, contact.getCameraImg());

        // updating row
        return db.update(TABLE_CONTACTS, values, COLUMN_CAMERA_ID + " = ?",
                new String[]{String.valueOf(contact.getCameraId())});
    }
    //Update Paint Note
    public int updatePaint(Paint paint) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PAINT_TITLE, paint.getPaintTitle());
        values.put(COLUMN_PAINT_IMG, paint.getPaintImg());

        // updating row
        return db.update(TABLE_PAINT, values, COLUMN_PAINT_ID + " = ?",
                new String[]{String.valueOf(paint.getPaintId())});
    }
    //Delete Note
    public void deleteNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_ID + " = ?",
                new String[] { String.valueOf(note.getNoteId()) });
        db.close();
    }
    //Delete Calendar Note
    public void deleteNoteCal(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CAL, COLUMN_CAL_ID + " = ?",
                new String[] { String.valueOf(note.getNoteId()) });
        db.close();
    }
    //Delete Camera Note
    public void deleteContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_CAMERA_ID + " = ?",
                new String[] { String.valueOf(contact.getCameraId()) });
        db.close();
    }
    //Delete Record
    public void deleteRecord(Record record) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECORD, COLUMN_RECORD_ID + " = ?",
                new String[] { String.valueOf(record.getRecordId()) });
        db.close();
    }
    //Delete Paint Note
    public void deletePaint(Paint paint) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAINT, COLUMN_PAINT_ID + " = ?",
                new String[] { String.valueOf(paint.getPaintId()) });
        db.close();
    }
    //Find Calendar Note By Day
    public List<Note> findbyday(int d, int m,int y,String color) {
        String date = d + "/" + m + "/" + y;
        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery;
        if(color.equals("")) {
            selectQuery = "SELECT * FROM cal WHERE Cal_Date = '" + date + "'";
        }
        else
            selectQuery = "SELECT * FROM cal WHERE Cal_Date = '" + date + "' AND Cal_Color = '" + color + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }
    //Search Note
    public List<Note> searchnote(String s){

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT * FROM Note WHERE Note_Title Like+'%"+s+"%' OR Note_Content Like+'%"+s+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }
    //Search record Note
    public List<Record> searchrecordnote(String s){

        List<Record> recordList = new ArrayList<Record>();
        // Select All Query
        String selectQuery = "SELECT * FROM Record WHERE Record_Name Like + '%"+s+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setRecordId(Integer.parseInt(cursor.getString(0)));
                record.setRecordName(cursor.getString(1));
                record.setRecordFilepath(cursor.getString(2));
                // Thêm vào danh sách.
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        return recordList;
    }
    //Search Color Note
    public List<Note> searchcolornote(String s){

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT * FROM Note WHERE Note_Color Like + '%"+s+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }
    //Search Cal Note
    public List<Note> searchcalnote(String s){

        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT * FROM Cal WHERE Cal_Title Like + '%"+s+"%' OR Cal_Content Like + '%"+s+"%' ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public List<Note> sortnoteTitle(){
        List<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT * FROM Note ORDER BY Note_Title DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(Integer.parseInt(cursor.getString(0)));
                note.setNoteTitle(cursor.getString(1));
                note.setNoteContent(cursor.getString(2));
                note.setNoteDate(cursor.getString(3));
                note.setNoteColor(cursor.getString(4));
                // Thêm vào danh sách.
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }

    public List<Record> sortrecordTitle(){
        List<Record> recordList = new ArrayList<Record>();
        // Select All Query
        String selectQuery = "SELECT * FROM Record ORDER BY Record_Name DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Record record = new Record();
                record.setRecordId(Integer.parseInt(cursor.getString(0)));
                record.setRecordName(cursor.getString(1));
                record.setRecordFilepath(cursor.getString(2));
                // Thêm vào danh sách.
                recordList.add(record);
            } while (cursor.moveToNext());
        }
        return recordList;
    }

    public List<Paint> sortpaintDate(){
        List<Paint> paintList = new ArrayList<Paint>();
        // Select All Query
        String selectQuery = "SELECT * FROM Drawing ORDER BY Paint_title DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Paint paint = new Paint();
                paint.setPaintId(Integer.parseInt(cursor.getString(0)));
                paint.setPaintTitle(cursor.getString(1));
                paint.setPaintImg(cursor.getBlob(2));
                // Thêm vào danh sách.
                paintList.add(paint);
            } while (cursor.moveToNext());
        }
        return paintList;
    }

    public List<Contact> sortcameraDate(){
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT * FROM Contacts ORDER BY Camera_Name DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setCameraId(Integer.parseInt(cursor.getString(0)));
                contact.setCameraTitle(cursor.getString(1));
                contact.setCameraContent(cursor.getString(2));
                contact.setCameraDate(cursor.getString(3));
                contact.setCameraImg(cursor.getBlob(4));
                // Thêm vào danh sách.
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }
}
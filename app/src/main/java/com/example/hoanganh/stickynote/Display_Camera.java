package com.example.hoanganh.stickynote;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import com.example.hoanganh.stickynote.Contact;
import com.example.hoanganh.stickynote.MyDatabaseHelper;
import com.example.hoanganh.stickynote.R;

public class Display_Camera extends AppCompatActivity {


    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;
    private int mode;

    private ImageView pic;
    private Button btnChange,btnSave;
    private EditText cameratitle,cameracontent;
    private Bitmap bp;
    private byte[] ima,photo;
    private  String s,title,content;
    private int d;
    private MyDatabaseHelper db;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__camera);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        openImage();
        buttonClick();
    }

    private void openImage(){

        Intent intent = this.getIntent();
        this.contact = (Contact) intent.getSerializableExtra("contact");
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            s = bundle.getString("name");
            ima = bundle.getByteArray("photo");
        }
        if(contact == null) {
            this.mode = MODE_CREATE;
        }
        else {
            this.mode = MODE_EDIT;
            cameratitle.setText(contact.getCameraTitle());
            cameracontent.setText(contact.getCameraContent());
            ima = contact.getCameraImg();
            s = contact.getCameraDate();
        }

        bp=convertToBitmap(ima);
        pic.setImageBitmap(bp);
    }

    public void init(){
        pic = (ImageView) findViewById(R.id.canvas);
        db = new MyDatabaseHelper(this);
        btnChange = (Button) findViewById(R.id.btnChangeImage);
        btnSave = (Button) findViewById(R.id.btnSaveImage);
        cameratitle = (EditText) findViewById(R.id.cameratitle);
        cameracontent = (EditText) findViewById(R.id.cameracontent);

    }
    //handler click on button change
    public void buttonClick() {

        btnChange.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                title = cameratitle.getText().toString();
                content = cameracontent.getText().toString();
                if(mode==MODE_CREATE ) {
                    photo = profileImage(bp);
                    contact= new Contact(title,content,s,photo);
                    db.addContact(contact);
                } else  {
                    contact.setCameraTitle(title);
                    contact.setCameraContent(content);
                    photo = profileImage(bp);
                    contact.setCameraImg(photo);
                    contact.setCameraDate(s);
                    db.updateContact(contact);
                }
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    //get image in gallery
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                        pic.setImageBitmap(bp);

                        Toast.makeText(getApplicationContext(),"Successfully", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
                }
        }
    }

    //Convert and resize our image to 400dp for faster uploading our images to DB
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

    //convert byte to bitmap to load image into imageview
    private Bitmap convertToBitmap(byte[] b){
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b){

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {

        // Chuẩn bị dữ liệu Intent.
        Intent data = new Intent();
        // Yêu cầu MainActivity refresh lại ListView hoặc không.
        data.putExtra("needRefresh", true);

        // Activity đã hoàn thành OK, trả về dữ liệu.
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}

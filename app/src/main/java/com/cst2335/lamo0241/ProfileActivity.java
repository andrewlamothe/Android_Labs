package com.cst2335.lamo0241;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageButton mImageButton;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_actitivty);
        Log.e(ACTIVITY_NAME,   "In activity onCreate()");
        Intent fromMain = getIntent();
        EditText emailText = findViewById(R.id.emailtext);
        emailText.setText(fromMain.getStringExtra("Email"));

        ImageButton mImageButton = findViewById(R.id.picture_button);
        Button chatButton = findViewById(R.id.gotochat);
        Button weatherButton = findViewById(R.id.gotoweather);
        mImageButton.setOnClickListener( click -> {
            dispatchTakePictureIntent();
        });

        chatButton.setOnClickListener( x -> {
            Intent goToProfile = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivity(goToProfile);
        });


        weatherButton.setOnClickListener(x -> {
            Intent gotoweather = new Intent(ProfileActivity.this, WeatherForecast.class);
            startActivity(gotoweather);
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Log.e(ACTIVITY_NAME,   "In activity onActivityResult()");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton = findViewById(R.id.picture_button);
            mImageButton.setImageBitmap(imageBitmap);
        }
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        Log.e(ACTIVITY_NAME,   "In activity onPause()");

    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.e(ACTIVITY_NAME,   "In activity onStop()");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.e(ACTIVITY_NAME,   "In activity onStart()");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.e(ACTIVITY_NAME,   "In activity onDestroy()");
    }

}
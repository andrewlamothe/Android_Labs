package com.cst2335.lamo0241;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
        EditText emailText = findViewById(R.id.email);
        SharedPreferences lab3 = getSharedPreferences("lab3", MODE_PRIVATE);

        emailText.setText(lab3.getString("email_address",""));
        Button login = findViewById(R.id.login);

        login.setOnClickListener( click -> {
            Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
            goToProfile.putExtra("Email", emailText.getText().toString());
            startActivity(goToProfile);
        });

    }
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences lab3 = getSharedPreferences("lab3", MODE_PRIVATE);
        SharedPreferences.Editor emailEdit = lab3.edit();
        EditText emailText = findViewById(R.id.email);
        emailEdit.putString("email_address", emailText.getText().toString());
        emailEdit.apply();
    }
}
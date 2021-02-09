package com.cst2335.lamo0241;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.StringSearch;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_linear);
        Switch switcher = findViewById(R.id.switcher);
        Button button1 = findViewById(R.id.button);

        button1.setOnClickListener(x -> {
               Toast.makeText(this,getString(R.string.button_click), Toast.LENGTH_LONG).show();
            }
        );

        switcher.setOnCheckedChangeListener((checkbox, isChecked) ->{
            if (isChecked){
               Snackbar switch_on = Snackbar.make(findViewById(R.id.switcher) , getString(R.string.snackbar_on), Snackbar.LENGTH_LONG);
                switch_on.setAction(R.string.undo_string, click -> checkbox.setChecked(!isChecked));
                switch_on.show();
            }
            else {
               Snackbar switch_off = Snackbar.make(findViewById(R.id.switcher) , getString(R.string.snackbar_off), Snackbar.LENGTH_LONG);

                switch_off.show();
            }
        });

    }
}
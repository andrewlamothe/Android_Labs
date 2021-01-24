package com.cst2335.lamo0241;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView myTextView = findViewById(R.id.text1);
        TextView checktext = findViewById(R.id.checked);
        EditText text2 = findViewById(R.id.text2);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        CheckBox checker = findViewById(R.id.checker);
        button1.setOnClickListener(x -> {
                counter++;
                text2.setText("Button Has been pressed " + counter + " times");
                Toast.makeText(this,"HELLO!", Toast.LENGTH_LONG).show();
            }
        );

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View r) {
                counter--;
                text2.setText("Button Has been pressed " + counter + " times");
            }
        });

        checker.setOnCheckedChangeListener((checkbox, isChecked) ->{
            if (isChecked){
                checktext.setText("Checked!");
            }
            else checktext.setText("Unchecked!");
        });

    }
}
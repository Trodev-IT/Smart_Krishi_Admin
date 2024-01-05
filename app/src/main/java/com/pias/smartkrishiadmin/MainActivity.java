package com.pias.smartkrishiadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.pias.smartkrishiadmin.AllCultivateAndFarming.agricultureinformation.AgriculturePDFActivity;
import com.pias.smartkrishiadmin.AllCultivateAndFarming.cow.CowPDFActivity;

public class MainActivity extends AppCompatActivity {

    TextView text, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        text2 = findViewById(R.id.text2);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AgriculturePDFActivity.class));
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CowPDFActivity.class));
            }
        });
    }
}
package com.pias.smartkrishiadmin.AllCultivateAndFarming.hen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pias.smartkrishiadmin.R;

public class HenPDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hen_pdfactivity);

        /*title*/
        getSupportActionBar().setTitle("হাস-মুরগী ও অন্যান্য পাখির তথ্য আপলোড করুন");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
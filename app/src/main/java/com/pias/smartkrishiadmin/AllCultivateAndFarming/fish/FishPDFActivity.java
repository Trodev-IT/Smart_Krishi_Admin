package com.pias.smartkrishiadmin.AllCultivateAndFarming.fish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pias.smartkrishiadmin.R;

public class FishPDFActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_pdfactivity);

        /*title*/
        getSupportActionBar().setTitle("মাছ চাষের তথ্য আপলোড করুন");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
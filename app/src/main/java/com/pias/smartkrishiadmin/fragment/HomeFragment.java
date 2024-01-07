package com.pias.smartkrishiadmin.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pias.smartkrishiadmin.AllCultivateAndFarming.agricultureinformation.AgriculturePDFActivity;
import com.pias.smartkrishiadmin.AllCultivateAndFarming.cow.CowPDFActivity;
import com.pias.smartkrishiadmin.AllCultivateAndFarming.fish.FishPDFActivity;
import com.pias.smartkrishiadmin.AllCultivateAndFarming.hen.HenPDFActivity;
import com.pias.smartkrishiadmin.AnotherCultivateAndTechnology.AnotherCultivateActivity;
import com.pias.smartkrishiadmin.R;

public class HomeFragment extends Fragment {
    CardView farmer, fish, cow, hen, anothercultivate;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        farmer= view.findViewById(R.id.farmer);
        fish= view.findViewById(R.id.fish);
        cow= view.findViewById(R.id.cow);
        hen= view.findViewById(R.id.hen);
        anothercultivate= view.findViewById(R.id.anothercultivate);

        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AgriculturePDFActivity.class));
            }
        });
        fish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FishPDFActivity.class));
            }
        });
        cow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CowPDFActivity.class));
            }
        });
        hen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HenPDFActivity.class));
            }
        });
        anothercultivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AnotherCultivateActivity.class));
            }
        });

        return view;
    }
}
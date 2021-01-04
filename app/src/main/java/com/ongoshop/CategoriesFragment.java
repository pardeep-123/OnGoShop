package com.ongoshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CategoriesFragment extends Fragment {
    View v;
    Context mContext;
    TextView tvDrinks;
    Button btnCategories;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_categories, container, false);
       mContext=getActivity();
        tvDrinks=v.findViewById(R.id.tvDrinks);
        btnCategories=v.findViewById(R.id.btnCategories);
        btnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),ManageCategoryActivity.class);
                startActivity(i);
            }
        });
        tvDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),SubCategoriesActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}
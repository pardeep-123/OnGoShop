package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ongoshop.R;

public class ManageCategoryActivity extends AppCompatActivity {
LinearLayout llIndividual,llProduct;
Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);
        mContext=this;
        ImageView ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llProduct=findViewById(R.id.llProduct);
        llIndividual=findViewById(R.id.llIndividual);
        llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ManageCategoryActivity.this, AddCategoryActivity.class);
                startActivity(i);
            }
        });
        llIndividual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ManageCategoryActivity.this, BarcodeScannerActivity.class);
                startActivity(i);
            }
        });
    }
}
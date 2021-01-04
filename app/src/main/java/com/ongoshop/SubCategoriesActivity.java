package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SubCategoriesActivity extends AppCompatActivity {
ImageView ivBack;
Context mContext;
LinearLayout ll_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        mContext=this;

        ivBack=findViewById(R.id.ivBack);
        ll_data=findViewById(R.id.ll_data);
        ll_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(SubCategoriesActivity.this,ProductActivity.class);
                startActivity(i);
            }
        });
    }
}
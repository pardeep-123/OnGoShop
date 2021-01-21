package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ongoshop.R;

public class AddCategoryActivity extends AppCompatActivity {
Button btnItem;
Context mContext;
EditText edSelect;
ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        mContext=this;
        btnItem=findViewById(R.id.btnItem);
        ivBack=findViewById(R.id.ivBack);
        edSelect=findViewById(R.id.edSelect);
        edSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(AddCategoryActivity.this, ProductCategoriesActivity.class);
                startActivity(i);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(AddCategoryActivity.this,AddProductActivity.class);
                startActivity(i);
            }
        });
    }
}
package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.R;
import com.ongoshop.activities.CompleteProfileActivity;

public class VerficationCodeActivity extends AppCompatActivity {

    Button btnSubmit;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication_code);
        mContext =this;
        btnSubmit=findViewById(R.id.btnSubmit);
       ImageView ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Go();
            }
        });
    }
    public void Go(){
        Intent intent= new Intent( this, CompleteProfileActivity.class);
        startActivity(intent);
    }
}
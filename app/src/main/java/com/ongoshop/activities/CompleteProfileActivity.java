package com.ongoshop.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.utils.helperclasses.image;
import com.ongoshop.R;

public class CompleteProfileActivity extends image {

    Button btnSubmit;
    Context mContext;
    ImageView ivcamera,ivImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
        mContext=this;
        btnSubmit=findViewById(R.id.btnSubmit);
        ImageView ivBack=findViewById(R.id.ivBack);
         ivcamera=findViewById(R.id.ivcamera);
        ivImg=findViewById(R.id.ivImg);
        ivcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image("all");
            }
        });
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
        Intent intent= new Intent( this, DeliveryOptionsActivity.class);
        startActivity(intent);
    }

    @Override
    public void selectedImage(Bitmap var1, String var2) {
        ivImg.setImageBitmap(var1);
    }
}
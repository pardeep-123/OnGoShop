package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.Halper.image;

public class MyShopEditActivity extends image {
    Context mContext;
    Button btnSubmit;
    ImageView ivAdd,ivImgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shop_edit);
        mContext=this;
        btnSubmit=findViewById(R.id.btnSubmit);
        ivAdd=findViewById(R.id.ivAdd);
        ivImgs=findViewById(R.id.ivImg);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image("all");
            }
        });
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
                Intent i= new Intent(MyShopEditActivity.this,MyShopActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void selectedImage(Bitmap var1, String var2) {
        ivImgs.setImageBitmap(var1);
    }
}
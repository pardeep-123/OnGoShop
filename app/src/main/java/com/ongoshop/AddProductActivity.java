package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ongoshop.Halper.image;

public class AddProductActivity extends image {
Button btnProceed;
Context mContext;
    ImageView ivOn,ivOff;
    LinearLayout ll_chackbox;
    int temp=2;
ImageView ivImg,ivcamera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        mContext=this;
       ImageView ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivImg=findViewById(R.id.ivImg);
        ivcamera=findViewById(R.id.ivcamera);
        ivOn=findViewById(R.id.ivOn);
        ivOff=findViewById(R.id.ivOff);
        ll_chackbox=findViewById(R.id.ll_chackbox);

        ll_chackbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(temp%2==0)
                {

                    ivOff.setVisibility(View.GONE);
                    ivOn.setVisibility(View.VISIBLE);
                }
                else
                {
                    ivOn.setVisibility(View.GONE);
                    ivOff.setVisibility(View.VISIBLE);
                }
                temp++;
            }
        });

        ivcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image("all");
            }
        });
        btnProceed=findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddProductActivity.this,HomeActivity.class);
                i.putExtra("type","my");
                startActivity(i);
            }
        });
    }

    @Override
    public void selectedImage(Bitmap var1, String var2) {
        ivImg.setImageBitmap(var1);
    }
}
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ongoshop.Halper.image;

public class CreateAccountActivity extends image {

    Context mContext;
    Button btnVerification;
    TextView tvSignin,tvTerms;
    ImageView ivBack,ivOn,ivOff,ivImg;
    int temp=2;
    RelativeLayout rlAdd;
    LinearLayout ll_chackbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mContext =this;
        btnVerification=findViewById(R.id.btnVerification);
        tvSignin=findViewById(R.id.tvSignin);
        tvTerms=findViewById(R.id.tvTerms);
        ivBack=findViewById(R.id.ivBack);
        ivOn=findViewById(R.id.ivOn);
        ivImg=findViewById(R.id.iv_profile);
        rlAdd=findViewById(R.id.rlAdd);
        rlAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image("all");
            }
        });
        ivOff=findViewById(R.id.ivOff);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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
        tvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccountActivity.this,TermsActivity.class);
                startActivity(i);
            }
        });
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAccountActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        btnVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go();
            }
        });
    }
    public void Go(){
        Intent intent= new Intent( this, VerficationCodeActivity.class);
        startActivity(intent);
    }

    @Override
    public void selectedImage(Bitmap var1, String var2) {
        ivImg.setImageBitmap(var1);
    }
}
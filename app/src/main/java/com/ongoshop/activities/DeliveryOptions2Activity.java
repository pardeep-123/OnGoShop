package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.R;

public class DeliveryOptions2Activity extends AppCompatActivity {
    Button btnConfirm;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_options2);
        mContext =this;
        btnConfirm=findViewById(R.id.btnConfirm);
        ImageView ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go();
            }
        });
    }
    public void Go(){
        Intent intent= new Intent( this, DeliveryOptions3Activity.class);
        startActivity(intent);
    }
}
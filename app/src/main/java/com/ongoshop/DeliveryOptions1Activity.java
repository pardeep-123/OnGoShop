package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class DeliveryOptions1Activity extends AppCompatActivity {
    Button btnConfirm;
    Context mContext;
    RecyclerView recyclerview;
    DeliveryOptions1Adapter deliveryOptions1Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_options1);
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
        recyclerview=findViewById(R.id.recyclerview);
        deliveryOptions1Adapter = new DeliveryOptions1Adapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(deliveryOptions1Adapter) ;
    }
    public void Go(){
        Intent intent= new Intent( this, DeliveryOptions2Activity.class);
        startActivity(intent);
    }
}
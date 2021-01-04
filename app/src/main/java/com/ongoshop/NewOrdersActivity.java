package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NewOrdersActivity extends AppCompatActivity {
    ImageView ivBack,ivNoti;
    Context mContext;
    RecyclerView recyclerview;
    NewOrderAdapter newOrderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_orders);
        mContext=this;
        ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivNoti=findViewById(R.id.ivNoti);
        ivNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(NewOrdersActivity.this,NotificationActivity.class);
                startActivity(i);
            }
        });
        recyclerview=findViewById(R.id.recyclerview);
        newOrderAdapter = new NewOrderAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(newOrderAdapter) ;
    }
}
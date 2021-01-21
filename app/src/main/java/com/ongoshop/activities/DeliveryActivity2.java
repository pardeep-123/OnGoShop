package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.R;

public class DeliveryActivity2 extends AppCompatActivity {
    ImageView ivBack;
    Context mContext;
    Button btnDelivery,btnDelivery2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery2);
        mContext=this;
        ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnDelivery=findViewById(R.id.btnDelivery);
       /* btnDelivery2=findViewById(R.id.btnDelivery2);
        btnDelivery2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });*/
        btnDelivery.setTag(1);
        btnDelivery.setText("Items on Board for delivery");
        btnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnDelivery.getText().toString().equals("Complete Delivery")){
                    btnDelivery.setText("Complete Delivery");
                    Intent intent = new Intent(DeliveryActivity2.this, HomeActivity.class);
                    startActivity(intent);
                    //openNewActivity();
                    v.setTag(1); //pause
                }else {
                    final int status =(Integer) v.getTag();
                    if(status == 1) {
                        btnDelivery.setText("Items on Board for delivery");
                        v.setTag(0); //pause
                    } else {
                        btnDelivery.setText("Complete Delivery");

                        //openNewActivity();
                        v.setTag(1); //pause
                    } } } });
    }
    public void openNewActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
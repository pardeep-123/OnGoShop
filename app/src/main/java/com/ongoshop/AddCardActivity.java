package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AddCardActivity extends AppCompatActivity {
    Context mContext;
    ImageView ivBack,ivOn,ivOff,ivImg;
    LinearLayout ll_chackbox;
    Button btnSave;
    int temp=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        mContext =this;
        ivBack=findViewById(R.id.ivBack);
        ivOn=findViewById(R.id.ivOn);
        ivOff=findViewById(R.id.ivOff);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( AddCardActivity.this, PaymentActivity.class);
                startActivity(intent);
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
    }
}
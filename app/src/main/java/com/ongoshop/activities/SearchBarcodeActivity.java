package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.R;

public class SearchBarcodeActivity extends AppCompatActivity {
Context mContext;
Button btnSearchBarcode;
ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_barcode);
        mContext=this;
        ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSearchBarcode=findViewById(R.id.btnSearchBarcode);
        btnSearchBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(SearchBarcodeActivity.this, ProductDetailActivity.class);
                startActivity(i);
            }
        });
    }
}
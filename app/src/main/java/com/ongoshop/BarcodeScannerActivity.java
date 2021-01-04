package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ongoshop.Halper.image;

public class BarcodeScannerActivity extends image {
Context mContext;
ImageView ivBack,ivImg;
Button btnSearch,btnItem;
RelativeLayout llBarcode;
    private static final int pic_id = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        mContext=this;
        ivBack=findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnItem=findViewById(R.id.btnItem);
        ivImg=findViewById(R.id.ivImg);
        llBarcode=findViewById(R.id.llBarcode);
        llBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image("camera");
            }
        });
        btnSearch=findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BarcodeScannerActivity.this,SearchBarcodeActivity.class);
                startActivity(i);
            }
        });
        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(BarcodeScannerActivity.this,SearchBarcode1Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void selectedImage(Bitmap var1, String var2) {

    }
}
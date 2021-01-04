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
import android.widget.LinearLayout;

public class ProductActivity extends AppCompatActivity {
    ImageView ivBack;
    Context mContext;
 Button btnAddProducts;
    RecyclerView recyclerview;
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        mContext=this;

        ivBack=findViewById(R.id.ivBack);
        btnAddProducts=findViewById(R.id.btnAddProducts);
        btnAddProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(ProductActivity.this,AddProductActivity.class);
                startActivity(i);
            }
        });
        recyclerview=findViewById(R.id.recyclerview);
        productAdapter = new ProductAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(productAdapter) ;
    }
}
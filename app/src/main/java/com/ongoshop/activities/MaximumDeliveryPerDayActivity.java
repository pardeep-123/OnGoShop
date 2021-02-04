package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ongoshop.R;
import com.ongoshop.pojo.VendorDeliveryCharge;
import com.ongoshop.utils.others.CommonMethods;

import java.util.ArrayList;

public class MaximumDeliveryPerDayActivity extends AppCompatActivity {
    Button btnConfirm;
    private MaximumDeliveryPerDayActivity mContext;
    private ArrayList<VendorDeliveryCharge> vendorDeliveryChargesList = new ArrayList();
    private EditText et_max_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_delivery_perday);
        mContext =this;
        btnConfirm=findViewById(R.id.btnConfirm);
        ImageView ivBack=findViewById(R.id.ivBack);
         et_max_number=findViewById(R.id.et_max_number);

       if (getIntent() !=null){
         vendorDeliveryChargesList= getIntent().getParcelableArrayListExtra("vendorDeliveryCharges");
       }
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
        if ( et_max_number.getText().toString().equals("") || et_max_number.getText().toString().isEmpty()){
            CommonMethods.AlertErrorMessage(mContext, getString(R.string.max_deliveries_error));
        }else {
            if (getIntent() !=null) {
                Intent intent = new Intent(mContext, DeliveryChargesActivity.class);
                intent.putExtra("shopName", getIntent().getStringExtra("shopName"));
                intent.putExtra("categoryName", getIntent().getStringExtra("categoryName"));
                intent.putExtra("shopABN", getIntent().getStringExtra("shopABN"));
                intent.putExtra("buildingNumber", getIntent().getStringExtra("buildingNumber"));
                intent.putExtra("streetNumber", getIntent().getStringExtra("streetNumber"));
                intent.putExtra("city", getIntent().getStringExtra("city"));
                intent.putExtra("state", getIntent().getStringExtra("state"));
                intent.putExtra("country", getIntent().getStringExtra("country"));
                intent.putExtra("postalCode", getIntent().getStringExtra("postalCode"));
                intent.putExtra("openTime", getIntent().getStringExtra("openTime"));
                intent.putExtra("closeTime", getIntent().getStringExtra("closeTime"));
                intent.putExtra("shopImage", getIntent().getStringExtra("shopImage"));
                intent.putExtra("homeDelivery", getIntent().getStringExtra("homeDelivery"));
                intent.putExtra("deliveriesPerDay", et_max_number.getText().toString().trim());

                intent.putExtra("deliveryOptionsJsonString", getIntent().getStringExtra("deliveryOptionsJsonString"));
                intent.putParcelableArrayListExtra("vendorDeliveryCharges", vendorDeliveryChargesList);
                startActivity(intent);

            }
        }
    }
}
package com.ongoshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ongoshop.activities.MyProductsActivity;
import com.ongoshop.activities.NewOrdersActivity;
import com.ongoshop.activities.ReadyForPickupActivity;
import com.ongoshop.R;
import com.ongoshop.activities.ReadyForDeliveryActivity;


public class CurrentFragment extends Fragment {
    View v;
    Context mContext;
    RelativeLayout rlOrder,rlProgress,rlDelivery,rlPickup, rlMyAddedProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_current, container, false);
        mContext=getActivity();

        rlOrder=v.findViewById(R.id.rlOrder);
        rlProgress=v.findViewById(R.id.rlProgress);
        rlDelivery=v.findViewById(R.id.rlDelivery);
        rlPickup=v.findViewById(R.id.rlPickup);
        rlMyAddedProducts=v.findViewById(R.id.rl_my_added_products);
        rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), NewOrdersActivity.class);
                i.putExtra("Orders", "0");
                startActivity(i);
            }
        });
        rlProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), NewOrdersActivity.class);
                i.putExtra("Orders", "1");
                startActivity(i);
            }
        });
        rlDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), ReadyForDeliveryActivity.class);
                i.putExtra("readyforDelivery", "2");
                startActivity(i);
            }
        });
        rlPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), ReadyForPickupActivity.class);
                i.putExtra("readyforPickup", "2");

                startActivity(i);
            }
        });
        rlMyAddedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), MyProductsActivity.class);
                i.putExtra("from", "Home");
                startActivity(i);
            }
        });
        return v;
    }
}
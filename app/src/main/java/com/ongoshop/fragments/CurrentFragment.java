package com.ongoshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ongoshop.activities.NewOrdersActivity;
import com.ongoshop.activities.OrderDetailActivity;
import com.ongoshop.activities.PickupActivity;
import com.ongoshop.R;
import com.ongoshop.activities.DeliveryActivity;


public class CurrentFragment extends Fragment {
    View v;
    Context mContext;
    RelativeLayout rlOrder,rlProgress,rlDelivery,rlPickup;

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
        rlOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), NewOrdersActivity.class);
                startActivity(i);
            }
        });
        rlProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), OrderDetailActivity.class);
                startActivity(i);
            }
        });
        rlDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), DeliveryActivity.class);
                startActivity(i);
            }
        });
        rlPickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), PickupActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
}
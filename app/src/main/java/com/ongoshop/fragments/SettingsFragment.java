package com.ongoshop.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ongoshop.R;
import com.ongoshop.activities.ChangePasswordActivity;
import com.ongoshop.activities.SalesDataActivity;
import com.ongoshop.activities.SubscriptionActivity;

import com.ongoshop.activities.LoginActivity;
import com.ongoshop.activities.ManagerManagmentActivity;
import com.ongoshop.activities.TermsConditionsActivity;

public class SettingsFragment extends Fragment {
    Context mContext;
    View v;
    RelativeLayout rlChange,rlTerms,rlLogout,rlSubscription,rlSales,rlStaff;
Dialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_settings, container, false);
        mContext=getActivity();
        rlChange=v.findViewById(R.id.rlChange);
        rlTerms=v.findViewById(R.id.rlTerms);
        rlLogout=v.findViewById(R.id.rlLogout);
        rlSales=v.findViewById(R.id.rlSales);
        rlStaff=v.findViewById(R.id.rlStaff);
        rlStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManagerManagmentActivity.class);
                startActivity(intent);
            }
        });
        rlSubscription=v.findViewById(R.id.rlSubscription);
        rlSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SalesDataActivity.class);
                startActivity(intent);
            }
        });
        rlSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
            }
        });
        rlLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDailog();
            }
        });
        rlTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TermsConditionsActivity.class);
                startActivity(intent);
            }
        });
        rlChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        return  v;
    }
    public void showDailog(){

        dialog = new Dialog(mContext);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.alert_logout);
        dialog.setCancelable(true);
        Button btnYes,btnNo;

        btnYes= dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                dialog.dismiss();
            }
        });btnNo= dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
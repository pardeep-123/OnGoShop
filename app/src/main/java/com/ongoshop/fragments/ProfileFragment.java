package com.ongoshop.fragments;

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
import com.ongoshop.activities.EditProfileActivity;
import com.ongoshop.activities.MyShopActivity;

public class ProfileFragment extends Fragment {
    View v;
    Context mContext;
    Button btnEditProfile;
    RelativeLayout rlShop;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_profile, container, false);
        mContext=getActivity();
        rlShop=v.findViewById(R.id.rlShop);
        rlShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), MyShopActivity.class);
                startActivity(i);
            }
        });
        btnEditProfile=v.findViewById(R.id.btnEditProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), EditProfileActivity.class);
                startActivity(i);
            }
        });

        return v;

    }
}
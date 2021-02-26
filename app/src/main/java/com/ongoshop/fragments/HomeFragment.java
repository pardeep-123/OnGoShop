package com.ongoshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ongoshop.activities.NotificationActivity;
import com.ongoshop.R;

public class HomeFragment extends Fragment implements View.OnClickListener {
Context mContext;
View v;
ImageView iVNoti;
Button btnCurrent,btnPast;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_home, container, false);
        mContext=getActivity();
        iVNoti=v.findViewById(R.id.iVNoti);
        btnPast=v.findViewById(R.id.btnPast);
        btnCurrent=v.findViewById(R.id.btnCurrent);
        btnCurrent.setOnClickListener( this);
        btnPast.setOnClickListener(this);
        iVNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(), NotificationActivity.class);
                startActivity(i);
            }
        });
        replaceFragment(new CurrentFragment());
        return v;
    }

    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btnCurrent:
                fragment = new CurrentFragment();
                replaceFragment(fragment);
                btnCurrent.setTextColor(getResources().getColor(R.color.white));
                btnPast.setTextColor(getResources().getColor(R.color.grey));
                break;
            case R.id.btnPast:
                fragment = new PastOrderFragment();
                replaceFragment(fragment);
                /*Bundle args = new Bundle();
                args.putString("pastOrders", "4");
                fragment.setArguments(args);
                requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
*/
                btnPast.setTextColor(getResources().getColor(R.color.white));
                btnCurrent.setTextColor(getResources().getColor(R.color.grey));
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
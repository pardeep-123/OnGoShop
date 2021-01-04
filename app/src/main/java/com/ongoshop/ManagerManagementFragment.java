package com.ongoshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ManagerManagementFragment extends Fragment {
Context mContext;
View v;
    RecyclerView recyclerview;
    Button btnAdd;
    ManagerManagementAdapter managerManagementAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_manager_management, container, false);
        mContext=getActivity();
        recyclerview=v.findViewById(R.id.recyclerview);
        btnAdd=v.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getActivity(),AddEmployeeActivity.class);
                startActivity(i);
            }
        });
        managerManagementAdapter = new ManagerManagementAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(managerManagementAdapter) ;

        return v;
    }
}
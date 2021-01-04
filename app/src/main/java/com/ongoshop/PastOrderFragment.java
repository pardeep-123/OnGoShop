package com.ongoshop;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class PastOrderFragment extends Fragment {
Context mContext;
View v;
    RecyclerView recyclerview;
    PastOrderAdapter pastOrderAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_past_order, container, false);
        mContext=getActivity();
        recyclerview=v.findViewById(R.id.recyclerview);
        pastOrderAdapter = new PastOrderAdapter(mContext);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(pastOrderAdapter) ;

        return v;
    }
}
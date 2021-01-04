package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    Context mContext;
    RecyclerView recyclerview;
    TestAdapter testAdapter;
    List<TestPOJO> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mContext=this;
        recyclerview=findViewById(R.id.recyclerview);
        testAdapter = new TestAdapter(mContext,list);
        recyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview.setAdapter(testAdapter) ;
        //list= Arrays.asList(getResources().getStringArray(R.array.android_versions));
        list.add(new TestPOJO("Daniel Shiffman"));
        list.add(new TestPOJO("Jhon Doe"));
        list.add(new TestPOJO("Jane Doe"));


        testAdapter.notifyDataSetChanged();
    }
}
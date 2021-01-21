package com.ongoshop.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.ongoshop.R;
import com.ongoshop.models.TestPOJO;
import com.ongoshop.adapter.TestAdapter;

import java.util.ArrayList;
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
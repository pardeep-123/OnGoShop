package com.ongoshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ManagerManagmentActivity extends AppCompatActivity {
    Context mContext;
    View v;
    ImageView ivBack;
    Button btnEmployee,btnManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_managment);
        mContext=this;
        ivBack=findViewById(R.id.ivBack);
        changeFragment(new EmployeeFragment());
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnEmployee=findViewById(R.id.btnEmployee);
        btnManager=findViewById(R.id.btnManager);
        btnEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new EmployeeFragment());
                btnEmployee.setTextColor(getResources().getColor(R.color.white));
                btnManager.setTextColor(getResources().getColor(R.color.grey));
            }
        });
        btnManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ManagerManagementFragment());
                btnManager.setTextColor(getResources().getColor(R.color.white));
                btnEmployee.setTextColor(getResources().getColor(R.color.grey));
            }
        });
    }
    void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

    }
}
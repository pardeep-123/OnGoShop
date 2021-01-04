package com.ongoshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
Context mContext;
TextView tvForgotpassword,tvCreateAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        tvForgotpassword=findViewById(R.id.tvForgotpassword);
        tvCreateAccount=findViewById(R.id.tvCreateAccount);
        Button btnSignin=findViewById(R.id.btnSignin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
                finishAffinity();
            }
        });
        tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    create();
            }
        });
        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Go();
            }
        });
    }
    public void Go(){
        Intent intent= new Intent( this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
    public void create(){
        Intent intent= new Intent( this, CreateAccountActivity.class);
        startActivity(intent);
    }
}

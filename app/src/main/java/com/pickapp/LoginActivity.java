package com.pickapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.pickapp.R.id._or;
import static com.pickapp.R.id.reg_id;

public class LoginActivity extends AppCompatActivity
{
    TextView txtView;
    Button signin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtView = (TextView) findViewById(_or);
        //txtView.bringToFront();
        signin  = (Button) findViewById(reg_id);
        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
}

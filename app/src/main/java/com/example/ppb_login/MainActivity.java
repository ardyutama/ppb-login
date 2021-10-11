package com.example.ppb_login;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ppb_login.DBHelper.DBHelper;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbhelper;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new Session(this);
        if(!session.loggedin()){
            logout();
        }
        TextView nama = findViewById(R.id.tv_namaMain);
        findViewById(R.id.button_logoutMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    private void logout(){
        session.setLoggedin(false);
        startActivity(new Intent(getBaseContext(),LoginActivity.class));
        finish();
    }
}
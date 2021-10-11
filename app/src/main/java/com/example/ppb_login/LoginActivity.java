package com.example.ppb_login;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ppb_login.DBHelper.DBHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText mViewUser, mViewPassword;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mViewUser=findViewById(R.id.et_emailSignin);
        mViewPassword =findViewById(R.id.et_passwordSignin);
        dbHelper = new DBHelper(this);
        mViewPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    validationLogin();
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.button_signinSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validationLogin();
            }
        });
        findViewById(R.id.button_signupSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Preferences.getLoggedInStatus(getBaseContext())){
            startActivity(new Intent(getBaseContext(),MainActivity.class));
            finish();
        }
    }

    private void validationLogin(){
        /* Mereset semua Error dan fokus menjadi default */
        String user = mViewUser.getText().toString();
        String password = mViewPassword.getText().toString();
        Boolean res = dbHelper.checkUser(user,password);
        mViewUser.setError(null);
        mViewPassword.setError(null);
        if(res == true){
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else {
            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void masuk(){
        Preferences.setLoggedInUser(getBaseContext(),Preferences.getRegisteredUser(getBaseContext()));
        Preferences.setLoggedInStatus(getBaseContext(),true);
        startActivity(new Intent(getBaseContext(),MainActivity.class));finish();
    }
//    private void checklogin(String user,String password){
//        View fokus = null;
//        boolean cancel = false;
//        if (TextUtils.isEmpty(user)){
//            mViewUser.setError("This field is required");
//            fokus = mViewUser;
//            cancel = true;
//        }else if(!cekUser(user)){
//            mViewUser.setError("This Username is not found");
//            fokus = mViewUser;
//            cancel = true;
//        }
//
//        if (TextUtils.isEmpty(password)){
//            mViewPassword.setError("This field is required");
//            fokus = mViewPassword;
//            cancel = true;
//        }else if (!cekPassword(password)){
//            mViewPassword.setError("This password is incorrect");
//            fokus = mViewPassword;
//            cancel = true;
//        }
//        if (cancel) fokus.requestFocus();
//        else masuk();
//    }

    private boolean cekPassword(String password){
        return password.equals(Preferences.getRegisteredPass(getBaseContext()));
    }

    private boolean cekUser(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}
package com.example.nishant.practiceappmarch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Register extends AppCompatActivity {
EditText name,email,phone, pwd,cfpw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=(EditText)findViewById(R.id.editText3);
        email=(EditText)findViewById(R.id.editText4);
        phone=(EditText)findViewById(R.id.editText5);
        pwd=(EditText)findViewById(R.id.editText6);
        cfpw=(EditText)findViewById(R.id.editText7);

    }
    public  void SignUp(View view)
    {
        String nam,emai,phon,pw,cfp,method="signup";
        nam=name.getText().toString();
        emai=email.getText().toString();
        phon=phone.getText().toString();
        pw=pwd.getText().toString();
        cfp=cfpw.getText().toString();

            BackGroundTask SignUp=new BackGroundTask(method,emai,pw,this);
            SignUp.execute(method,nam,emai,phon,pw,cfp);
            //finish();

    }
}

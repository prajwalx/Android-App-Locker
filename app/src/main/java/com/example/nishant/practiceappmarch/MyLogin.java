package com.example.nishant.practiceappmarch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MyLogin extends AppCompatActivity {

    EditText em;
    EditText pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);
        em=(EditText)findViewById(R.id.editText);
        pw=(EditText)findViewById(R.id.editText2);



    }
    public void Login(View view)
    {


        String method="login";
        String email,pass;
        email=em.getText().toString();
        pass=pw.getText().toString();

        BackGroundTask backGroundTask=new BackGroundTask(method,email,pass,this);
        backGroundTask.execute(method,email,pass);

        //finish();
    }

    public void Signup(View view)
    {
        Intent signupIntent=new Intent(this,Register.class);
        startActivity(signupIntent);
        finish();
    }
}

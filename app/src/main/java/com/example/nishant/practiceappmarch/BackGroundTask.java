package com.example.nishant.practiceappmarch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.CharBuffer;

/**
 * Created by Nishant on 13-Mar-17.
 */

public class BackGroundTask extends AsyncTask<String,Void,String> {

    Context ctx;
    String method,email,password;



    BackGroundTask(String met,String ema,String pw,Context ctx)
    {
        method=met;
        email=ema;
        password=pw;
        this.ctx=ctx;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if(s.equals("Success"))
        {
            Toast.makeText(ctx,s+" Login",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(ctx,MainActivity.class);
            intent.putExtra("email",email);
            ctx.startActivity(intent);

            ((Activity)ctx).finish();

        }
        else if(s.equals("Registration Success"))
        {
            Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
            Intent intent=new Intent(ctx,MyLogin.class);
            ctx.startActivity(intent);
            ((Activity)ctx).finish();

        }
        else
        {
            //Intent intent=new Intent(ctx,MyLogin.class);
            //ctx.startActivity(intent);
            //Toast.makeText(ctx,"Wrong Email or Password",Toast.LENGTH_LONG).show();
            Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected String doInBackground(String... strings) {
        String log_url="http://192.168.1.10/MyProject/Login.php";
        String reg_url="http://192.168.1.10/MyProject/Register.php";
        String method=strings[0];


        //if(strings[2].equals(""))
        //{
          //Intent i1=new Intent(ctx,Register.class);
        //i1.putExtra("EMAIL",strings[1]);
        //ctx.startActivity(i1);
        //}

        if(method.equals("login"))
        {

            String mEmail=strings[1];
            String mPassword=strings[2];

            try {
                URL url = new URL(log_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(mEmail, "UTF-8") +
                        "&" + URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(mPassword, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                httpURLConnection.setRequestMethod("GET");
                InputStream is = httpURLConnection.getInputStream();
                //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String res = "";
                //bufferedReader.read(CharBuffer.wrap(res));
                res=bufferedReader.readLine();
                bufferedReader.close();
                is.close();

                return res;
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
        }
        else if(method.equals("signup"))
        {
            String name=strings[1];
            String email=strings[2];
            String phone=strings[3];
            String pwd=strings[4];
            String cfpw=strings[5];
            if(name.equals("")||email.equals("")||phone.equals("")||!email.contains("@"))
            {
                return "Please fill The form";
            }
            if(pwd.length()<5)
                return "Password must be atleast 5 characters";
            if(!pwd.equals(cfpw))
                return "Password mismatch";

            try
            {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data=URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+
                        "&"+URLEncoder.encode("user_pass","UTF-8")+"="+URLEncoder.encode(pwd,"UTF-8")+
                        "&"+URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+
                        "&"+URLEncoder.encode("user_phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();
                httpURLConnection.setRequestMethod("GET");
                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String res="";
                res=bufferedReader.readLine();
                bufferedReader.close();
                is.close();
                return res;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return "Failure(App)";
    }


}

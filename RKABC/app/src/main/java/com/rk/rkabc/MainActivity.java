package com.rk.rkabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    int flag = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button s = findViewById(R.id.mstudent);
        Button t = findViewById(R.id.mteacher);
        Button login = findViewById(R.id.mlogin);

        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1 ){
                    Intent i = new Intent(getApplicationContext(),StudentRegistration.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"No connection to server",Toast.LENGTH_SHORT).show();
                }
            }
        });

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1 ){
                    Intent i = new Intent(getApplicationContext(),TeacherRegistration.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"No connection to server",Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag==1 ){
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"No connection to server",Toast.LENGTH_SHORT).show();
                }
            }
        });

        makerequest();
    }

    void makerequest(){
        StringRequest s = new StringRequest(Request.Method.POST, new URL().url+"check.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("ok")){
                    //Toast.makeText(getApplicationContext(),"goo",Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(getApplicationContext(),Login.class);
//                    startActivity(i);
                    flag=1;
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot connect to server",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error" +error,Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(s);
    }
}

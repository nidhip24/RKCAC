package com.rk.rkabc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rk.rkabc.admin.AdminHome;

import java.util.HashMap;
import java.util.Map;

import static com.rk.rkabc.StudentRegistration.isEmailValid;

public class Login extends AppCompatActivity {

    EditText email,pass;


    int flag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.memail);
        pass = findViewById(R.id.mpass);

        Button btn = findViewById(R.id.mlogin);

        Button s = findViewById(R.id.mstudent);
        Button t = findViewById(R.id.mteacher);

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

        makerequest2();

        email.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (!isEmailValid(email.getText().toString())) {
                    email.setError("Enter valid emali id");
                } else {
                    email.setError(null);
                }
            }
        });

        pass.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (pass.getText().toString().equals("")) {
                    pass.setError("Enter Password");
                } else {
                    pass.setError(null);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("") && !pass.getText().toString().equals("")){
                    if (!isEmailValid(email.getText().toString())) {
                        email.setError("Enter valid emali id");
                        Toast.makeText(getApplicationContext(),"Enter valid email id",Toast.LENGTH_SHORT).show();
                    } else {
                        email.setError(null);
                        makerequest(email.getText().toString(), pass.getText().toString());
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"one of the field is empty",Toast.LENGTH_SHORT).show();
                    //email.setError("Enter valid emali id");
                    //pass.setError("Enter valid password");
                }
            }
        });
    }

    void makerequest2(){
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


    void makerequest(final String uu, final String pp){
        URL u = new URL();
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"logging in...",Toast.LENGTH_SHORT).show();

                    UserData u = new UserData();
                    u.saveUserData(getApplicationContext(),uu);

                    Toast.makeText(getApplicationContext(),"stud",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if(response.trim().equals("admin")){
                    UserData u = new UserData();
                    u.saveUserData(getApplicationContext(),uu);
                    Toast.makeText(getApplicationContext(),"admin",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), AdminHome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else if(response.trim().equals("teacher")){
                    UserData u = new UserData();
                    u.saveUserData(getApplicationContext(),uu);
                    Toast.makeText(getApplicationContext(),"Teacher account is not activated yet",Toast.LENGTH_SHORT).show();
                    //Intent intent=new Intent(getApplicationContext(),AdminC.class);
                    //startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Invalid username or or password",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", uu);
                params.put("pass",pp);
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }
}

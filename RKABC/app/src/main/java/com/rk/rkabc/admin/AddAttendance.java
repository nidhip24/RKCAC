package com.rk.rkabc.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rk.rkabc.AppController;
import com.rk.rkabc.Home;
import com.rk.rkabc.R;
import com.rk.rkabc.URL;
import com.rk.rkabc.UserData;

import java.util.HashMap;
import java.util.Map;

public class AddAttendance extends AppCompatActivity {

    EditText name;
    Button gene;
    TextView pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        name = findViewById(R.id.mname);
        gene = findViewById(R.id.mgenerate);
        pas = findViewById(R.id.mpass);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Add Attendance");

        gene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("")){
                    makerequest();
                }else{
                    Toast.makeText(getApplicationContext(),"Field is empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void makerequest(){
        UserData uu = new UserData();
        final String username = uu.getUsername(getApplicationContext());
        URL u = new URL();
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"addatt.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("Error")){
                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    String p = "Password : "+response.trim();
                    pas.setText(p);
                }else{
                    Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error"+ error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", username);
                params.put("name", name.getText().toString());
                return params;

            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }
}

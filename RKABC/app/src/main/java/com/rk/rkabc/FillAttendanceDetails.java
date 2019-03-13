package com.rk.rkabc;

import android.app.ProgressDialog;
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
import com.rk.rkabc.admin.AdminHome;

import java.util.HashMap;
import java.util.Map;

public class FillAttendanceDetails extends AppCompatActivity {

    TextView grno,title;
    EditText pass;
    Button pro;

    String name,id;

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_attendance_details);

        grno = findViewById(R.id.mgrno);
        title = findViewById(R.id.mname);
        pass = findViewById(R.id.mpass);
        pro = findViewById(R.id.mpro);

        Intent i = getIntent();
        name = i.getStringExtra("name");
        id = i.getStringExtra("id");

        title.setText(name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Gathering details...");
        pDialog.setCancelable(false);

        pro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!pass.getText().toString().equals("")){
                    add();
                }else{
                    Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        getGR();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private  void add(){
        URL u = new URL();
        UserData userf = new UserData();
        final String username = userf.getUsername(getApplicationContext());
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"attendance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), Home.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Incorrect password",Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                params.put("uid", username);
                params.put("pass", pass.getText().toString().trim());

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }

    private  void getGR(){
        URL u = new URL();
        UserData userf = new UserData();
        showpDialog();
        final String username = userf.getUsername(getApplicationContext());
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"getgrno.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(!response.trim().equals("error")){
                    //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                    grno.setText(response.trim());
                }else{
                    Toast.makeText(getApplicationContext(),"Try again after some time...",Toast.LENGTH_SHORT).show();
                }
                hidepDialog();
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
                params.put("uid", username);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(s);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}

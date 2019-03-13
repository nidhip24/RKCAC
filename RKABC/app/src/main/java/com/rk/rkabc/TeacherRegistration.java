package com.rk.rkabc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TeacherRegistration extends AppCompatActivity {

    EditText fname,lname,pno,email,password,cnfpass;

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_registration);

        fname = findViewById(R.id.mfname);
        lname = findViewById(R.id.mlastname);
        pno = findViewById(R.id.mphone);
        email = findViewById(R.id.memail);
        password = findViewById(R.id.mpass);
        cnfpass = findViewById(R.id.mcnfpass);

        Button reg = findViewById(R.id.mregister);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fname.getText().toString().equals("") && !lname.getText().toString().equals("") && !pno.getText().toString().equals("")  && !email.getText().toString().equals("") && !password.getText().toString().equals("") && !cnfpass.getText().toString().equals("") ){
                    //Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();

                    makeRequest();
                }else{
                    if (fname.getText().toString().equals("")) {
                        fname.setError("Enter First Name");
                    }if (lname.getText().toString().equals("")) {
                        lname.setError("Enter Last Name");
                    }if (pno.getText().toString().length() != 10) {
                        pno.setError("Enter 10 digit phone number");
                    }if (!isEmailValid(email.getText().toString())) {
                        email.setError("Enter valid emali id");
                    }if (password.getText().toString().equals("")) {
                        password.setError("Enter Password");
                    }if (!cnfpass.getText().toString().equals(password.getText().toString())) {
                        cnfpass.setError("Password does not match");
                    }

                    Toast.makeText(getApplicationContext(),"One of the field is empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        fname.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (fname.getText().toString().equals("")) {
                    fname.setError("Enter First Name");
                } else {
                    fname.setError(null);
                }
            }
        });

        lname.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (lname.getText().toString().equals("")) {
                    lname.setError("Enter Last Name");
                } else {
                    lname.setError(null);
                }
            }
        });

        pno.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (pno.getText().toString().length() != 10) {
                    pno.setError("Enter 10 digit phone number");
                } else {
                    pno.setError(null);
                }
            }
        });

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

        password.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (password.getText().toString().equals("")) {
                    password.setError("Enter Password");
                } else {
                    password.setError(null);
                }
            }
        });

        cnfpass.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (!cnfpass.getText().toString().equals(password.getText().toString())) {
                    cnfpass.setError("Password does not match");
                } else {
                    cnfpass.setError(null);
                }
            }
        });
    }

    public static boolean isEmailValid(String e){
        return (!TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches());
    }

    void makeRequest(){
        showpDialog();
        URL u = new URL();
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                Log.e("dammmmmmmm2",response);
                if(response.trim().equals("done")){
                    Intent intent=new Intent(getApplicationContext(),Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Email address is taken",Toast.LENGTH_SHORT).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", fname.getText().toString());
                params.put("lname",lname.getText().toString());
                params.put("pno", pno.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("type", "teacher");

                return params;

            }
        };
        s.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

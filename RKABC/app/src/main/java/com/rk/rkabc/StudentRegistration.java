package com.rk.rkabc;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class StudentRegistration extends AppCompatActivity {

    EditText fname,lname,std,div,dob,pno,section,grno,email,password;

    // Progress dialog
    private ProgressDialog pDialog;
    DatePickerDialog.OnDateSetListener date;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        fname = findViewById(R.id.mfname);
        lname = findViewById(R.id.mlname);
        std = findViewById(R.id.mstd);
        div = findViewById(R.id.mdiv);
        dob = findViewById(R.id.mdob);
        pno = findViewById(R.id.mphone);
        section = findViewById(R.id.msection);
        grno = findViewById(R.id.mtxt);
        email = findViewById(R.id.memail);
        password = findViewById(R.id.mpass);

        Button reg = findViewById(R.id.button);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(StudentRegistration.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fname.getText().toString().equals("") && !lname.getText().toString().equals("") && !std.getText().toString().equals("") && !div.getText().toString().equals("") && !dob.getText().toString().equals("") && !pno.getText().toString().equals("") && !section.getText().toString().equals("") && !grno.getText().toString().equals("") && !email.getText().toString().equals("") && !password.getText().toString().equals("") ){
                    //Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                    makeRequest();
                }else{
                    Toast.makeText(getApplicationContext(),"One of the field is empty",Toast.LENGTH_SHORT).show();
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

        std.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (std.getText().toString().equals("")) {
                    std.setError("Enter STD.");
                } else {
                    std.setError(null);
                }
            }
        });

        div.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (div.getText().toString().equals("")) {
                    div.setError("Enter DIV.");
                } else {
                    div.setError(null);
                }
            }
        });

        section.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (section.getText().toString().equals("")) {
                    section.setError("Enter Section");
                } else {
                    section.setError(null);
                }
            }
        });

        dob.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (dob.getText().toString().equals("")) {
                    dob.setError("Enter select DOB");
                } else {
                    dob.setError(null);
                }
            }
        });

        grno.addTextChangedListener(new TextWatcher()  {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s)  {
                if (grno.getText().toString().length() < 5) {
                    grno.setError("Enter GR number");
                } else {
                    grno.setError(null);
                }
            }
        });
    }

    public static boolean isEmailValid(String e){
        return (!TextUtils.isEmpty(e) && Patterns.EMAIL_ADDRESS.matcher(e).matches());
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dob.setText(sdf.format(myCalendar.getTime()));
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
                params.put("std",std.getText().toString());
                params.put("div",div.getText().toString());
                params.put("dob", dob.getText().toString());
                params.put("pno", pno.getText().toString());
                params.put("section", section.getText().toString());
                params.put("grno", grno.getText().toString());
                params.put("email", email.getText().toString());
                params.put("password", password.getText().toString());
                params.put("type", "student");

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

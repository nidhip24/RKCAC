package com.rk.rkabc.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rk.rkabc.AppController;
import com.rk.rkabc.R;
import com.rk.rkabc.StudentRegistration;
import com.rk.rkabc.URL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddEvent extends AppCompatActivity {

    Button addd;
    EditText name,info,dat;

    DatePickerDialog.OnDateSetListener date;

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        name = findViewById(R.id.eveName);
        info = findViewById(R.id.description);
        dat = findViewById(R.id.mdate);
        addd = findViewById(R.id.submit);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Add Event");

        addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().equals("") && !info.getText().toString().equals("") && !dat.getText().toString().equals("")){
                    makerequest();
                }else{
                    Toast.makeText(getApplicationContext(),"One of the field is empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

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

        dat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddEvent.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dat.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void makerequest(){

        StringRequest s = new StringRequest(Request.Method.POST, new URL().url+"event.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"Event details added",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),AdminHome.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Try again after some time...",Toast.LENGTH_SHORT).show();
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
                params.put("eventname", name.getText().toString().trim());
                params.put("info",info.getText().toString().trim());
                params.put("dat",dat.getText().toString().trim());
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }
}

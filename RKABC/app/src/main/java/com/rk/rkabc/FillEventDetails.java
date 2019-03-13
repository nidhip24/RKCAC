package com.rk.rkabc;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FillEventDetails extends AppCompatActivity {

    // Progress dialog
    private ProgressDialog pDialog;

    String en,dess,id,dat,userID;

    TextView ename,edate,clas,gr;
    Button btn;
    String yeno= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_event_details);

        ename = findViewById(R.id.meventname);
        edate = findViewById(R.id.meventdate);
        clas = findViewById(R.id.mclass);
        gr = findViewById(R.id.mgrno);
        btn = findViewById(R.id.btn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Fill Event Details");

        Intent i = getIntent();
        en = i.getStringExtra("ename");
        dess = i.getStringExtra("des");
        id = i.getStringExtra("id");
        dat = i.getStringExtra("dat");

        ename.setText("Event Name : " +en );
        edate.setText("Event Date : " + dat );

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!yeno.equals("")){
                    makeRequest2();
                }else{
                    Toast.makeText(getApplicationContext(),"Please select yes or no",Toast.LENGTH_SHORT).show();
                }
            }
        });

        makerequest();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.myes:
                if (checked)

                    yeno = "yes";
                    break;
            case R.id.mno:
                if (checked)

                    yeno = "no";
                    break;
        }
    }

    void makerequest(){
        showpDialog();

        UserData u = new UserData();
        String username = u.getUsername(getApplicationContext());
        JsonArrayRequest req = new JsonArrayRequest(new URL().url+"getUserDetails.php?uid="+username, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("EventFuture", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject com = (JSONObject) response.get(i);


                        userID= com.getString("id");
                        String grno = com.getString("grno").trim();
                        String classs = com.getString("class");


                        if (!grno.equals("-1") && !classs.equals("-1")){
                            //EventData t = new EventData(cname,information,id,date);

                            //eventList.add(t);
                            gr.setText("GR Number  : " + grno);
                            clas.setText("Class      : " + classs);
                        }else {
                            Toast.makeText(getApplicationContext(),"NO user found",Toast.LENGTH_SHORT).show();
                        }
                        //idarray.add(id+"");
                        //array.add(cname);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("EventFuture", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"NO Events available right now", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    void makeRequest2(){
        showpDialog();
        URL u = new URL();
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"participate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                Log.e("dammmmmmmm2",response);
                if(response.trim().equals("done")){
                    Intent intent=new Intent(getApplicationContext(),Home.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Try again after some time",Toast.LENGTH_SHORT).show();
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
                params.put("eid", id);
                params.put("uid",userID);
                params.put("attending", yeno);

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

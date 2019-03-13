package com.rk.rkabc.admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.rk.rkabc.AppController;
import com.rk.rkabc.FillAttendanceDetails;
import com.rk.rkabc.R;
import com.rk.rkabc.URL;
import com.rk.rkabc.UserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShowAttendanceList extends AppCompatActivity {

    ListView l;
    ArrayList<String> array = new ArrayList<String>();
    ArrayList<String> idarray = new ArrayList<String>();
    ArrayAdapter adapter;

    // Progress dialog
    private ProgressDialog pDialog;

    int mode = -1;

    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance_list);

        l = findViewById(R.id.listview);
        t = findViewById(R.id.noatt);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        l.setAdapter(adapter);

        Intent i = getIntent();
        mode = i.getIntExtra("mode",-1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Attendance List");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonArrayRequest();

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mode==1){
                    String lid = idarray.get(position);
                    array.remove(position);
                    adapter.notifyDataSetChanged();
                    deleteAtt(lid);
                }else if(mode==2){
//                    Intent i =new Intent(getApplicationContext(), FillAttendanceDetails.class);
//                    i.putExtra("id",idarray.get(position));
//                    i.putExtra("name",array.get(position));
                    //startActivity(i);
                    checkAtt(idarray.get(position),array.get(position));
                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void checkAtt(final String id, final String name){
        URL u = new URL();
        UserData userf = new UserData();
        final String username = userf.getUsername(getApplicationContext());

        StringRequest s = new StringRequest(Request.Method.POST, u.url+"checkattendance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("go")){
                    Intent i =new Intent(getApplicationContext(), FillAttendanceDetails.class);
                    i.putExtra("id",id);
                    i.putExtra("name",name);
                    startActivity(i);
                }else{
                    Toast.makeText(getApplicationContext(),"You have already filled the attendance form",Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }

    private  void deleteAtt(final String lid){
        URL u = new URL();
        StringRequest s = new StringRequest(Request.Method.POST, u.url+"deleteatt.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                if(response.trim().equals("done")){
                    Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(), AdminHome.class);
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
                params.put("id", lid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(s);
    }

    private void makeJsonArrayRequest() {

        showpDialog();

        String url = new URL().url;
        url+="getAttendanceList.php";

        JsonArrayRequest req = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("ShowAttendanceList", response.toString());

                try {
                    // Parsing json array response
                    // loop through each json object
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject com = (JSONObject) response.get(i);

                        String cname = com.getString("name");
                        int id =com.getInt("id");

                        if (cname.equals("-1") && id == -1){
                            t.setVisibility(View.VISIBLE);
                            break;
                        }else{
                            idarray.add(id+"");
                            array.add(cname);
                        }
                    }
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("ShowAttendanceList", "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"error volley", Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
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

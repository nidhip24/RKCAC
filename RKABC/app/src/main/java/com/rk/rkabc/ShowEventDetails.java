package com.rk.rkabc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowEventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_event_details);

        Intent i = getIntent();
        final String en = i.getStringExtra("ename");
        final String dess = i.getStringExtra("des");
        final String id = i.getStringExtra("id");
        final String dat = i.getStringExtra("dat");

        TextView n = findViewById(R.id.ename);
        TextView d = findViewById(R.id.info);
        TextView datt = findViewById(R.id.mdate);

        Button b = findViewById(R.id.button);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Event details");

        if(en!=null)
            n.setText(en);
        if(dess!=null)
            d.setText(dess);

        if (dat!=null)
            datt.setText("Event date is : "+ dat);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),FillEventDetails.class);
                i.putExtra("ename",en);
                i.putExtra("des",dess);
                i.putExtra("id",id);
                i.putExtra("dat",dat);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

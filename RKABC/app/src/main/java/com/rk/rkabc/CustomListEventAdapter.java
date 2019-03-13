package com.rk.rkabc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class CustomListEventAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<EventData> evnData;

    public CustomListEventAdapter(Activity activity, List<EventData> en) {
        this.activity = activity;
        this.evnData = en;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        //Log.d("CustomListAdapter",position+"");
        if(position%2==0){
            return 0;
        }
        return 1;
    }

    @Override
    public int getCount() {
        return evnData.size();
    }

    @Override
    public Object getItem(int location) {
        return evnData.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getting team data for the row
        final EventData m = evnData.get(position);
        int pos = getItemViewType(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null){
             //layout
            Log.d("CustomListAdapter"," not null\nnot null\n");
            convertView = inflater.inflate(R.layout.list_event, null);
        }
//
//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
//        NetworkImageView thumbNail = (NetworkImageView) convertView
//                .findViewById(R.id.thumbnail);
//
//
        TextView title =  convertView.findViewById(R.id.eventName);
        TextView des =  convertView.findViewById(R.id.eventDes);
        TextView dat =  convertView.findViewById(R.id.mdate);
        Button b = convertView.findViewById(R.id.participate);


//        ImageView img = (ImageView)  convertView.findViewById(R.id.thumbnail);
//
//        // title
        title.setText(m.getEname());
        des.setText(m.getEdes());
        dat.setText("Event is date : "+m.getDat());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity,ShowEventDetails.class);
                i.putExtra("ename",m.getEname());
                i.putExtra("des",m.getEdes());
                i.putExtra("id",m.getID());
                i.putExtra("dat",m.getDat());

                activity.startActivity(i);
            }
        });
//
//        // post
//        post.setText(m.getPost());
//
//        //image
//        thumbNail.setImageUrl(m.getImg(), imageLoader);

        return convertView;
    }

}

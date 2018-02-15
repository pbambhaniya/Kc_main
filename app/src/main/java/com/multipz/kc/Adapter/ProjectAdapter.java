package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.ProjectModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 01-08-2017.
 */

public class ProjectAdapter extends BaseAdapter {

    Context context;

    ArrayList<ProjectModel> list = new ArrayList<ProjectModel>();
    ProjectModel data;

    public ProjectAdapter(Context context, ArrayList<ProjectModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        data = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View layoutview = inflater.inflate(R.layout.project_item, parent, false);


        TextView txt_sidesortname = (TextView) layoutview.findViewById(R.id.txt_sidesortname);
        txt_sidesortname.setTypeface(Application.fontOxygenRegular);
        TextView txt_side_now = (TextView) layoutview.findViewById(R.id.txt_side_now);
        txt_side_now.setTypeface(Application.fontOxygenRegular);
        TextView txt_location = (TextView) layoutview.findViewById(R.id.txt_location);
        txt_location.setTypeface(Application.fontOxygenRegular);




        ImageView img_check = (ImageView) layoutview.findViewById(R.id.img_check);

        if (data.getIs_complete().toLowerCase().contentEquals("y")) {
            img_check.setImageResource(R.drawable.red);
        } else {
            img_check.setImageResource(R.drawable.green);
        }

        txt_sidesortname.setText(data.getSide_sort_name());
        txt_side_now.setText(data.getName_of_work());
        txt_location.setText(data.getLocation());


        return layoutview;
    }


}

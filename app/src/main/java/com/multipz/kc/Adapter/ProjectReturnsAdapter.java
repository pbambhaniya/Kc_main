package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.ProjectModel;
import com.multipz.kc.Model.ProjectReturnsModel;
import com.multipz.kc.Project.ProjectReturns;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 11-09-2017.
 */

public class ProjectReturnsAdapter extends BaseAdapter {
    Context context;

    ArrayList<ProjectReturnsModel> list = new ArrayList<ProjectReturnsModel>();
    ProjectReturnsModel data;

    public ProjectReturnsAdapter(Context context, ArrayList<ProjectReturnsModel> list) {
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

        View layoutview = inflater.inflate(R.layout.project_return_item, parent, false);


        TextView txt_sidesortname = (TextView) layoutview.findViewById(R.id.txt_sidesortname);
        txt_sidesortname.setTypeface(Application.fontOxygenRegular);
        TextView txt_sd = (TextView) layoutview.findViewById(R.id.txt_sd);
        txt_sd.setTypeface(Application.fontOxygenRegular);
        TextView txt_fmg = (TextView) layoutview.findViewById(R.id.txt_fmg);
        txt_fmg.setTypeface(Application.fontOxygenRegular);
        TextView txt_tds = (TextView) layoutview.findViewById(R.id.txt_tds);
        txt_tds.setTypeface(Application.fontOxygenRegular);


        ImageView img_check = (ImageView) layoutview.findViewById(R.id.img_check);

//        if (data.getIs_complete().toLowerCase().contentEquals("y")) {
//            img_check.setImageResource(R.drawable.red);
//        } else {
//            img_check.setImageResource(R.drawable.green);
//        }

        txt_sidesortname.setText(data.getSide_sort_name());
        txt_fmg.setText(data.getFmg());
        txt_sd.setText(data.getSd());
        txt_tds.setText(data.getTds());


        return layoutview;
    }


}

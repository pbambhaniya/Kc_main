package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.WorkTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 08-08-2017.
 */

public class WorkTypeAdapter extends BaseAdapter {

    Context context;
    ArrayList<WorkTypeModel> list = new ArrayList<WorkTypeModel>();
    WorkTypeModel data;

    public WorkTypeAdapter(Context context, ArrayList<WorkTypeModel> list) {
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

        View layoutview = inflater.inflate(R.layout.work_type_item, parent, false);


        TextView work_type = (TextView) layoutview.findViewById(R.id.work_type);
        work_type.setText(data.getWork_type());
        work_type.setTypeface(Application.fontOxygenRegular);


        return layoutview;
    }

}

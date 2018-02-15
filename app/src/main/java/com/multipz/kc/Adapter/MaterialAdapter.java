package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.multipz.kc.Model.MaterialModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 02-08-2017.
 */

public class MaterialAdapter extends BaseAdapter {
    Context context;
    ArrayList<MaterialModel> list = new ArrayList<MaterialModel>();
    MaterialModel data;

    public MaterialAdapter(Context context, ArrayList<MaterialModel> list) {
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

        View layoutview = inflater.inflate(R.layout.assets_item, parent, false);

        TextView txt_name = (TextView) layoutview.findViewById(R.id.assets_name);


        txt_name.setTypeface(Application.fontOxygenRegular);


        txt_name.setText(data.getMaterial_type());

        return layoutview;
    }
}

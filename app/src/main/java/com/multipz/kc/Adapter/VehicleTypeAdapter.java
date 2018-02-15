package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.multipz.kc.Model.VehicleTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 04-08-2017.
 */

public class VehicleTypeAdapter extends BaseAdapter {
    Context context;
    ArrayList<VehicleTypeModel> list = new ArrayList<VehicleTypeModel>();
    VehicleTypeModel data;

    public VehicleTypeAdapter(Context context, ArrayList<VehicleTypeModel> list) {
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

        View layoutview = inflater.inflate(R.layout.vehicle_type_item, parent, false);

        TextView txt_name = (TextView) layoutview.findViewById(R.id.vehicle_name);
        txt_name.setTypeface(Application.fontOxygenRegular);
        txt_name.setText(data.getVehicle_type());

        return layoutview;
    }
}

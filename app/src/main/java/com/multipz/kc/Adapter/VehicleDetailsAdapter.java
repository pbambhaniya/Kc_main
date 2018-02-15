package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 03-08-2017.
 */

public class VehicleDetailsAdapter extends BaseAdapter {
    Context context;
    ArrayList<VehicleModel> list = new ArrayList<VehicleModel>();
    VehicleModel data;

    public VehicleDetailsAdapter(Context context, ArrayList<VehicleModel> list) {
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

        View layoutview = inflater.inflate(R.layout.vehicle_item, parent, false);

        TextView txt_vehicle_no = (TextView) layoutview.findViewById(R.id.vehicle_no);
        TextView txt_vehicle_type = (TextView) layoutview.findViewById(R.id.vehicle_type);

        txt_vehicle_no.setTypeface(Application.fontOxygenRegular);
        txt_vehicle_type.setTypeface(Application.fontOxygenRegular);

        txt_vehicle_no.setText(data.getVehicle_no());
        txt_vehicle_type.setText(data.getVehicle_type());

        return layoutview;
    }
}

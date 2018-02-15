package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleExReportModel;
import com.multipz.kc.Model.VehicleTransportReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class VehicleTransportReportAdapter extends BaseAdapter {

    Context context;

    ArrayList<VehicleTransportReportModel> list = new ArrayList<VehicleTransportReportModel>();
    VehicleTransportReportModel data;
    LayoutInflater inflater;


    public VehicleTransportReportAdapter(Context context, ArrayList<VehicleTransportReportModel> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        View rowView = convertView;
        Holder holder;

        if (rowView == null) {
            holder = new Holder();
            rowView = inflater.inflate(R.layout.vehicle_transport_report_item, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txtusername = rowView.findViewById(R.id.user_name);
            holder.txt_vehicle_no = rowView.findViewById(R.id.vehicle_no);
            holder.material_name = rowView.findViewById(R.id.material_name);
            holder.txt_load = rowView.findViewById(R.id.txt_load);
            holder.load_com_name = rowView.findViewById(R.id.load_com_name);
            holder.load_amount=rowView.findViewById(R.id.load_amount);
            holder.empty_to = rowView.findViewById(R.id.empty_to);
            holder.empty_com_name = rowView.findViewById(R.id.empty_com_name);
            holder.empty_amount = rowView.findViewById(R.id.empty_amount);
            holder.date = rowView.findViewById(R.id.date);


            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txtusername.setTypeface(Application.fontOxygenRegular);
            holder.txt_vehicle_no.setTypeface(Application.fontOxygenRegular);
            holder.material_name.setTypeface(Application.fontoxegenregular);
            holder.txt_load.setTypeface(Application.fontoxegenregular);
            holder.load_com_name.setTypeface(Application.fontoxegenregular);
            holder.load_amount.setTypeface(Application.fontOxygenRegular);
            holder.empty_to.setTypeface(Application.fontOxygenRegular);
            holder.empty_com_name.setTypeface(Application.fontOxygenRegular);
            holder.empty_amount.setTypeface(Application.fontOxygenRegular);
            holder.date.setTypeface(Application.fontOxygenRegular);


            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txtusername.setText(data.getUserName());
        holder.txt_vehicle_no.setText(data.getVehicle_no());
        holder.material_name.setText(data.getMaterial_type());
        holder.txt_load.setText(data.getLoad_to());
        holder.load_com_name.setText(data.getLoadCompany());
        holder.load_amount.setText(data.getLoad_amount());
        holder.empty_to.setText(data.getEmpty_to());
        holder.empty_com_name.setText(data.getEmptyCompany());
        holder.empty_amount.setText(data.getEmpty_amount());
        holder.date.setText(data.getCreated_date());

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txtusername, txt_vehicle_no,    material_name, txt_load, load_com_name,load_amount,empty_to,empty_com_name,empty_amount,date;
}
}

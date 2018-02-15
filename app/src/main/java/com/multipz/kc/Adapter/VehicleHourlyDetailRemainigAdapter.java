package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.VehicleRemainDetailReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.Constant_method;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class VehicleHourlyDetailRemainigAdapter extends BaseAdapter {

    Context context;

    ArrayList<VehicleRemainDetailReportModel> list = new ArrayList<VehicleRemainDetailReportModel>();
    VehicleRemainDetailReportModel data;
    LayoutInflater inflater;


    public VehicleHourlyDetailRemainigAdapter(Context context, ArrayList<VehicleRemainDetailReportModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_vehicle_remain_detail_item, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_company_name = rowView.findViewById(R.id.txt_company_name);
            holder.txt_user_name_rep = rowView.findViewById(R.id.txt_user_name_rep);
            holder.txt_vehicle_no_rep = rowView.findViewById(R.id.txt_vehicle_no_rep);
            holder.txt_vehicle_type_rep = rowView.findViewById(R.id.txt_vehicle_type_rep);
            holder.txt_start_time_rep = rowView.findViewById(R.id.txt_start_time_rep);
            holder.txt_end_time_rep = rowView.findViewById(R.id.txt_end_time_rep);
            holder.txt_date_rep = rowView.findViewById(R.id.txt_date_rep);


            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_company_name.setText(data.getCompanyName());
        holder.txt_user_name_rep.setText(data.getUserName());
        holder.txt_vehicle_no_rep.setText(data.getVehicle_no());
        holder.txt_vehicle_type_rep.setText(data.getVehicle_type());
        holder.txt_start_time_rep.setText(data.getStart_time());
        holder.txt_end_time_rep.setText(data.getEnd_time());
        holder.txt_date_rep.setText(data.getCreated_date());

        holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
        holder.txt_company_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_user_name_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_vehicle_no_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_vehicle_type_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_start_time_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_end_time_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_date_rep.setTypeface(Application.fontOxygenRegular);

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_company_name, txt_user_name_rep, txt_vehicle_no_rep, txt_vehicle_type_rep, txt_start_time_rep, txt_end_time_rep, txt_date_rep;
    }
}

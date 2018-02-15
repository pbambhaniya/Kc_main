package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.StaffVehicleExpReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class StaffExpVeicleReportAdapter extends BaseAdapter {

    Context context;

    ArrayList<StaffVehicleExpReportModel> list = new ArrayList<StaffVehicleExpReportModel>();
    StaffVehicleExpReportModel data;
    LayoutInflater inflater;


    public StaffExpVeicleReportAdapter(Context context, ArrayList<StaffVehicleExpReportModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_staff_exp_vehicle_rep, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_user_name = rowView.findViewById(R.id.txt_user_name);
            holder.txt_vehicle_no = rowView.findViewById(R.id.txt_vehicle_no);
            holder.txt_gen_exp_detail = rowView.findViewById(R.id.txt_gen_exp_detail);
            holder.txt_gen_exp_date = rowView.findViewById(R.id.txt_gen_exp_date);
            holder.txt_amount = rowView.findViewById(R.id.txt_amount);


            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_user_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_vehicle_no.setTypeface(Application.fontOxygenRegular);
            holder.txt_gen_exp_detail.setTypeface(Application.fontOxygenRegular);
            holder.txt_gen_exp_date.setTypeface(Application.fontOxygenRegular);
            holder.txt_amount.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_user_name.setText(data.getName());
        holder.txt_vehicle_no.setText(data.getVehicle_no());
        holder.txt_gen_exp_detail.setText(data.getDetail());
        holder.txt_gen_exp_date.setText(data.getCreated_date());
        holder.txt_amount.setText(data.getAmt());

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_user_name, txt_vehicle_no, txt_gen_exp_detail, txt_gen_exp_date, txt_amount;
    }
}

package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.StaffProjectReportModel;
import com.multipz.kc.Model.VehicleExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class StaffProjectReportAdapter extends BaseAdapter {

    Context context;

    ArrayList<StaffProjectReportModel> list = new ArrayList<StaffProjectReportModel>();
    StaffProjectReportModel data;
    LayoutInflater inflater;


    public StaffProjectReportAdapter(Context context, ArrayList<StaffProjectReportModel> list) {
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
        StaffProjectReportAdapter.Holder holder;

        if (rowView == null) {
            holder = new StaffProjectReportAdapter.Holder();
            rowView = inflater.inflate(R.layout.staff_project_report_item, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txtusername = rowView.findViewById(R.id.user_name);
            holder.txtpro_name = rowView.findViewById(R.id.pro_name);
            holder.txt_ex_detail = rowView.findViewById(R.id.ex_detail);
            holder.txt_ex_date = rowView.findViewById(R.id.ex_date);
            holder.txtAmount = rowView.findViewById(R.id.amount);


            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txtusername.setTypeface(Application.fontOxygenRegular);
            holder.txtpro_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_ex_detail.setTypeface(Application.fontOxygenRegular);
            holder.txt_ex_date.setTypeface(Application.fontOxygenRegular);

            holder.txtAmount.setTypeface(Application.fontOxygenRegular);


            rowView.setTag(holder);
        }

        holder = (StaffProjectReportAdapter.Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txtusername.setText(data.getUserName());
        holder.txtpro_name.setText(data.getSide_sort_name());
        holder.txt_ex_detail.setText(data.getDetail());
        holder.txt_ex_date.setText(data.getCreated_date());
        holder.txtAmount.setText(data.getAmount());

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txtusername, txtpro_name, txt_ex_detail, txt_ex_date, txtAmount, txt1, txt2;
    }
}

package com.multipz.kc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.SiteWisePayment;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.StaffProjectReportModel;
import com.multipz.kc.Model.StaffSiteExpenseReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 05-10-2017.
 */

public class StaffSiteExpenseReportAdapter extends BaseAdapter {

    Context context;

    ArrayList<StaffSiteExpenseReportModel> list = new ArrayList<StaffSiteExpenseReportModel>();
    StaffSiteExpenseReportModel data;
    LayoutInflater inflater;


    public StaffSiteExpenseReportAdapter(Context context, ArrayList<StaffSiteExpenseReportModel> list) {
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
        StaffSiteExpenseReportAdapter.Holder holder;

        if (rowView == null) {
            holder = new StaffSiteExpenseReportAdapter.Holder();
            rowView = inflater.inflate(R.layout.staff_site_exp_report, parent, false);

            holder.serial_no = rowView.findViewById(R.id.serial_no);
            holder.txt_user_name = rowView.findViewById(R.id.txt_user_name);
            holder.txt_pro_name = rowView.findViewById(R.id.txt_pro_name);
            holder.txt_exp_detail = rowView.findViewById(R.id.txt_exp_detail);
            holder.txt_exp_date = rowView.findViewById(R.id.txt_exp_date);
            holder.txt_amount = rowView.findViewById(R.id.txt_amount);


            holder.serial_no.setTypeface(Application.fontOxygenRegular);
            holder.txt_user_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_pro_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_exp_detail.setTypeface(Application.fontOxygenRegular);
            holder.txt_exp_date.setTypeface(Application.fontOxygenRegular);
            holder.txt_amount.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (StaffSiteExpenseReportAdapter.Holder) rowView.getTag();
        data = list.get(position);
        holder.serial_no.setText(position + 1 + "");
        holder.txt_user_name.setText(data.getName());
        holder.txt_exp_detail.setText(data.getDetail());
        holder.txt_exp_date.setText(data.getCreated_date());
        holder.txt_amount.setText(data.getAmt());
        holder.txt_pro_name.setText(data.getSide_sort_name());
        return rowView;
    }


    class Holder {
        TextView serial_no, txt_user_name, txt_pro_name, txt_exp_detail, txt_exp_date, txt_amount;
    }
}

package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.Model.AttendanceDateModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class AttendanceDataReportAdapter extends BaseAdapter {

    Context context;

    ArrayList<AttendanceDateModel> list = new ArrayList<AttendanceDateModel>();
    AttendanceDateModel data;
    LayoutInflater inflater;


    public AttendanceDataReportAdapter(Context context, ArrayList<AttendanceDateModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_attendance_report, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_employee_name = rowView.findViewById(R.id.txt_employee_name);
            holder.txt_basetype = rowView.findViewById(R.id.txt_basetype);

            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_employee_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_basetype.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_employee_name.setText(data.getName());
        if (data.getSalary_based_type().matches("F")) {
            holder.txt_basetype.setText("Fix");
        } else if (data.getSalary_based_type().matches("D")) {
            holder.txt_basetype.setText("Daily Base");
        }

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_employee_name, txt_attendance, txt_basetype;
    }
}

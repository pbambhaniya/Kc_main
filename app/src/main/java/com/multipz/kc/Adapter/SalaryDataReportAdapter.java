package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.Model.SiteExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.Report.SalaryStatementActivity;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class SalaryDataReportAdapter extends BaseAdapter {

    Context context;
    SalaryStatementActivity activity;
    ArrayList<SalaryDataModel> list = new ArrayList<SalaryDataModel>();
    SalaryDataModel data;
    LayoutInflater inflater;
    private int pos;


    public SalaryDataReportAdapter(SalaryStatementActivity context, ArrayList<SalaryDataModel> list) {
        this.context = context;
        this.list = list;
        this.activity = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;
        Holder holder;

        if (rowView == null) {
            holder = new Holder();
            rowView = inflater.inflate(R.layout.list_item_salary_report, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_employee_name = rowView.findViewById(R.id.txt_employee_name);
            holder.txt_attendance = rowView.findViewById(R.id.txt_attendance);
            holder.txt_actual_salary = rowView.findViewById(R.id.txt_actual_salary);
            holder.txt_pay_salary = rowView.findViewById(R.id.txt_pay_salary);
            holder.txt_actual_pay_salary = rowView.findViewById(R.id.txt_actual_pay_salary);
            holder.txt_pay_status = rowView.findViewById(R.id.txt_pay_status);
            holder.btn_view_salary = rowView.findViewById(R.id.btn_view_salary);

            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_employee_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_attendance.setTypeface(Application.fontOxygenRegular);
            holder.txt_actual_salary.setTypeface(Application.fontOxygenRegular);
            holder.txt_pay_salary.setTypeface(Application.fontOxygenRegular);
            holder.txt_actual_pay_salary.setTypeface(Application.fontOxygenRegular);
            holder.txt_pay_status.setTypeface(Application.fontOxygenRegular);

            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_employee_name.setText(data.getName());
        holder.txt_attendance.setText(data.getDay());
        holder.txt_actual_salary.setText(data.getActual_salary());
        holder.txt_pay_salary.setText(data.getActual_pay_salary());
        holder.txt_actual_pay_salary.setText(data.getActual_pay_salary());
        holder.txt_pay_status.setText(data.getPay_status());

        holder.btn_view_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                data = list.get(pos);
                String uid = data.getUser_id();
                activity.onDetailClickFromAdapter(uid);
            }
        });


        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_employee_name, txt_attendance, txt_actual_salary, txt_pay_salary, txt_actual_pay_salary, txt_debit_credit, txt_pay_status;
        Button btn_view_salary;
    }
}

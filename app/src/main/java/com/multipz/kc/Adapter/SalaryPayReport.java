package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.Model.SalaryPayModel;
import com.multipz.kc.R;
import com.multipz.kc.Report.SalaryStatementActivity;
import com.multipz.kc.Staff.SalaryPayAcivity;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class SalaryPayReport extends BaseAdapter {

    Context context;
    SalaryPayAcivity activity;
    ArrayList<SalaryPayModel> list = new ArrayList<SalaryPayModel>();
    SalaryPayModel data;
    LayoutInflater inflater;
    private int pos;


    public SalaryPayReport(SalaryPayAcivity context, ArrayList<SalaryPayModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_salary_pay_activity_view, parent, false);

            holder.serial_no = rowView.findViewById(R.id.serial_no);
            holder.txt_user_name_salary_pay = rowView.findViewById(R.id.txt_user_name_salary_pay);
            holder.txt_salary_base_type_pay = rowView.findViewById(R.id.txt_salary_base_type_pay);

            holder.btn_view_salary = rowView.findViewById(R.id.btn_view_salary);

            holder.serial_no.setTypeface(Application.fontOxygenRegular);
            holder.txt_user_name_salary_pay.setTypeface(Application.fontOxygenRegular);
            holder.txt_salary_base_type_pay.setTypeface(Application.fontOxygenRegular);


            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.serial_no.setText(position + 1 + "");
        holder.txt_user_name_salary_pay.setText(data.getName());
        holder.txt_salary_base_type_pay.setText(data.getSalary_based_type());

        holder.btn_view_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                data = list.get(pos);
                String uid = data.getUser_id();
                String salarybasetype = data.getSalary_based_type();
                activity.onDetailClickFromAdapter(uid, salarybasetype);
            }
        });


        return rowView;
    }


    class Holder {
        TextView serial_no, txt_user_name_salary_pay, txt_salary_base_type_pay;
        Button btn_view_salary;
    }
}

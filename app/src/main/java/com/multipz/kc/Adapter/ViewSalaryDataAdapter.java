package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.multipz.kc.Model.PartyWisePayModel;
import com.multipz.kc.Model.ViewSalaryDataModel;
import com.multipz.kc.R;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class ViewSalaryDataAdapter extends BaseAdapter {

    Context context;

    ArrayList<ViewSalaryDataModel> list = new ArrayList<ViewSalaryDataModel>();
    ViewSalaryDataModel data;
    LayoutInflater inflater;


    public ViewSalaryDataAdapter(Context context, ArrayList<ViewSalaryDataModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_salary_data, parent, false);

            holder.serial_no = rowView.findViewById(R.id.serial_no);
            holder.txt_Amount_salary_view = rowView.findViewById(R.id.txt_Amount_salary_view);
            holder.txt_payement_type = rowView.findViewById(R.id.txt_payement_type);
            holder.txt_detail = rowView.findViewById(R.id.txt_detail);
            holder.txt_date = rowView.findViewById(R.id.txt_date);


          /*  holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_employee_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_attendance.setTypeface(Application.fontOxygenRegular);
            holder.txt_actual_salary.setTypeface(Application.fontOxygenRegular);
            holder.txt_pay_salary.setTypeface(Application.fontOxygenRegular);
            holder.txt_actual_pay_salary.setTypeface(Application.fontOxygenRegular);
            holder.txt_pay_status.setTypeface(Application.fontOxygenRegular);*/


            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.serial_no.setText(position + 1 + "");
        holder.txt_Amount_salary_view.setText(data.getAmount());
        holder.txt_payement_type.setText(data.getPayment_type());
        holder.txt_detail.setText(data.getDetail());
        holder.txt_date.setText(data.getDate());


        return rowView;
    }


    class Holder {
        TextView serial_no, txt_Amount_salary_view, txt_payement_type, txt_detail, txt_date;

    }
}

package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.PartyWisePayModel;
import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class PartyWisePaymeentAdapter extends BaseAdapter {

    Context context;

    ArrayList<PartyWisePayModel> list = new ArrayList<PartyWisePayModel>();
    PartyWisePayModel data;
    LayoutInflater inflater;


    public PartyWisePaymeentAdapter(Context context, ArrayList<PartyWisePayModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_party_wise_pay, parent, false);

            holder.txt_kc_name = rowView.findViewById(R.id.txt_kc_name);
            holder.txt_kc_amount = rowView.findViewById(R.id.txt_kc_amount);
            holder.txt_kc_total_pay_amount = rowView.findViewById(R.id.txt_kc_total_pay_amount);
            holder.txt_total_debit_credit = rowView.findViewById(R.id.txt_total_debit_credit);
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

        holder.txt_kc_name.setText(data.getName());
        holder.txt_kc_amount.setText(data.getTotal_amount());
        holder.txt_kc_total_pay_amount.setText(data.getTotal_pay());
        int total = Integer.parseInt(data.getTotal_pay()) - Integer.parseInt(data.getTotal_amount());
        if (total > 0) {
            holder.txt_total_debit_credit.setText(total + "/- Credit");
        } else {
            holder.txt_total_debit_credit.setText(total + "/- Debit");
        }


        return rowView;
    }


    class Holder {
        TextView txt_kc_name, txt_kc_amount, txt_kc_total_pay_amount, txt_total_debit_credit;
    }
}

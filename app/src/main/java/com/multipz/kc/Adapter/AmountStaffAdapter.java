package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Adapter.StaffAdapter;
import com.multipz.kc.Model.AmountStaffModel;
import com.multipz.kc.Model.StaffModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 19-09-2017.
 */

public class AmountStaffAdapter extends BaseAdapter {
    Context context;
    ArrayList<AmountStaffModel> list = new ArrayList<AmountStaffModel>();
    AmountStaffModel data;

    public AmountStaffAdapter(Context context, ArrayList<AmountStaffModel> list) {
        this.context = context;
        this.list = list;
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
        data = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View layoutview = inflater.inflate(R.layout.amount_staff_item, parent, false);
        TextView txt_user_name = (TextView) layoutview.findViewById(R.id.txt_user_name);
        txt_user_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_work_type = (TextView) layoutview.findViewById(R.id.txt_work_type);
        txt_work_type.setTypeface(Application.fontOxygenRegular);
        TextView txt_sort_name = (TextView) layoutview.findViewById(R.id.txt_sort_name);
        txt_sort_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_bank_name = (TextView) layoutview.findViewById(R.id.txt_bank_name);
        txt_bank_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_pay_type = (TextView) layoutview.findViewById(R.id.txt_pay_type);
        txt_pay_type.setTypeface(Application.fontOxygenRegular);

        txt_user_name.setText(data.getUserName());
        txt_work_type.setText(data.getWork_type());
        txt_sort_name.setText(data.getSide_sort_name());
        txt_bank_name.setText(data.getBankName());
        txt_pay_type.setText(data.getPayment_type());


        if (data.getBankName().contentEquals("null")) {
            txt_bank_name.setText("---");
        }
        return layoutview;

    }
}

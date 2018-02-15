package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Company.CompanyPay;
import com.multipz.kc.Model.CompanyModel;
import com.multipz.kc.Model.CompanyPayModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 13-09-2017.
 */

public class CompanyPayAdapter extends BaseAdapter {

    Context context;
    ArrayList<CompanyPayModel> list = new ArrayList<CompanyPayModel>();
    CompanyPayModel data;

    public CompanyPayAdapter(Context context, ArrayList<CompanyPayModel> list) {
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

        View layoutview = inflater.inflate(R.layout.company_pay_item, parent, false);

        TextView txt_bank_name = (TextView) layoutview.findViewById(R.id.txt_bank_name);
        txt_bank_name.setTypeface(Application.fontoxegenregular);

        TextView txt_com_name = (TextView) layoutview.findViewById(R.id.txt_com_name);
        txt_com_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_pay_type = (TextView) layoutview.findViewById(R.id.txt_pay_type);
        txt_pay_type.setTypeface(Application.fontOxygenRegular);
        TextView txt_amount = (TextView) layoutview.findViewById(R.id.txt_amount);
        txt_amount.setTypeface(Application.fontOxygenRegular);
        TextView txt_pay_info = (TextView) layoutview.findViewById(R.id.txt_pay_info);
        txt_pay_info.setTypeface(Application.fontOxygenRegular);

        txt_com_name.setText(data.getCompanyName());
        txt_bank_name.setText(data.getBank_name());
        txt_amount.setText(data.getAmount());
        txt_pay_info.setText(data.getPayment_info());
        txt_pay_type.setText(data.getPayment_type());

        return layoutview;
    }


}

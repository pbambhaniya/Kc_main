package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.GenExpReport;
import com.multipz.kc.Model.SiteWisePayment;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class GenExpAdapter extends BaseAdapter {

    Context context;

    ArrayList<GenExpReport> list = new ArrayList<GenExpReport>();
    GenExpReport data;
    LayoutInflater inflater;


    public GenExpAdapter(Context context, ArrayList<GenExpReport> list) {
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
            rowView = inflater.inflate(R.layout.list_item_kc_gen_exp_report, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_detail = rowView.findViewById(R.id.txt_detail);
            holder.txt_amount = rowView.findViewById(R.id.txt_amount);


            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_detail.setTypeface(Application.fontOxygenRegular);
            holder.txt_amount.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_detail.setText(data.getDetail());
        holder.txt_amount.setText(data.getAmount());

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_detail, txt_amount;
    }
}

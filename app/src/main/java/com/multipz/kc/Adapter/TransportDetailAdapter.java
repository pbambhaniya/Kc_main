package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.TransportDetailModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class TransportDetailAdapter extends BaseAdapter {

    Context context;

    ArrayList<TransportDetailModel> list = new ArrayList<TransportDetailModel>();
    TransportDetailModel data;
    LayoutInflater inflater;


    public TransportDetailAdapter(Context context, ArrayList<TransportDetailModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_tranport_detail, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_location = rowView.findViewById(R.id.txt_location);
            holder.txt_pay_amount = rowView.findViewById(R.id.txt_pay_amount);
            holder.txt_pay_back_amt = rowView.findViewById(R.id.txt_pay_back_amt);
            holder.txt_date = rowView.findViewById(R.id.txt_date);

            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_location.setTypeface(Application.fontOxygenRegular);
            holder.txt_pay_amount.setTypeface(Application.fontOxygenRegular);
            holder.txt_pay_back_amt.setTypeface(Application.fontOxygenRegular);
            holder.txt_date.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_location.setText(data.getLocation());
        holder.txt_pay_amount.setText(data.getPay_amount());
        holder.txt_pay_back_amt.setText(data.getRevert_amount());
        holder.txt_date.setText(data.getCreated_date());

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_location, txt_pay_amount, txt_pay_back_amt, txt_date;
    }
}

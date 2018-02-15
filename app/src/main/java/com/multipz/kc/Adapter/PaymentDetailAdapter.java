package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.PaymentDetailModel;
import com.multipz.kc.Model.TransportDetailModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class PaymentDetailAdapter extends BaseAdapter {

    Context context;

    ArrayList<PaymentDetailModel> list = new ArrayList<PaymentDetailModel>();
    PaymentDetailModel data;
    LayoutInflater inflater;


    public PaymentDetailAdapter(Context context, ArrayList<PaymentDetailModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_payment_detail, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_type = rowView.findViewById(R.id.txt_type);
            holder.txt_payment_type = rowView.findViewById(R.id.txt_payment_type);
            holder.txt_amt = rowView.findViewById(R.id.txt_amt);
            holder.txt_chequeno = rowView.findViewById(R.id.txt_chequeno);
            holder.txt_date = rowView.findViewById(R.id.txt_date);


            holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
            holder.txt_type.setTypeface(Application.fontOxygenRegular);
            holder.txt_payment_type.setTypeface(Application.fontOxygenRegular);
            holder.txt_amt.setTypeface(Application.fontOxygenRegular);
            holder.txt_chequeno.setTypeface(Application.fontOxygenRegular);
            holder.txt_date.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_type.setText(data.getType());
        holder.txt_payment_type.setText(data.getPayment_type());
        holder.txt_amt.setText(data.getAmount());
        holder.txt_chequeno.setText(data.getCheque_no());
        holder.txt_date.setText(data.getCreate_date());
        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_type, txt_payment_type, txt_amt, txt_chequeno, txt_date;
    }
}

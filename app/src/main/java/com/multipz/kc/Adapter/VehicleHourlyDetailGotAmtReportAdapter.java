package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleHourlyGotAmtDetailModel;
import com.multipz.kc.Model.VehicleRemainDetailReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 21-09-2017.
 */

public class VehicleHourlyDetailGotAmtReportAdapter extends BaseAdapter {

    Context context;

    ArrayList<VehicleHourlyGotAmtDetailModel> list = new ArrayList<VehicleHourlyGotAmtDetailModel>();
    VehicleHourlyGotAmtDetailModel data;
    LayoutInflater inflater;


    public VehicleHourlyDetailGotAmtReportAdapter(Context context, ArrayList<VehicleHourlyGotAmtDetailModel> list) {
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
            rowView = inflater.inflate(R.layout.list_item_vehicle_got_amt_detail_item, parent, false);

            holder.txtSrNo = rowView.findViewById(R.id.serial_no);
            holder.txt_company_name = rowView.findViewById(R.id.txt_company_name);
            holder.txt_bank_name_rep = rowView.findViewById(R.id.txt_bank_name_rep);
            holder.txt_amt_rep = rowView.findViewById(R.id.txt_amt_rep);
            holder.txt_payment_type_rep = rowView.findViewById(R.id.txt_payment_type_rep);
            holder.txt_payment_info_rep = rowView.findViewById(R.id.txt_payment_info_rep);
            holder.txt_detail_rep = rowView.findViewById(R.id.txt_detail_rep);
            holder.txt_date_rep = rowView.findViewById(R.id.txt_date_rep);

            rowView.setTag(holder);
        }

        holder = (Holder) rowView.getTag();

        data = list.get(position);
        holder.txtSrNo.setText(position + 1 + "");
        holder.txt_company_name.setText(data.getCompanyName());
        holder.txt_bank_name_rep.setText(data.getBankName());
        holder.txt_amt_rep.setText(data.getAmount());
        holder.txt_payment_type_rep.setText(data.getPayment_type());
        holder.txt_payment_info_rep.setText(data.getPayment_info());
        holder.txt_detail_rep.setText(data.getDetail());
        holder.txt_date_rep.setText(data.getDate());

        holder.txtSrNo.setTypeface(Application.fontOxygenRegular);
        holder.txt_company_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_bank_name_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_amt_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_payment_type_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_payment_info_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_detail_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_date_rep.setTypeface(Application.fontOxygenRegular);

        return rowView;
    }


    class Holder {
        TextView txtSrNo, txt_company_name, txt_bank_name_rep, txt_amt_rep, txt_payment_type_rep, txt_payment_info_rep, txt_detail_rep, txt_date_rep;
    }
}

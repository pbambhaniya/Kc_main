package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleTransportReportItemModel;
import com.multipz.kc.Model.VehicleTransportReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 04-10-2017.
 */

public class VehicleTransportReportsItemAdapter extends BaseAdapter {

    Context context;

    ArrayList<VehicleTransportReportItemModel> list = new ArrayList<VehicleTransportReportItemModel>();
    VehicleTransportReportItemModel data;
    LayoutInflater inflater;


    public VehicleTransportReportsItemAdapter(Context context, ArrayList<VehicleTransportReportItemModel> list) {
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
        VehicleTransportReportsItemAdapter.Holder holder;

        if (rowView == null) {
            holder = new VehicleTransportReportsItemAdapter.Holder();
            rowView = inflater.inflate(R.layout.list_item_vehicle_transport_reoprt_item, parent, false);


            holder.txt_serial_no = rowView.findViewById(R.id.txt_serial_no);
            holder.txt_user_name = rowView.findViewById(R.id.txt_user_name);
            holder.txt_load_to = rowView.findViewById(R.id.txt_load_to);
            holder.txt_load_com_name = rowView.findViewById(R.id.txt_load_com_name);
            holder.txt_load_amount = rowView.findViewById(R.id.txt_load_amount);
            holder.txt_empty_to = rowView.findViewById(R.id.txt_empty_to);
            holder.txt_empty_com_name = rowView.findViewById(R.id.txt_empty_com_name);
            holder.txt_empty_amount = rowView.findViewById(R.id.txt_empty_amount);
            holder.txt_profit = rowView.findViewById(R.id.txt_profit);
            holder.txt_date = rowView.findViewById(R.id.txt_date);


            holder.txt_serial_no.setTypeface(Application.fontOxygenRegular);
            holder.txt_user_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_load_to.setTypeface(Application.fontOxygenRegular);
            holder.txt_load_com_name.setTypeface(Application.fontoxegenregular);
            holder.txt_load_amount.setTypeface(Application.fontoxegenregular);
            holder.txt_empty_to.setTypeface(Application.fontoxegenregular);
            holder.txt_empty_com_name.setTypeface(Application.fontOxygenRegular);
            holder.txt_empty_amount.setTypeface(Application.fontOxygenRegular);
            holder.txt_profit.setTypeface(Application.fontoxegenregular);
            holder.txt_date.setTypeface(Application.fontOxygenRegular);
            rowView.setTag(holder);
        }

        holder = (VehicleTransportReportsItemAdapter.Holder) rowView.getTag();

        data = list.get(position);
        holder.txt_serial_no.setText(position + 1 + "");
        holder.txt_user_name.setText(data.getName());
        holder.txt_load_to.setText(data.getLoad_to());
        holder.txt_load_com_name.setText(data.getLoadCompanyName());
        holder.txt_load_amount.setText(data.getLoad_amount());
        holder.txt_empty_to.setText(data.getEMPTY_TO());
        holder.txt_empty_com_name.setText(data.getEmptyCompanyName());
        holder.txt_empty_amount.setText(data.getEmpty_amount());
        holder.txt_profit.setText(data.getProfit());
        holder.txt_date.setText(data.getCreated_date());
        return rowView;
    }


    class Holder {
        TextView txt_serial_no, txt_user_name, txt_load_to, txt_load_com_name, txt_load_amount, txt_empty_to, txt_empty_com_name, txt_empty_amount, txt_date, txt_profit;
    }
}

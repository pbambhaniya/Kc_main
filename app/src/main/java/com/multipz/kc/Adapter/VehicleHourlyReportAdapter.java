package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleHourlyReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;

import java.util.ArrayList;

/**
 * Created by Admin on 14-09-2017.
 */

public class VehicleHourlyReportAdapter extends RecyclerView.Adapter<VehicleHourlyReportAdapter.MyViewHolder> {
    Context context;
    ArrayList<VehicleHourlyReportModel> list = new ArrayList<VehicleHourlyReportModel>();
    MyAsyncTask.AsyncInterface asyncInterface;
    private ItemClickListener clickListener;

    public VehicleHourlyReportAdapter(Context context, ArrayList<VehicleHourlyReportModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_hourly_report_item, parent, false);
        return new MyViewHolder(itemView);
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VehicleHourlyReportModel data = list.get(position);
        holder.txt_com_name.setText(data.getName());
        holder.txt_total_remaining_amount.setText(data.getAmount());
        holder.txt_got_amount.setText(data.getGet_amount());
        holder.txt_actual_total.setText(data.getActualRemaining());

        holder.txt_com_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_total_remaining_amount.setTypeface(Application.fontOxygenRegular);
        holder.txt_got_amount.setTypeface(Application.fontOxygenRegular);
        holder.txt_actual_total.setTypeface(Application.fontOxygenRegular);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_com_name, txt_total_remaining_amount, txt_got_amount, txt_actual_total;
        Button btnRemainingAmt, btnGotAmt;

        public MyViewHolder(View view) {
            super(view);
            txt_com_name = (TextView) view.findViewById(R.id.txt_com_name);
            txt_total_remaining_amount = (TextView) view.findViewById(R.id.txt_total_remaining_amount);
            txt_got_amount = (TextView) view.findViewById(R.id.txt_got_amount);
            txt_actual_total = (TextView) view.findViewById(R.id.txt_actual_total);
            btnRemainingAmt = (Button) view.findViewById(R.id.btnRemainingAmt);
            btnGotAmt = (Button) view.findViewById(R.id.btnGotAmt);

            btnRemainingAmt.setOnClickListener(this);
            btnGotAmt.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }
    }

}

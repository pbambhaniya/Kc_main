package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleHourlyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;

import java.util.ArrayList;

/**
 * Created by Admin on 14-09-2017.
 */

public class VehicleHourlyAdapter extends RecyclerView.Adapter<VehicleHourlyAdapter.MyViewHolder> {
    Context context;
    ArrayList<VehicleHourlyModel> list = new ArrayList<VehicleHourlyModel>();
    MyAsyncTask.AsyncInterface asyncInterface;
    private ItemClickListener clickListener;

    public VehicleHourlyAdapter(Context context, ArrayList<VehicleHourlyModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_hourly_item, parent, false);
        return new MyViewHolder(itemView);
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VehicleHourlyModel data = list.get(position);
        holder.txt_com_name.setText(data.getCompanyName());
        holder.txt_vehicle_no.setText(data.getVehicle_no());
        holder.txt_amount.setText(data.getAmount());
        holder.txt_start_time_rep.setText(data.getStart_time());
        holder.txt_end_time_rep.setText(data.getEnd_time());

        holder.txt_com_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_vehicle_no.setTypeface(Application.fontOxygenRegular);
        holder.txt_amount.setTypeface(Application.fontOxygenRegular);
        holder.txt_start_time_rep.setTypeface(Application.fontOxygenRegular);
        holder.txt_end_time_rep.setTypeface(Application.fontOxygenRegular);

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
        TextView txt_com_name, txt_vehicle_no, txt_amount, txt_start_time_rep, txt_end_time_rep;
        ImageView img_delete;

        public MyViewHolder(View view) {
            super(view);
            txt_com_name = (TextView) view.findViewById(R.id.txt_com_name);
            txt_vehicle_no = (TextView) view.findViewById(R.id.txt_vehicle_no);
            txt_amount = (TextView) view.findViewById(R.id.txt_amount);
            txt_start_time_rep = (TextView) view.findViewById(R.id.txt_start_time_rep);
            txt_end_time_rep = (TextView) view.findViewById(R.id.txt_end_time_rep);

            img_delete = (ImageView) view.findViewById(R.id.img_delete);
            img_delete.setOnClickListener(this);
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

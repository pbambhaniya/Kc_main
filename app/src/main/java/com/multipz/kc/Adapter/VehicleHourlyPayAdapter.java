package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleHourlyPayModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Admin on 29-09-2017.
 */

public class VehicleHourlyPayAdapter extends RecyclerView.Adapter<VehicleHourlyPayAdapter.MyViewHolder> {
    Context context;
    ArrayList<VehicleHourlyPayModel> list = new ArrayList<VehicleHourlyPayModel>();
    VehicleHourlyPayModel data;
    private ItemClickListener clickListener;

    public VehicleHourlyPayAdapter(Context context, ArrayList<VehicleHourlyPayModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_hourly_pay_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VehicleHourlyPayModel data = list.get(position);

        holder.txt_com_name.setText(data.getName());
        holder.txt_bank_name.setText(data.getBank_name());
        holder.txt_pay_type.setText(data.getPayment_type());
        holder.txt_com_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_bank_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_pay_type.setTypeface(Application.fontOxygenRegular);

        if (data.getBank_name().contentEquals("null")) {
            holder.txt_bank_name.setText("-");
        }
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
        TextView txt_com_name, txt_bank_name, txt_pay_type;
        ImageView img_tra;

        public MyViewHolder(View view) {
            super(view);
            txt_com_name = (TextView) view.findViewById(R.id.txt_com_name);
            txt_bank_name = (TextView) view.findViewById(R.id.txt_bank_name);
            txt_pay_type = (TextView) view.findViewById(R.id.txt_pay_type);
            img_tra = (ImageView) view.findViewById(R.id.img_tra);
            img_tra.setOnClickListener(this);
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


package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleTransportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Admin on 15-09-2017.
 */

public class VehicleTransportAdapter extends RecyclerView.Adapter<VehicleTransportAdapter.MyViewHolder> {
    Context context;
    ArrayList<VehicleTransportModel> list = new ArrayList<VehicleTransportModel>();
    private ItemClickListener clickListener;

    public VehicleTransportAdapter(Context context, ArrayList<VehicleTransportModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_transport_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VehicleTransportModel model = list.get(position);

        holder.txt_empty_com_name.setText(model.getEmptyCompanyName());
        holder.txt_vehicle_no.setText(model.getVehicle_no());
        holder.txt_user_name.setText(model.getUserName());
        holder.txt_load_com_name.setText(model.getLoadCompanyName());

        holder.txt_user_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_vehicle_no.setTypeface(Application.fontOxygenRegular);
        holder.txt_load_com_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_empty_com_name.setTypeface(Application.fontOxygenRegular);
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
        TextView txt_user_name, txt_vehicle_no, txt_load_com_name, txt_empty_com_name;
        ImageView deletetransport;

        public MyViewHolder(View view) {
            super(view);
            txt_user_name = (TextView) view.findViewById(R.id.txt_user_name);
            txt_vehicle_no = (TextView) view.findViewById(R.id.txt_vehicle_no);
            txt_load_com_name = (TextView) view.findViewById(R.id.txt_load_com_name);
            txt_empty_com_name = (TextView) view.findViewById(R.id.txt_empty_com_name);
            deletetransport = (ImageView) view.findViewById(R.id.deletetransport);

            deletetransport.setOnClickListener(this);
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

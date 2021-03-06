package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multipz.kc.Model.VehicleExReportModel;
import com.multipz.kc.Model.VehicleTransportReportsModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Admin on 04-10-2017.
 */

public class VehicleTransportReportssAdapter extends RecyclerView.Adapter<VehicleTransportReportssAdapter.MyViewHolder> {

    Context context;
    ArrayList<VehicleTransportReportsModel> list = new ArrayList<VehicleTransportReportsModel>();
    VehicleExReportModel data;
    LayoutInflater inflater;
    private ItemClickListener clickListener;


    public VehicleTransportReportssAdapter(Context context, ArrayList<VehicleTransportReportsModel> list) {
        this.context = context;
        this.list = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_transports_report_item, parent, false);
        return new VehicleTransportReportssAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VehicleTransportReportsModel data = list.get(position);
        holder.txt_vehicle_type.setText(data.getVehicle_type());
        holder.txt_vehicle_no.setText(data.getVehicle_no());

        holder.txt_vehicle_type.setTypeface(Application.fontOxygenRegular);
        holder.txt_vehicle_no.setTypeface(Application.fontOxygenRegular);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_vehicle_type, txt_vehicle_no, txt_total_expense;

        public MyViewHolder(View view) {
            super(view);
            txt_vehicle_type = (TextView) view.findViewById(R.id.txt_vehicle_type);
            txt_vehicle_no = (TextView) view.findViewById(R.id.txt_vehicle_no);
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

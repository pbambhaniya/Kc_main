package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.TransPortInOutModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;

import java.util.ArrayList;

/**
 * Created by Admin on 02-10-2017.
 */

public class TransPortInOutAdapter extends RecyclerView.Adapter<TransPortInOutAdapter.MyViewHolder> {
    Context context;
    ArrayList<TransPortInOutModel> list = new ArrayList<TransPortInOutModel>();
    TransPortInOutModel data;
    MyAsyncTask.AsyncInterface asyncInterface;
    private ItemClickListener clickListener;

    public TransPortInOutAdapter(Context context, ArrayList<TransPortInOutModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_out_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        TransPortInOutModel data = list.get(position);

        holder.type.setText(data.getType());
        holder.pay_type.setText(data.getPayment_type());
        holder.amount.setText(data.getAmount());
        holder.com_name.setText(data.getName());

        holder.type.setTypeface(Application.fontOxygenRegular);
        holder.pay_type.setTypeface(Application.fontOxygenRegular);
        holder.amount.setTypeface(Application.fontOxygenRegular);
        holder.com_name.setTypeface(Application.fontOxygenRegular);

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView type, pay_type, amount, com_name;
        ImageView img_tra;

        public MyViewHolder(View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.type);
            pay_type = (TextView) view.findViewById(R.id.pay_type);
            amount = (TextView) view.findViewById(R.id.amount);
            com_name = (TextView) view.findViewById(R.id.com_name);

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

package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.multipz.kc.Model.TransportCompanyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;

import java.util.ArrayList;

/**
 * Created by Admin on 14-09-2017.
 */

public class TransportCompanyReportAdapter extends RecyclerView.Adapter<TransportCompanyReportAdapter.MyViewHolder> {
    Context context;
    ArrayList<TransportCompanyModel> list = new ArrayList<TransportCompanyModel>();
    MyAsyncTask.AsyncInterface asyncInterface;
    private ItemClickListener clickListener;

    public TransportCompanyReportAdapter(Context context, ArrayList<TransportCompanyModel> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transport_company_item, parent, false);
        return new MyViewHolder(itemView);
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TransportCompanyModel data = list.get(position);
        holder.txt_party_name.setText(data.getPartyName());
        holder.txt_total_debit_credit.setText(data.getAmt1());
        holder.txt_party_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_total_debit_credit.setTypeface(Application.fontOxygenRegular);

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
        TextView txt_party_name, txt_total_debit_credit;
        Button btnTransportDetail, btnPaymentDetail;

        public MyViewHolder(View view) {
            super(view);
            txt_party_name = (TextView) view.findViewById(R.id.txt_party_name);
            txt_total_debit_credit = (TextView) view.findViewById(R.id.txt_total_debit_credit);
            btnTransportDetail = (Button) view.findViewById(R.id.btnTransportDetail);
            btnPaymentDetail = (Button) view.findViewById(R.id.btnPaymentDetail);
            btnTransportDetail.setOnClickListener(this);
            btnPaymentDetail.setOnClickListener(this);
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

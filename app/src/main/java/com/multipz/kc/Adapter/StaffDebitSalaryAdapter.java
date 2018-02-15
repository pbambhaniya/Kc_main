package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.StaffDebitSalaryModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Admin on 29-09-2017.
 */

public class StaffDebitSalaryAdapter extends RecyclerView.Adapter<StaffDebitSalaryAdapter.MyViewHolder> {

    Context context;
    ArrayList<StaffDebitSalaryModel> list = new ArrayList<StaffDebitSalaryModel>();
    StaffDebitSalaryModel data;
    private ItemClickListener clickListener;

    public StaffDebitSalaryAdapter(Context context, ArrayList<StaffDebitSalaryModel> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.staff_debit_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        StaffDebitSalaryModel data = list.get(position);

        holder.txt_user_name.setText(data.getUserName());
        holder.txt_bank_name.setText(data.getBankName());
        holder.txt_salary_base_type.setText(data.getSalary_based_type());
        holder.txt_pay_type.setText(data.getPayment_type());
        holder.txt_detail.setText(data.getDetail());

        holder.txt_user_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_bank_name.setTypeface(Application.fontOxygenRegular);
        holder.txt_salary_base_type.setTypeface(Application.fontOxygenRegular);
        holder.txt_pay_type.setTypeface(Application.fontOxygenRegular);
        holder.txt_detail.setTypeface(Application.fontOxygenRegular);

        if (data.getBankName().contentEquals("null"))
        {
            holder.txt_bank_name.setText("--");
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
        TextView txt_user_name, txt_bank_name, txt_salary_base_type, txt_pay_type, txt_detail;
        ImageView img_delete;

        public MyViewHolder(View view) {
            super(view);
            txt_user_name = (TextView) view.findViewById(R.id.txt_user_name);
            txt_bank_name = (TextView) view.findViewById(R.id.txt_bank_name);
            txt_salary_base_type = (TextView) view.findViewById(R.id.txt_salary_base_type);
            txt_pay_type = (TextView) view.findViewById(R.id.txt_pay_type);
            txt_detail = (TextView) view.findViewById(R.id.txt_detail);
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
//
//

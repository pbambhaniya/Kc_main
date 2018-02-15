package com.multipz.kc.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multipz.kc.Model.GetStaffAsPerTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.Shared;

import java.util.ArrayList;

public class showAttendanceAdapter extends RecyclerView.Adapter<showAttendanceAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<GetStaffAsPerTypeModel> listData;
    private ItemClickListener clickListener;
    Shared shared;
    int pos;
    LayoutInflater inflater;

    public showAttendanceAdapter(Context c, ArrayList<GetStaffAsPerTypeModel> timeSlots) {
        mContext = c;
        this.listData = timeSlots;
        shared = new Shared(mContext);
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_staff_attendance, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GetStaffAsPerTypeModel model = listData.get(position);
        holder.txt_staff_name.setText(model.getName());
        holder.txt_work_type.setText(model.getWork_type());
        holder.txt_kc_amount.setText(model.getAmount());
        holder.txt_salary.setText(model.getSalary());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_staff_name, txt_work_type, txt_kc_amount, txt_salary;

        public MyViewHolder(View view) {
            super(view);
            txt_staff_name = (TextView) view.findViewById(R.id.txt_staff_name);
            txt_work_type = (TextView) view.findViewById(R.id.txt_work_type);
            txt_kc_amount = (TextView) view.findViewById(R.id.txt_kc_amount);
            txt_salary = (TextView) view.findViewById(R.id.txt_salary);
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

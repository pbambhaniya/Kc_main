package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Adapter.WorkTypeAdapter;
import com.multipz.kc.Model.StaffModel;
import com.multipz.kc.Model.VehicleTransportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 19-09-2017.
 */

public class StaffAdapter extends BaseAdapter {
    Context context;
    ArrayList<StaffModel> list = new ArrayList<StaffModel>();
    StaffModel data;

    public StaffAdapter(Context context, ArrayList<StaffModel> list) {
        this.context = context;
        this.list = list;
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
        data = list.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);

        View layoutview = inflater.inflate(R.layout.staff_item, parent, false);
        TextView txt_name = (TextView) layoutview.findViewById(R.id.txt_name);
        txt_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_work_type = (TextView) layoutview.findViewById(R.id.txt_work_type);
        txt_work_type.setTypeface(Application.fontOxygenRegular);
        TextView txt_sallary_type = (TextView) layoutview.findViewById(R.id.txt_sallary_type);
        txt_sallary_type.setTypeface(Application.fontOxygenRegular);
        TextView txt_salary = (TextView) layoutview.findViewById(R.id.txt_salary);
        txt_salary.setTypeface(Application.fontOxygenRegular);

        txt_name.setText(data.getName());
        txt_work_type.setText(data.getWork_type());
        String salarybase = data.getSalary_based_type();

        txt_sallary_type.setText(salarybase);

        txt_salary.setText(data.getSalary());


        return layoutview;
    }
}

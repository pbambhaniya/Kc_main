package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.multipz.kc.Model.CompanyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 03-08-2017.
 */

public class Company_Master_Adapter extends BaseAdapter {

    Context context;
    ArrayList<CompanyModel> list = new ArrayList<CompanyModel>();
    CompanyModel data;

    public Company_Master_Adapter(Context context, ArrayList<CompanyModel> list) {
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

        View layoutview = inflater.inflate(R.layout.company_item, parent, false);

        TextView txt_name = (TextView) layoutview.findViewById(R.id.mst_name);
        txt_name.setTypeface(Application.fontOxygenRegular);
//        TextView txt_address = (TextView) layoutview.findViewById(R.id.mst_address);
//        txt_address.setTypeface(Application.fontOxygenRegular);
        TextView txt_mobile = (TextView) layoutview.findViewById(R.id.mst_mobile);
        txt_mobile.setTypeface(Application.fontOxygenRegular);

        txt_name.setText(data.getName());
//        txt_address.setText(data.getName());
        txt_mobile.setText(data.getContact_no());

        return layoutview;
    }


}

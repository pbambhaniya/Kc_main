package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.multipz.kc.Model.MaterialCompanyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 01-08-2017.
 */

public class MaterialCompanyAdapter extends BaseAdapter {

    Context context;
    ArrayList<MaterialCompanyModel> list = new ArrayList<MaterialCompanyModel>();
    MaterialCompanyModel data;


    public MaterialCompanyAdapter(Context context, ArrayList<MaterialCompanyModel> list) {
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

        View layoutview = inflater.inflate(R.layout.material_company_item, parent, false);

        TextView txt_asset = (TextView) layoutview.findViewById(R.id.asset_name);

        txt_asset.setTypeface(Application.fontOxygenRegular);

        TextView txt_company = (TextView) layoutview.findViewById(R.id.company);
        txt_company.setTypeface(Application.fontOxygenRegular);

        txt_asset.setText(data.getMatetailName());
        txt_company.setText(data.getName());

        return layoutview;
    }
}

package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.CompanyImportModel;
import com.multipz.kc.Model.CompanyPayModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 14-09-2017.
 */

public class CompanyImporAdapter extends BaseAdapter {

    Context context;
    ArrayList<CompanyImportModel> list = new ArrayList<CompanyImportModel>();
    public CompanyImportModel data;

    public CompanyImporAdapter(Context context, ArrayList<CompanyImportModel> list) {
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

        View layoutview = inflater.inflate(R.layout.company_import_item, parent, false);

        TextView txt_com_name = (TextView) layoutview.findViewById(R.id.txt_com_name);
        txt_com_name.setTypeface(Application.fontoxegenregular);

        TextView txt_pro_name = (TextView) layoutview.findViewById(R.id.txt_pro_name);
        txt_pro_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_material_name = (TextView) layoutview.findViewById(R.id.txt_material_name);
        txt_material_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_amount = (TextView) layoutview.findViewById(R.id.txt_amount);
        txt_amount.setTypeface(Application.fontOxygenRegular);
        TextView txt_detail = (TextView) layoutview.findViewById(R.id.txt_detail);
        txt_detail.setTypeface(Application.fontOxygenRegular);

        txt_com_name.setText(data.getCompanyName());
        txt_pro_name.setText(data.getSide_sort_name());
        txt_amount.setText(data.getAmount());
        txt_detail.setText(data.getDetail());
        txt_material_name.setText(data.getMaterial_type());

        return layoutview;
    }


}

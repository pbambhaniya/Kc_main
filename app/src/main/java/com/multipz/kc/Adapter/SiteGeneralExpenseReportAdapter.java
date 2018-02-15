package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.Model.SiteExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 12-09-2017.
 */

public class SiteGeneralExpenseReportAdapter extends BaseAdapter {

    Context context;
    ArrayList<SiteExReportModel> list = new ArrayList<SiteExReportModel>();
    SiteExReportModel data;

    public SiteGeneralExpenseReportAdapter(Context context, ArrayList<SiteExReportModel> list) {
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

        View layoutview = inflater.inflate(R.layout.list_item_gen_exp_report, parent, false);
        TextView txt_kc_name = (TextView) layoutview.findViewById(R.id.txt_kc_name);
        TextView txt_gen_exp_detail = (TextView) layoutview.findViewById(R.id.txt_gen_exp_detail);
        TextView txt_kc_amount = (TextView) layoutview.findViewById(R.id.txt_kc_amount);


        txt_kc_name.setText(data.getName_of_work());
        txt_gen_exp_detail.setText(data.getDetail());
        txt_kc_amount.setText(data.getAmount());
        txt_kc_name.setTypeface(Application.fontOxygenRegular);
        txt_gen_exp_detail.setTypeface(Application.fontOxygenRegular);
        txt_kc_amount.setTypeface(Application.fontOxygenRegular);

        return layoutview;
    }

}

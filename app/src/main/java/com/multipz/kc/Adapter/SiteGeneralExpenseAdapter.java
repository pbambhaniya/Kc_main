package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 12-09-2017.
 */

public class SiteGeneralExpenseAdapter extends BaseAdapter {

    Context context;
    ArrayList<KCExpenseModel> list = new ArrayList<KCExpenseModel>();
    KCExpenseModel data;

    public SiteGeneralExpenseAdapter(Context context, ArrayList<KCExpenseModel> list) {
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

        View layoutview = inflater.inflate(R.layout.staff_expense_item, parent, false);
        TextView txt_project = (TextView) layoutview.findViewById(R.id.txt_project);
        txt_project.setTypeface(Application.fontoxegenregular);
        TextView txt_detail = (TextView) layoutview.findViewById(R.id.txt_kc_detail);
        TextView txt_amount = (TextView) layoutview.findViewById(R.id.txt_kc_amount);
        txt_detail.setText(data.getDetail());
        txt_project.setText(data.getSide_sort_name());
        txt_amount.setText(data.getAmount());
        txt_detail.setTypeface(Application.fontOxygenRegular);
        txt_amount.setTypeface(Application.fontOxygenRegular);


        return layoutview;
    }

}

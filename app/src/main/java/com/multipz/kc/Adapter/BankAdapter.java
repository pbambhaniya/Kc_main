package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.multipz.kc.Model.BankModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;


import java.util.ArrayList;

/**
 * Created by Admin on 03-08-2017.
 */

public class BankAdapter extends BaseAdapter {
    Context context;
    ArrayList<BankModel> list = new ArrayList<BankModel>();
    BankModel data;

    public BankAdapter(Context context, ArrayList<BankModel> list) {
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

        View layoutview = inflater.inflate(R.layout.bank_item, parent, false);

        TextView txt_name = (TextView) layoutview.findViewById(R.id.bank_name);
        txt_name.setTypeface(Application.fontOxygenRegular);
        TextView txt_branch = (TextView) layoutview.findViewById(R.id.bank_branch);
        txt_branch.setTypeface(Application.fontOxygenRegular);
        TextView txt_ifsc = (TextView) layoutview.findViewById(R.id.bank_ifsc);
        txt_ifsc.setTypeface(Application.fontOxygenRegular);

        txt_name.setText(data.getBank_name());
        txt_branch.setText(data.getBranch());
        txt_ifsc.setText(data.getIfsc_code());

        return layoutview;
    }
}

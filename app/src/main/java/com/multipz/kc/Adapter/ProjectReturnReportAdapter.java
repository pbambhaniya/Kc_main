package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Model.ProjectReturnReportModel;
import com.multipz.kc.Model.ProjectReturnsModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 04-10-2017.
 */

public class ProjectReturnReportAdapter extends BaseAdapter {
    Context context;

    ArrayList<ProjectReturnReportModel> list = new ArrayList<ProjectReturnReportModel>();
    ProjectReturnReportModel data;

    public ProjectReturnReportAdapter(Context context, ArrayList<ProjectReturnReportModel> list) {
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

        View layoutview = inflater.inflate(R.layout.project_return_report_item, parent, false);


        TextView txt_serial_no = (TextView) layoutview.findViewById(R.id.txt_serial_no);
        txt_serial_no.setTypeface(Application.fontOxygenRegular);

        TextView txt_project_name = (TextView) layoutview.findViewById(R.id.txt_project_name);
        txt_project_name.setTypeface(Application.fontOxygenRegular);

        TextView txt_start_date = (TextView) layoutview.findViewById(R.id.txt_start_date);
        txt_start_date.setTypeface(Application.fontOxygenRegular);

        TextView txt_budget = (TextView) layoutview.findViewById(R.id.txt_budget);
        txt_budget.setTypeface(Application.fontOxygenRegular);


        TextView txt_deposite = (TextView) layoutview.findViewById(R.id.txt_deposite);
        txt_deposite.setTypeface(Application.fontOxygenRegular);

        TextView txt_sd = (TextView) layoutview.findViewById(R.id.txt_sd);
        txt_sd.setTypeface(Application.fontOxygenRegular);

        TextView txt_fmd = (TextView) layoutview.findViewById(R.id.txt_fmd);
        txt_fmd.setTypeface(Application.fontOxygenRegular);

        TextView txt_tds = (TextView) layoutview.findViewById(R.id.txt_tds);
        txt_tds.setTypeface(Application.fontOxygenRegular);


        TextView txt_total_pay = (TextView) layoutview.findViewById(R.id.txt_total_pay);
        txt_total_pay.setTypeface(Application.fontOxygenRegular);

        TextView txt_remaining = (TextView) layoutview.findViewById(R.id.txt_remaining);
        txt_remaining.setTypeface(Application.fontOxygenRegular);
        TextView txt_total_back = (TextView) layoutview.findViewById(R.id.txt_total_back);
        txt_total_back.setTypeface(Application.fontOxygenRegular);


        txt_serial_no.setText(position + 1 + "");
        txt_start_date.setText(data.getStart_date());
        txt_project_name.setText(data.getSide_sort_name());
        txt_budget.setText(data.getBudget());
        txt_deposite.setText(data.getDeposite());
        txt_sd.setText(data.getSd());
        txt_fmd.setText(data.getFmg());
        txt_tds.setText(data.getTds());

        txt_total_pay.setText(data.getTotalpay());
        txt_total_back.setText(data.getTotalback());
        txt_remaining.setText(data.getRemaining());
        txt_tds.setText(data.getTds());


        return layoutview;
    }


}

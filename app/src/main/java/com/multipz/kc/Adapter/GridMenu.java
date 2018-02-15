package com.multipz.kc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.multipz.kc.R;
import com.multipz.kc.util.Application;

import java.util.ArrayList;

/**
 * Created by Admin on 08-08-2017.
 */

public class GridMenu extends BaseAdapter{


    Context context;
    ArrayList<com.multipz.kc.Model.GridMenu> data;
    LayoutInflater inflater;

    public GridMenu(Context context, ArrayList<com.multipz.kc.Model.GridMenu> data) {
        this.context = context;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View rowView = view;
        Holder holder;

        if (rowView == null){
            holder = new Holder();
            rowView = inflater.inflate(R.layout.view_gridmenu, null);

            holder.txt = rowView.findViewById(R.id.txt_gridmenu);
            holder.img = rowView.findViewById(R.id.img_gridmenu);


            rowView.setTag(holder);

        }

        holder = (Holder) rowView.getTag();

        holder.txt.setText(data.get(position).getName());
//        Application.setFontDefault((RelativeLayout)view.findViewById(R.id.rel_root));


        holder.img.setImageDrawable(context.getResources().getDrawable(data.get(position).getDrawable()));

        return rowView;
    }

    class Holder{

        TextView txt;
        ImageView img;

    }

}

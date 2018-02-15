package com.multipz.kc.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.multipz.kc.GeneralExpense.KCExpense;
import com.multipz.kc.GeneralExpense.SiteGeneralExpense;
import com.multipz.kc.GeneralExpense.VehicleGeneralExpense;
import com.multipz.kc.Model.GridMenu;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Shared;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneralExpenseFragment extends Fragment {
    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;
    Shared shared;

    public GeneralExpenseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        getActivity().setTitle("કેસી નો ખર્ચ");
        shared = new Shared(getActivity());
        context = getActivity();
        data = new ArrayList<>();

        gridMenu = view.findViewById(R.id.gridmenu);

        setData();
        adapter = new com.multipz.kc.Adapter.GridMenu(context, data);
        gridMenu.setAdapter(adapter);

        gridMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (shared.getBoolean(Config.isLogin, false)) {
                    try {
                        JSONObject job = new JSONObject(shared.getString(Config.CurrentUser, "{}"));
                        if (job.getString("user_type").contentEquals("A")) {
                            switch (position) {
                                case 0:
                                    startActivity(new Intent(context, KCExpense.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(context, SiteGeneralExpense.class));
                                    break;
                                case 2:
                                    startActivity(new Intent(context, VehicleGeneralExpense.class));
                                    break;
                            }
                        } else if (job.getString("user_type").contentEquals("S")) {
                            switch (position) {
                                case 0:
                                    startActivity(new Intent(context, VehicleGeneralExpense.class));
                                    break;
                                case 1:
                                    startActivity(new Intent(context, SiteGeneralExpense.class));
                                    break;
                            }
                        }
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        });
        return view;
    }

    private void setData() {
        if (shared.getBoolean(Config.isLogin, false)) {

            try {
                JSONObject job = new JSONObject(shared.getString(Config.CurrentUser, "{}"));
                if (job.getString("user_type").contentEquals("A")) {
                    data.add(new GridMenu(getResources().getString(R.string.Kc_general_expense), R.drawable.kc_expence_guj));
                    data.add(new GridMenu(getResources().getString(R.string.Site_general_expense), R.drawable.side_exp_guj));
                    data.add(new GridMenu(getResources().getString(R.string.Vehicle_General_EX), R.drawable.vehecle_expense));

                } else if (job.getString("user_type").contentEquals("S")) {
                    data.add(new GridMenu(getResources().getString(R.string.Vehicle_General_EX), R.drawable.vehecle_expense));
                    data.add(new GridMenu(getResources().getString(R.string.Site_general_expense), R.drawable.side_exp_guj));
                }
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


//        data.add(new GridMenu("Vehicle Expense", R.drawable.expences));
    }
}
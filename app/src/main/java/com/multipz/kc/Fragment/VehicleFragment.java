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

import com.multipz.kc.Report.ProjectReturnReport;
import com.multipz.kc.Vehicle.Transport_In_out_Activity;
import com.multipz.kc.GeneralExpense.VehicleGeneralExpense;
import com.multipz.kc.Model.GridMenu;
import com.multipz.kc.R;
import com.multipz.kc.Vehicle.VehicleHourly;
import com.multipz.kc.Vehicle.VehicleHourlyPay;
import com.multipz.kc.Vehicle.VehicleList;
import com.multipz.kc.Vehicle.VehicleTransport;
import com.multipz.kc.Vehicle.VehicleType;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VehicleFragment extends Fragment {

    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;

    public VehicleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        getActivity().setTitle(getResources().getString(R.string.Vehicle));
        context = getActivity();
        data = new ArrayList<>();

        gridMenu = view.findViewById(R.id.gridmenu);

        setData();
        adapter = new com.multipz.kc.Adapter.GridMenu(context, data);
        gridMenu.setAdapter(adapter);

        gridMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(context, VehicleType.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, VehicleList.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, VehicleHourly.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, VehicleHourlyPay.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, VehicleTransport.class));
                        break;
                    case 5:
                        startActivity(new Intent(context, Transport_In_out_Activity.class));
                        break;

                }
            }
        });

        return view;
    }

    private void setData() {
        data.add(new GridMenu(getResources().getString(R.string.Type), R.drawable.vehecle_type_guj));
        data.add(new GridMenu(getResources().getString(R.string.Detail), R.drawable.vehicle_detail_guj));
        data.add(new GridMenu(getResources().getString(R.string.Vehicle_Hourly), R.drawable.hourly_paid_vehicle_guj));
        data.add(new GridMenu(getResources().getString(R.string.Vehicl_Hourly_Pay), R.drawable.hourlly_income));
        data.add(new GridMenu(getResources().getString(R.string.Vehicle_Transport), R.drawable.transport_vehicle_guj));
        data.add(new GridMenu(getResources().getString(R.string.Transport_In_Out), R.drawable.transport_inout_guj));
    }
}
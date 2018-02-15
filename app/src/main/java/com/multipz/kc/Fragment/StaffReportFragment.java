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

import com.multipz.kc.ManageStaffAttendance.StaffAttendanceActivity;
import com.multipz.kc.Model.GridMenu;
import com.multipz.kc.R;
import com.multipz.kc.Staff.AmountStaff;
import com.multipz.kc.Staff.StaffDebitSalary;
import com.multipz.kc.Staff.Staffs;
import com.multipz.kc.Staff.Worktype;
import com.multipz.kc.StaffReport.StaffExpenseVehicleReport;
import com.multipz.kc.StaffReport.StaffSiteExpenseReport;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffReportFragment extends Fragment {
    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;

    public StaffReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        getActivity().setTitle(getResources().getString(R.string.Staff_report));
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
                        startActivity(new Intent(context, StaffSiteExpenseReport.class));
                        break;

                    case 1:
                        startActivity(new Intent(context, StaffExpenseVehicleReport.class));
                        break;


//                    default:
//                        break;
                }
            }
        });

        return view;
    }

    private void setData() {
        data.add(new GridMenu(getResources().getString(R.string.Staff_Site_ex_report), R.drawable.side_report));
        data.add(new GridMenu(getResources().getString(R.string.Staff_vehicle_ex_report), R.drawable.vehicle_expence_report_guj));


    }
}


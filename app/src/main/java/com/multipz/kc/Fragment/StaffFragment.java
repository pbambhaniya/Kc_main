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
import com.multipz.kc.Staff.SalaryPayAcivity;
import com.multipz.kc.Staff.StaffDebitSalary;
import com.multipz.kc.Staff.Staffs;
import com.multipz.kc.Staff.Worktype;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffFragment extends Fragment {
    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;

    public StaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        getActivity().setTitle("સ્ટાફ");
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
                        startActivity(new Intent(context, Worktype.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, Staffs.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, AmountStaff.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, StaffAttendanceActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, SalaryPayAcivity.class));
                        break;
                    case 5:
                        startActivity(new Intent(context, StaffDebitSalary.class));
                        break;
//                    default:
//                        break;
                }
            }
        });
        return view;
    }

    private void setData() {
        data.add(new GridMenu("કામ નો પ્રકાર", R.drawable.staff_type_guj));
        data.add(new GridMenu("સ્ટાફ", R.drawable.staff_guj));
        data.add(new GridMenu("સ્ટાફની રકમ", R.drawable.staff_amount));
        data.add(new GridMenu("સ્ટાફની હાજરી", R.drawable.staff_attendene_guj));
        data.add(new GridMenu("પગાર ની ચૂકવણી", R.drawable.salary));
        data.add(new GridMenu("સ્ટાફ નો ઉધાર પગાર", R.drawable.staff_uppad));

    }
}


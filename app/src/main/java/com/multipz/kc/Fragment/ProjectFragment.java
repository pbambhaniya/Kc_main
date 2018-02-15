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

import com.multipz.kc.Model.GridMenu;
import com.multipz.kc.Project.Banks;
import com.multipz.kc.Project.ProjectReturns;
import com.multipz.kc.Project.Projects;
import com.multipz.kc.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {

    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;

    public ProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_project, container, false);

        getActivity().setTitle("પ્રોજેક્ટ");
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
                        startActivity(new Intent(context, Banks.class));
                        break;
                    case 1:
                        startActivity(new Intent(context, Projects.class));
                        break;
                    case 2:
                        startActivity(new Intent(context, ProjectReturns.class));
                        break;
//                    case 3:
//                        startActivity(new Intent(context, Sites.class));
//                        break;
//                    default:
//                        break;
                }
            }
        });
        return view;


    }
    private void setData() {
        data.add(new GridMenu("બેંક", R.drawable.bank_guj_img));
        data.add(new GridMenu("પ્રોજેક્ટ", R.drawable.project_guj_img));
        data.add(new GridMenu("પ્રોજેક્ટ રિટર્ન્સ", R.drawable.project_return_guj));
//        data.add(new GridMenu("Site", R.drawable.site));
    }

}

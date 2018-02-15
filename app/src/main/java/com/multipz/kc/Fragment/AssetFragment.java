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

import com.multipz.kc.Asset.AssetCompany;
import com.multipz.kc.Asset.MaterialType;
import com.multipz.kc.Model.GridMenu;
import com.multipz.kc.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetFragment extends Fragment {
    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;

    public AssetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grid, container, false);
        getActivity().setTitle(getResources().getString(R.string.Material));
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
                        startActivity(new Intent(context, MaterialType.class));
                        break;
//                    case 1:
//                        startActivity(new Intent(context, AssetCompany.class));
//                        break;
//                    case 2:
//                        startActivity(new Intent(context, Wholeseller.class));
//                        break;
//                    default:
//                        break;
                }
            }
        });

        return view;
    }

    private void setData() {
        data.add(new GridMenu(getResources().getString(R.string.Material_Type), R.drawable.material_type_guj));
//        data.add(new GridMenu(getResources().getString(R.string.Company), R.drawable.asset_company));
//        data.add(new GridMenu("Wholeseller", R.drawable.whole_saller));
    }
}

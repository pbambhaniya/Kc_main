package com.multipz.kc.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.multipz.kc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IncomeFragment extends Fragment {


    public IncomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_grid, container, false);
        return  view;
    }

}

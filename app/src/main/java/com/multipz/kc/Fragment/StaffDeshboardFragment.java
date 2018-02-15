package com.multipz.kc.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class StaffDeshboardFragment extends Fragment {
    Context context;
    TextView txt_total_exp, txt_total_amt, txt_wallet;
    MyAsyncTask.AsyncInterface asyncInterface;
    Shared shared;

    public StaffDeshboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_staff_deshboard, container, false);
        getActivity().setTitle(getResources().getString(R.string.Home));

        shared = new Shared(getActivity());
        context = getActivity();
        txt_total_exp = (TextView) view.findViewById(R.id.txt_total_exp);
        txt_total_amt = (TextView) view.findViewById(R.id.txt_total_amt);
        txt_wallet = (TextView) view.findViewById(R.id.txt_wallet);

        callServiceResponse();
        if (Constant_method.checkConn(context)) {
            String param = "{\"user_id\":\"" + shared.getUserId().toString() + "\",\"action\":\"staffDashboard\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, getActivity(), asyncInterface, param, Config.Api_Staff_Deshboard);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {

                int success;
                String msg;
                if (flag == Config.Api_Staff_Deshboard) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        success = jsonObject.getInt("status");
                        if (success == 1) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            String totalExp = object.getString("totalExp");
                            String totalAmt = object.getString("totalAmt");
                            String wallet = object.getString("wallet");
                            txt_total_exp.setText(totalExp);
                            txt_total_amt.setText(totalAmt);
                            txt_wallet.setText(wallet);
                        } else {
                            msg = jsonObject.getString("msg");

                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
    }

}

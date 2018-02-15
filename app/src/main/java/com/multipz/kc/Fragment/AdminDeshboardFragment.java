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

import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminDeshboardFragment extends Fragment {
    Context context;
    TextView txt_project, txt_vehicle, txt_emp, txt_company, txt_bank, txt_kc_exp, txt_site_exp, txt_vehicle_exp;

    MyAsyncTask.AsyncInterface asyncInterface;

    public AdminDeshboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_admin_deshboard, container, false);
        getActivity().setTitle(getResources().getString(R.string.Home));
        context = getActivity();
        callServiceResponse();


        txt_project = (TextView) rootView.findViewById(R.id.txt_project);
        txt_vehicle = (TextView) rootView.findViewById(R.id.txt_vehicle);
        txt_emp = (TextView) rootView.findViewById(R.id.txt_emp);
        txt_company = (TextView) rootView.findViewById(R.id.txt_company);
        txt_bank = (TextView) rootView.findViewById(R.id.txt_bank);
        txt_kc_exp = (TextView) rootView.findViewById(R.id.txt_kc_exp);
        txt_site_exp = (TextView) rootView.findViewById(R.id.txt_site_exp);
        txt_vehicle_exp = (TextView) rootView.findViewById(R.id.txt_vehicle_exp);

        getAdminDeshBoard();
        return rootView;
    }

    private void getAdminDeshBoard() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"adminDashboard\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, getActivity(), asyncInterface, param, Config.API_TOKEN_ADMIN_DASHBOARD);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                int success;
                String msg;

                if (flag == Config.API_TOKEN_ADMIN_DASHBOARD) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        success = jsonObject.getInt("status");

                        if (success == 1) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            String Project = object.getString("cnt_project");
                            String Vehicle = object.getString("cnt_vehicle");
                            String Employee = object.getString("cnt_emp");
                            String Company = object.getString("cnt_company");
                            String Bank = object.getString("cnt_bank");
                            String KCExpance = object.getString("cnt_kcGenExp");
                            String SiteGenExp = object.getString("cnt_siteGenExp");
                            String VehicleGenExp = object.getString("cnt_vehicleGenExp");

                            txt_project.setText(Project);
                            txt_vehicle.setText(Vehicle);
                            txt_emp.setText(Employee);
                            txt_company.setText(Company);
                            txt_bank.setText(Bank);
                            txt_kc_exp.setText(KCExpance);
                            txt_site_exp.setText(SiteGenExp);
                            txt_vehicle_exp.setText(VehicleGenExp);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}

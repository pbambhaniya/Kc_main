package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Adapter.VehicleExReportAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.VehicleExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleExReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {
    Context context;
    int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean userScrolled = true;
    private int lastVisibleItem, firstVisible;

    private ArrayList<SpinnerModel> objects_project;
    ArrayList<VehicleExReportModel> userList;
    Shared shared;
    private LinearLayoutManager layoutManager;
    private VehicleExReportAdapter adapter;

    RecyclerView rv_vehicle_ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_ex_report);
        setTitle(getResources().getString(R.string.Vehicle_Expense));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared = new Shared(this);
        context = this;

        rv_vehicle_ex = (RecyclerView) findViewById(R.id.rv_vehicle_ex);
        getVehicleExpenseReport();

        rv_vehicle_ex.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        getVehicleExpenseReport();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_Get_vehicle_expense_report) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    userList = new ArrayList<>();
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleExReportModel vehicleExReport = new VehicleExReportModel();
                        vehicleExReport.setVehicle_detail_id(c.getString("vehicle_detail_id"));
                        vehicleExReport.setAmount(c.getString("amount"));
                        vehicleExReport.setVehicle_no(c.getString("vehicle_no"));
                        vehicleExReport.setVehicle_type(c.getString("vehicle_type"));
                        userList.add(vehicleExReport);
                    }
                    adapter = new VehicleExReportAdapter(context, userList);
                    layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv_vehicle_ex.setLayoutManager(layoutManager);
                    rv_vehicle_ex.setAdapter(adapter);
                    adapter.setClickListener(this);
                } else {

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getVehicleExpenseReport() {
        if (Constant_method.checkConn(context)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"vehicleExpReportData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleExReport.this, params, Config.APi_Get_vehicle_expense_report);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleExReport.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void itemClicked(View View, int position) {

        VehicleExReportModel item = userList.get(position);
        Intent intent = new Intent(VehicleExReport.this, VehicleExpenseItemActivity.class);
        intent.putExtra("vehicle_detail_id", item.getVehicle_detail_id());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
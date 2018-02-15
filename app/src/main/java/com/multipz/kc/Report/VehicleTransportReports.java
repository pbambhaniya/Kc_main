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
import android.widget.Button;
import android.widget.Toast;

import com.multipz.kc.Adapter.VehicleTransportReportssAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.VehicleTransportReportsModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleTransportReports extends AppCompatActivity implements MyAsyncTask.AsyncInterface,ItemClickListener {

    Context context;
    Button add;
    RecyclerView rv_vehicle_transport;
    int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private boolean userScrolled = true;
    private int lastVisibleItem, firstVisible;

    private ArrayList<SpinnerModel> objects_project;
    ArrayList<VehicleTransportReportsModel> userList;
    Shared shared;
    private LinearLayoutManager layoutManager;
    private VehicleTransportReportssAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_project_report);


        setTitle(getResources().getString(R.string.Vehicle_Transport));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared = new Shared(this);
        context = this;

        rv_vehicle_transport=(RecyclerView)findViewById(R.id.rv_vehicle_transport);
        getVehicleTransportReport();

        rv_vehicle_transport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        getVehicleTransportReport();
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
        if (flag == Config.API_Vehicle_Transports_reports) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    userList = new ArrayList<>();
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleTransportReportsModel vehicleExReport = new VehicleTransportReportsModel();
                        vehicleExReport.setVehicle_detail_id(c.getString("vehicle_detail_id"));
                        vehicleExReport.setVehicle_no(c.getString("vehicle_no"));
                        vehicleExReport.setVehicle_type(c.getString("vehicle_type"));
                        userList.add(vehicleExReport);
                    }
                    adapter = new VehicleTransportReportssAdapter(context, userList);
                    layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv_vehicle_transport.setLayoutManager(layoutManager);
                    rv_vehicle_transport.setAdapter(adapter);
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

    private void getVehicleTransportReport() {
        if (Constant_method.checkConn(context)) {
            String params = "{\"action\":\"vehicleTransportReport\",\"page\":\""+pagecount+"\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleTransportReports.this, params, Config.API_Vehicle_Transports_reports);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleTransportReports.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void itemClicked(View View, int position) {

        VehicleTransportReportsModel item = userList.get(position);
        Intent intent = new Intent(VehicleTransportReports.this, VehicleTransportReportsItemActivity.class);
        intent.putExtra("vehicle_detail_id", item.getVehicle_detail_id());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
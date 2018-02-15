package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Adapter.VehicleHourlyReportAdapter;
import com.multipz.kc.Model.VehicleHourlyReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleHourlyReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {

    private RecyclerView listviewvehiclehourlyreport;
    private Context mcontex;
    private ArrayList<VehicleHourlyReportModel> list;
    private VehicleHourlyReportAdapter adapter;
    private int lastVisibleItem, firstVisible;
    private boolean userScrolled = true;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_hourly_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_Hourly));
        mcontex = this;
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        getParamforPartywisepayment();
        listviewvehiclehourlyreport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        count++;
                        getParamforPartywisepayment();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void reference() {
        listviewvehiclehourlyreport = (RecyclerView) findViewById(R.id.listviewvehiclehourlyreport);
    }


    private void getParamforPartywisepayment() {
        if (Constant_method.checkConn(mcontex)) {
            String params = "{\"page\":\"" + count + "\",\"action\":\"" + Config.vehicleHourlyReport + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleHourlyReport.this, params, Config.APi_vehicleHourlyReport);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleHourlyReport.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_vehicleHourlyReport) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            VehicleHourlyReportModel model = new VehicleHourlyReportModel();
                            model.setName(c.getString("name"));
                            model.setCompany_id(c.getString("company_id"));
                            model.setAmount(c.getString("amount"));
                            model.setGet_amount(c.getString("get_amount"));
                            model.setActualRemaining(c.getString("actualRemaining"));
                            list.add(model);
                        }
                        adapter = new VehicleHourlyReportAdapter(getApplicationContext(), list);
                        layoutManager = new LinearLayoutManager(this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        listviewvehiclehourlyreport.setLayoutManager(layoutManager);
                        listviewvehiclehourlyreport.setAdapter(adapter);
                        adapter.setClickListener(this);
                    } else {
                        Toast.makeText(mcontex, "Data not found", Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public void itemClicked(View View, int position) {
        VehicleHourlyReportModel model = list.get(position);
        if (View.getId() == R.id.btnRemainingAmt) {
            Intent intent = new Intent(VehicleHourlyReport.this, VehicleRemainingDetailReportActivity.class);
            intent.putExtra("companyid", model.getCompany_id());
            startActivity(intent);
        } else if (View.getId() == R.id.btnGotAmt) {
            Intent intent = new Intent(VehicleHourlyReport.this, VehicleHourlyGotAmtDetailActivity.class);
            intent.putExtra("companyid", model.getCompany_id());
            startActivity(intent);
        }
    }
}

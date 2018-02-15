package com.multipz.kc.Vehicle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Activity.AddVehicleHourlyActivity;
import com.multipz.kc.Adapter.VehicleHourlyAdapter;
import com.multipz.kc.Model.VehicleHourlyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleHourly extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {
    RecyclerView vehicle_hour_list;
    Shared shared;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private boolean userScrolled = true;
    Context context;
    FloatingActionButton fabAdd;
    private int lastVisibleItem, firstVisible;
    private VehicleHourlyAdapter adapter;
    ArrayList<VehicleHourlyModel> userList;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_hourly);
        setTitle(getResources().getString(R.string.Vehicle_Hourly));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        shared = new Shared(context);

        refernce();
        init();
    }

    private void init() {

        vehicle_hour_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        getVehicleHourly();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddVehicleHourlyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userList = new ArrayList<>();
        getVehicleHourly();
    }

    private void getVehicleHourly() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getVehicleHourData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_vehicle_hourly);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void refernce() {

        vehicle_hour_list = (RecyclerView) findViewById(R.id.vehicle_hour_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_Get_vehicle_hourly) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject c = jsonArray.getJSONObject(i1);
                    VehicleHourlyModel vehicleHourlyModel = new VehicleHourlyModel();
                    vehicleHourlyModel.setVehicle_hourly_id(c.getString("vehicle_hourly_id"));
                    vehicleHourlyModel.setVehicle_no(c.getString("vehicle_no"));
                    vehicleHourlyModel.setCompanyName(c.getString("companyName"));
                    vehicleHourlyModel.setStart_time(c.getString("start_time"));
                    vehicleHourlyModel.setEnd_time(c.getString("end_time"));
                    vehicleHourlyModel.setUserName(c.getString("userName"));
                    vehicleHourlyModel.setAmount(c.getString("amount"));

                    vehicleHourlyModel.setVehicle_type(c.getString("vehicle_type"));
                    userList.add(vehicleHourlyModel);
                }
                adapter = new VehicleHourlyAdapter(context, userList);
                layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                vehicle_hour_list.setLayoutManager(layoutManager);
                vehicle_hour_list.setAdapter(adapter);
                adapter.setClickListener(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_Get_vehicle_hourly_delete) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void itemClicked(View View, int position) {
        VehicleHourlyModel item = userList.get(position);
        if (View.getId() == R.id.img_delete) {
            String vehical_hours_id = item.getVehicle_hourly_id();
            delete(vehical_hours_id);
            removeAt(position);
        }
//        } else {
//            Intent intent = new Intent(context, VehicleHourlyDetailActivity.class);
//            intent.putExtra("vehicle_hourly_id", item.getVehicle_hourly_id());
//            intent.putExtra("companyName", item.getCompanyName());
//            intent.putExtra("amount", item.getAmount());
//            intent.putExtra("userName", item.getUserName());
//            intent.putExtra("start_time", item.getStart_time());
//            intent.putExtra("end_time", item.getEnd_time());
//            intent.putExtra("vehicle_no", item.getVehicle_no());
//            intent.putExtra("vehicle_type", item.getVehicle_type());
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        }
    }

    private void delete(String vid) {
        if (Constant_method.checkConn(context)) {
            String param = "{\"id\": \"" + vid + "\",\"optFlag\":\"d\",\"action\": \"editDeleteVehicleHour\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_vehicle_hourly_delete);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeAt(int position) {
        userList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, userList.size());
    }

}

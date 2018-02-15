package com.multipz.kc.Vehicle;

import android.app.Activity;
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

import com.multipz.kc.Activity.AddVehicleHourlyPayActivity;
import com.multipz.kc.Adapter.VehicleHourlyPayAdapter;
import com.multipz.kc.Model.VehicleHourlyPayModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleHourlyPay extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {
    RecyclerView vehicle_hourly_pay_list;
    Shared shared;
    Context context;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    LinearLayoutManager layoutManager;
    private boolean userScrolled = true;
    private int lastVisibleItem, firstVisible;
    private VehicleHourlyPayAdapter adapter;
    ArrayList<VehicleHourlyPayModel> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_hourly_pay);
        setTitle(getResources().getString(R.string.Vehicl_Hourly_Pay));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        shared = new Shared(context);
        refernce();
        init();
    }

    private void init() {


        vehicle_hourly_pay_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        getVehicleHourlyPay();
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
                Intent intent = new Intent(VehicleHourlyPay.this, AddVehicleHourlyPayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    private void getVehicleHourlyPay() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getVehicleHourlyPayData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_vehicle_hourly_pay);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void refernce() {
        vehicle_hourly_pay_list = (RecyclerView) findViewById(R.id.vehicle_hourly_pay_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add_hourly_pay);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onResponseService(String response, int flag) {
        int sucess;
        String msg;
        if (flag == Config.APi_Get_vehicle_hourly_pay) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
//                msg = jsonObject.getString("msg");

                if (sucess == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");


                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleHourlyPayModel hourlyPayModel = new VehicleHourlyPayModel();
                        hourlyPayModel.setTbl_vehicle_hourly_pay_id(c.getString("tbl_vehicle_hourly_pay_id"));
                        hourlyPayModel.setCompany_id(c.getString("company_id"));
                        hourlyPayModel.setBank_id(c.getString("bank_id"));
                        hourlyPayModel.setAmount(c.getString("amount"));
                        hourlyPayModel.setPayment_type(c.getString("payment_type"));
                        hourlyPayModel.setPayment_info(c.getString("payment_info"));
                        hourlyPayModel.setDetail(c.getString("detail"));
                        hourlyPayModel.setDate(c.getString("date"));
                        hourlyPayModel.setName(c.getString("name"));
                        hourlyPayModel.setBank_name(c.getString("bank_name"));
                        userList.add(hourlyPayModel);
                    }
                    adapter = new VehicleHourlyPayAdapter(context, userList);
                    layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    vehicle_hourly_pay_list.setLayoutManager(layoutManager);
                    vehicle_hourly_pay_list.setAdapter(adapter);
                    adapter.setClickListener(this);
                }
//                else
//                {
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_vehicle_hourly_pay_Delete) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (sucess == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void delete(String id) {
        if (Constant_method.checkConn(context)) {
            String param = "{\"tbl_vehicle_hourly_pay_id\":\"" + id + "\",\"action\":\"vehicleHourlyPayDelete\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, (Activity) context, param, Config.APi_vehicle_hourly_pay_Delete);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        getVehicleHourlyPay();
    }

    @Override
    public void itemClicked(View View, int position) {
        VehicleHourlyPayModel data = userList.get(position);
        if (View.getId() == R.id.img_tra) {
            delete(data.getTbl_vehicle_hourly_pay_id());
            remove(position);
        }

    }

    private void remove(int pos) {
        userList.remove(pos);
        adapter.notifyItemRemoved(pos);
        adapter.notifyItemRangeChanged(pos, userList.size());
    }
}

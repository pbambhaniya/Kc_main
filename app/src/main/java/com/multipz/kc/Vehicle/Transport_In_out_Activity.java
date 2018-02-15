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
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Activity.AddTransportInOutActivity;
import com.multipz.kc.Adapter.TransPortInOutAdapter;
import com.multipz.kc.Model.TransPortInOutModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Transport_In_out_Activity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {
    RecyclerView vehicle_hourly_pay_list;
    Shared shared;
    Context context;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    FloatingActionButton fabAdd;
    private TransPortInOutAdapter adapter;
    ArrayList<TransPortInOutModel> userList;
    private int lastVisibleItem, firstVisible;
    private boolean userScrolled = true;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport__in_out_);
        setTitle(getResources().getString(R.string.Transport_In_Out));
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
                        getVehicleVehicleTransportPay();
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
                Intent intent = new Intent(Transport_In_out_Activity.this, AddTransportInOutActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getVehicleVehicleTransportPay() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"search\":\"\",\"action\":\"manageVehicleTransPayInOutgetData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_vehicle_In_Out);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void refernce() {
        vehicle_hourly_pay_list = (RecyclerView) findViewById(R.id.vehicle_hourly_pay_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
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
        if (flag == Config.APi_Get_vehicle_In_Out) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
//                msg = jsonObject.getString("msg");
                if (sucess == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        TransPortInOutModel transPortInOutModel = new TransPortInOutModel();
                        transPortInOutModel.setCheque_no(c.getString("cheque_no"));
                        transPortInOutModel.setPayment_type(c.getString("payment_type"));
                        transPortInOutModel.setTbl_master_payment_in_out_id(c.getString("tbl_master_payment_in_out_id"));
                        transPortInOutModel.setType(c.getString("type"));
                        transPortInOutModel.setAmount(c.getString("amount"));
                        transPortInOutModel.setName(c.getString("name"));

                        userList.add(transPortInOutModel);
                    }
                    adapter = new TransPortInOutAdapter(context, userList);
                    layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    vehicle_hourly_pay_list.setLayoutManager(layoutManager);
                    vehicle_hourly_pay_list.setAdapter(adapter);
                    adapter.setClickListener(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (flag == Config.APi_vehicle_in_out_delete) {
            try {
                JSONObject jsonObjectt = new JSONObject(response);
                Log.e("Responce", jsonObjectt.toString());
                sucess = jsonObjectt.getInt("status");
                msg = jsonObjectt.getString("msg");
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
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        getVehicleVehicleTransportPay();
    }

    @Override
    public void itemClicked(View View, int position) {
        TransPortInOutModel model = userList.get(position);
        if (View.getId() == R.id.img_tra) {
            String id = model.getTbl_master_payment_in_out_id();
            deleteTransportItem(id);
            removeAt(position);
        }

    }

    private void deleteTransportItem(String id) {
        if (Constant_method.checkConn(context)) {
            String param = "{\"pid\":\"" + id + "\",\"action\":\"deleteTransportpartypayment\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, (Activity) context, param, Config.APi_vehicle_in_out_delete);
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

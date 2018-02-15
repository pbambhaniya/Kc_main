package com.multipz.kc.Vehicle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Activity.AddVehicleDetailActivity;
import com.multipz.kc.Adapter.VehicleDetailsAdapter;
import com.multipz.kc.Details.VehicleListDetailActivity;
import com.multipz.kc.Model.VehicleModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleList extends AppCompatActivity {

    ListView vehicle_list;
    Shared shared;
    Context context;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;

    private VehicleDetailsAdapter adapter;
    ArrayList<VehicleModel> userList;

    private void callServiceResponse() {

        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                if (flag == Config.API_TOKEN_GET_VEHICLE) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        userList = new ArrayList<>();

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);

                            VehicleModel vehicleModel = new VehicleModel();
                            vehicleModel.setVehicle_detail_id(c.getString("vehicle_detail_id"));
                            vehicleModel.setVehicle_no(c.getString("vehicle_no"));
                            vehicleModel.setCdate(c.getString("cdate"));
                            vehicleModel.setVehicle_type(c.getString("vehicle_type"));
                            userList.add(vehicleModel);
                        }

                        adapter = new VehicleDetailsAdapter(context, userList);
                        vehicle_list.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }


        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list);

        setTitle(getResources().getString(R.string.Vehicle_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActivity().setTitle("Vehicle Detail");
        callServiceResponse();
        context = this;
        shared = new Shared(context);


        vehicle_list = (ListView) findViewById(R.id.vehicle_details_list);

        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getVehicleDetailData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_VEHICLE);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        vehicle_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VehicleModel item = userList.get(i);
                Intent intent = new Intent(context, VehicleListDetailActivity.class);
                intent.putExtra("vehicle_type", item.getVehicle_type());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("vehicle_detail_id", item.getVehicle_detail_id());
                intent.putExtra("vehicle_no", item.getVehicle_no());

                startActivity(intent);
            }
        });


        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddVehicleDetailActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        String param = "{\"action\":\"getVehicleDetailData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_VEHICLE);
        myAsyncTask.execute();
    }
}

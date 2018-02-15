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

import com.multipz.kc.Activity.AddVehicleTypeActivity;
import com.multipz.kc.Adapter.VehicleTypeAdapter;
import com.multipz.kc.Details.VehicleTypeDetailActivity;
import com.multipz.kc.Model.VehicleTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleType extends AppCompatActivity {
    Shared shared;
    private VehicleTypeAdapter adapter;
    ListView vehicle_type_list;
    Context context;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    private ArrayList<VehicleTypeModel> userList;


    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                int success;
                String msg;

                if (flag == Config.API_TOKEN_GET_VEHICLE_TYPE) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        success = jsonObject.getInt("status");
                        msg = jsonObject.getString("msg");

                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            userList = new ArrayList<>();
                            for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                                JSONObject c = jsonArray.getJSONObject(i1);
                                VehicleTypeModel vehicleTypeModel = new VehicleTypeModel();
                                vehicleTypeModel.setVehicle_type_id(c.getString("vehicle_type_id"));
                                vehicleTypeModel.setCdate(c.getString("cdate"));
                                vehicleTypeModel.setVehicle_type(c.getString("vehicle_type"));
                                userList.add(vehicleTypeModel);
                            }
                            adapter = new VehicleTypeAdapter(context, userList);
                            vehicle_type_list.setAdapter(adapter);
                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }

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
        setContentView(R.layout.activity_vehicle_type);


        setTitle(getResources().getString(R.string.vehicle_type));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActivity().setTitle("Vehicle Type");
        context = this;
        shared = new Shared(context);

        vehicle_type_list = (ListView) findViewById(R.id.vehicle_type_list);
        vehicle_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, VehicleTypeDetailActivity.class);
                VehicleTypeModel item = userList.get(i);
                intent.putExtra("vehicle_type", item.getVehicle_type());
                intent.putExtra("vehicle_type_id", item.getVehicle_type_id());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("created_date", item.getCreated_date());
                startActivity(intent);
            }
        });
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddVehicleTypeActivity.class);
                startActivity(intent);
            }
        });

        callServiceResponse();
        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getVehicleTypeData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_VEHICLE_TYPE);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        menu.clear();
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_edit:
                Intent intent = new Intent(context, Add_Vehicle_Type_Activity.class);
                startActivity(intent);

                return false;


            default:
                break;
        }

        return false;
    }*/

    @Override
    public void onResume() {
        super.onResume();

        String param = "{\"action\":\"getVehicleTypeData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_VEHICLE_TYPE);
        myAsyncTask.execute();
    }

}

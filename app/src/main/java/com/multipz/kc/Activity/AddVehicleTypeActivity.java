package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Model.VehicleTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddVehicleTypeActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText vehicle_type;
    Button add_vehicle_type, update_vehicle_type, delete_vehicle_type;
    Context context;
    String id;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_type);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.vehicle_type));
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vehicle_type = (EditText) findViewById(R.id.vehicle_type_name);
        add_vehicle_type = (Button) findViewById(R.id.add_vehicle);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        update_vehicle_type = (Button) findViewById(R.id.update_vehicle);
        delete_vehicle_type = (Button) findViewById(R.id.delete_vehicle);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);

        if (getIntent().getBooleanExtra("Update", false)) {
            vehicle_type.setText(getIntent().getExtras().getString("vehicle_type"));
            id = getIntent().getStringExtra("vehicle_type_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            add_vehicle_type.setVisibility(View.GONE);
            update_vehicle_type.setVisibility(View.VISIBLE);
        }

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddVehicleTypeActivity.this, txt_start_date);
            }
        });
        add_vehicle_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String evehicle, date;

                evehicle = vehicle_type.getText().toString();
                date = txt_start_date.getText().toString();
                if (evehicle.contentEquals("")) {
                    Toast.makeText(AddVehicleTypeActivity.this, "Enter Vehicle Name", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {

                    String param = "{\"id\":\"\",\"vehicle_type\":\"" + evehicle + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateVehicleType\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleTypeActivity.this, param, Config.API_TOKEN_ADD_VEHICLE_TYPE);
                    myAsyncTask.execute();
//                    Intent vehicle_type=new Intent(Add_Vehicle_Type_Activity.this,VehicleTypeActivity.class);
//                    startActivity(vehicle_type);
                }
            }
        });

        update_vehicle_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String evehicle,date;

                date = txt_start_date.getText().toString();
                evehicle = vehicle_type.getText().toString();
                if (evehicle.contentEquals("")) {
                    Toast.makeText(AddVehicleTypeActivity.this, "Enter Vehicle Name", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                }

                else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"vehicle_type\":\"" + evehicle + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateVehicleType\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleTypeActivity.this, param, Config.API_TOKEN_UPDATE_VEHICLE_TYPE);
                    myAsyncTask.execute();
                }
            }
        });

        delete_vehicle_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"1\",\"optFlag\":\"d\",\"action\":\"editDeleteVehicleType\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleTypeActivity.this, param, Config.API_TOKEN_DELETE_VEHICLE_TYPE);
                    myAsyncTask.execute();
                }
            }
        });
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_TOKEN_ADD_VEHICLE_TYPE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    vehicle_type.setText("");
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_UPDATE_VEHICLE_TYPE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    ArrayList<VehicleTypeModel> userList = new ArrayList<>();
                    VehicleTypeModel vehicleType = new VehicleTypeModel();
                    vehicleType.setVehicle_type(jsonObject1.getString("vehicle_type"));
                    userList.add(vehicleType);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_DELETE_VEHICLE_TYPE) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.VehicleModel;
import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class AddVehicleDetailActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText vehicle_no;
    Spinner spin_vehicle_type;
    Button add_vehicle, update_vehicle;
    Shared shared;
    Context context;
    String id;
    RelativeLayout rl_start_dates;

    TextView txt_start_date;
    private ArrayList<SpinnerModel> objects_vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_detail));
        context = this;
        shared = new Shared(context);

        new CallConfigDataApi(shared).execute();
        vehicle_no = (EditText) findViewById(R.id.vehicle_no);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        spin_vehicle_type = (Spinner) findViewById(R.id.spin_vehicle_type);


        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddVehicleDetailActivity.this, txt_start_date);
            }
        });

        objects_vehicle = new ArrayList<>();
        objects_vehicle.add(new SpinnerModel("", getResources().getString(R.string.vehicle_type)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.VehicleType, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);

                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_type_id"));
                spinnerModel.setName(staff.getString("vehicle_type"));
                objects_vehicle.add(spinnerModel);
            }
            spin_vehicle_type.setAdapter(new SpinnerAdapter(this, objects_vehicle));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        add_vehicle = (Button) findViewById(R.id.add_vehicle);
        update_vehicle = (Button) findViewById(R.id.update_vehicle);

        if (getIntent().getBooleanExtra("Update", false)) {
            vehicle_no.setText(getIntent().getExtras().getString("vehicle_no"));
            setSpinner(spin_vehicle_type, objects_vehicle, getIntent().getStringExtra("vehicle_type"));
            id = getIntent().getStringExtra("vehicle_detail_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            add_vehicle.setVisibility(View.GONE);
            update_vehicle.setVisibility(View.VISIBLE);
        }


        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String evehicle_no, evehicle_name, date;

                date = txt_start_date.getText().toString();
                evehicle_no = vehicle_no.getText().toString();
                evehicle_name = objects_vehicle.get(spin_vehicle_type.getSelectedItemPosition()).getid();

                if (evehicle_no.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle No", Toast.LENGTH_SHORT).show();
                } else if (evehicle_name.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Type", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"vehicle_no\":\"" + evehicle_no + "\",\"vehicle_type_id\":\"" + evehicle_name + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateVehicleDetail\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleDetailActivity.this, param, Config.API_TOKEN_ADD_VEHICLE);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        update_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String evehicle_no, evehicle_name, date;

                date = txt_start_date.getText().toString();
                evehicle_no = vehicle_no.getText().toString();
                evehicle_name = objects_vehicle.get(spin_vehicle_type.getSelectedItemPosition()).getid();

                if (evehicle_no.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle No", Toast.LENGTH_SHORT).show();
                } else if (evehicle_name.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Type", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"vehicle_no\":\"" + evehicle_no + "\",\"vehicle_type_id\":\"" + evehicle_name + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateVehicleDetail\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleDetailActivity.this, param, Config.API_TOKEN_UPDATE_VEHICLE);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setSpinner(Spinner spin_vehicle_type, ArrayList<SpinnerModel> objects_vehicle, String vehicle_type) {
        for (int i = 0; i < objects_vehicle.size(); i++) {
            if (vehicle_type.contentEquals(objects_vehicle.get(i).getName())) {
                spin_vehicle_type.setSelection(i);
                break;
            }
        }
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
        if (flag == Config.API_TOKEN_ADD_VEHICLE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    vehicle_no.setText("");
                    spin_vehicle_type.setSelection(0);
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_UPDATE_VEHICLE) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    ArrayList<VehicleModel> userList = new ArrayList<>();

                    VehicleModel vehicle = new VehicleModel();
                    vehicle.setVehicle_no(jsonObject1.getString("vehicle_no"));
                    vehicle.setVehicle_type(jsonObject1.getString("vehicle_type"));
                    userList.add(vehicle);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();

                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

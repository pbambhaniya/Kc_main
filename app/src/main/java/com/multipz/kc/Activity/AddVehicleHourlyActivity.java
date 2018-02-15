package com.multipz.kc.Activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.VehicleHourlyModel;
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

import java.util.ArrayList;
import java.util.Calendar;

public class AddVehicleHourlyActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private int mYear, mMonth, mDay;
    Button add_vehicle, update_vehicle;
    Shared shared;
    Context context;
    String id;
    private int hour, minutes;
    TextView txt_start_date, txt_end_date, txt_s_date;
    Spinner sp_vehicle_no, sp_user, sp_company, sp_vehicle_type;
    EditText et_amount, et_hour_pay, et_hours;
    ArrayList<SpinnerModel> objects_vehicle_no, object_user, object_company, objects_vehicle_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_hourly);

        context = this;
        shared = new Shared(context);
        new CallConfigDataApi(shared).execute();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_Hourly));

        refrence();
        init();


    }

    private void init() {
        getVehicleType();
        getCompanyName();
        getUserName();
        getVehicleNo();
        updateVehicleHourly();
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minutes = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddVehicleHourlyActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                txt_start_date.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minutes, false);
                timePickerDialog.show();
            }
        });

        txt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minutes = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddVehicleHourlyActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txt_end_date.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minutes, false);
                timePickerDialog.show();
            }
        });


        txt_s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddVehicleHourlyActivity.this, txt_s_date);
            }
        });


        sp_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                objects_vehicle_no.clear();
                objects_vehicle_no.add(new SpinnerModel("", getResources().getString(R.string.SElect_Vehicle_No)));
                try {
                    JSONArray jsonArray = new JSONArray(shared.getString(Config.Vehicle, "[{}]"));
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject staff = jsonArray.getJSONObject(j);
                        if (staff.getString("vehicle_type_id").contentEquals(objects_vehicle_type.get(i).getid())) {
                            SpinnerModel spinnerModel = new SpinnerModel();
                            spinnerModel.setid(staff.getString("vehicle_detail_id"));
                            spinnerModel.setName(staff.getString("vehicle_no"));
                            objects_vehicle_no.add(spinnerModel);
                        }
                    }
                    sp_vehicle_no.setAdapter(new SpinnerAdapter(AddVehicleHourlyActivity.this, objects_vehicle_no));
                    try {
                        setSpinnerVType(sp_vehicle_no, objects_vehicle_no, getIntent().getStringExtra("vehicle_no").trim());
                    } catch (Exception e) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, vehicle, company, amount, sdate, edate, date, hp, th;
                amount = et_amount.getText().toString();
                th = et_hours.getText().toString();
                hp = et_hour_pay.getText().toString();
                sdate = txt_start_date.getText().toString();
                edate = txt_end_date.getText().toString();
                date = txt_s_date.getText().toString();
                user = object_user.get(sp_user.getSelectedItemPosition()).getid();
                vehicle = objects_vehicle_no.get(sp_vehicle_type.getSelectedItemPosition()).getid();
                company = object_company.get(sp_company.getSelectedItemPosition()).getid();

                if (vehicle.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle No", Toast.LENGTH_SHORT).show();
                } else if (user.contentEquals("")) {
                    Toast.makeText(context, "Enter User Name", Toast.LENGTH_SHORT).show();
                } else if (company.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (sdate.contentEquals("") && edate.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (th.contentEquals("")) {
                    Toast.makeText(context, "Enter Total Hour", Toast.LENGTH_SHORT).show();
                } else if (hp.contentEquals("")) {
                    Toast.makeText(context, "Enter amount per hour", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"vehicle_detail_id\": \"" + vehicle + "\",\"user_id\": \"" + user + "\",\"company_id\": \"" + company + "\",\"start_time\": \"" + sdate + "\",\"end_time\": \"" + edate + "\",\"amount\": \"" + amount + "\",\"cdate\": \"" + date + "\",\"th\": \"" + th + "\",\"hp\": \"" + hp + "\",\"action\": \"addUpdateVehicleHour\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleHourlyActivity.this, param, Config.APi_Get_vehicle_hourly_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        update_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, vehicle, company, amount, sdate, edate;
                amount = et_amount.getText().toString();
                sdate = txt_start_date.getText().toString();
                edate = txt_end_date.getText().toString();

                user = object_user.get(sp_user.getSelectedItemPosition()).getid();
                vehicle = objects_vehicle_no.get(sp_vehicle_type.getSelectedItemPosition()).getid();
                company = object_company.get(sp_company.getSelectedItemPosition()).getid();

                if (vehicle.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle No", Toast.LENGTH_SHORT).show();
                } else if (user.contentEquals("")) {
                    Toast.makeText(context, "Enter User Name", Toast.LENGTH_SHORT).show();
                } else if (company.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (sdate.contentEquals("") && edate.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"" + id + "\",\"vehicle_detail_id\": \"" + vehicle + "\",\"user_id\": \"" + user + "\",\"company_id\": \"" + company + "\",\"start_time\": \"" + sdate + "\",\"end_time\": \"" + edate + "\",\"amount\": \"" + amount + "\",\"action\": \"addUpdateVehicleHour\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleHourlyActivity.this, param, Config.APi_Get_vehicle_hourly_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }

        });


    }

    private void updateVehicleHourly() {
        if (getIntent().getBooleanExtra("Update", false)) {
            id = getIntent().getStringExtra("vehicle_hourly_id");
            setSpinnerVno(sp_vehicle_type, objects_vehicle_type, getIntent().getStringExtra("vehicle_type"));
            setSpinner(sp_company, object_company, getIntent().getStringExtra("companyName"));
            setSpinner(sp_user, object_user, getIntent().getStringExtra("userName"));
            txt_start_date.setText(getIntent().getStringExtra("start_time"));
            txt_end_date.setText(getIntent().getStringExtra("end_time"));
            et_amount.setText(getIntent().getExtras().getString("amount"));
            add_vehicle.setVisibility(View.GONE);
            update_vehicle.setVisibility(View.VISIBLE);
        }
    }

    private void getVehicleNo() {
        objects_vehicle_no = new ArrayList<>();
        objects_vehicle_no.add(new SpinnerModel("", getResources().getString(R.string.SElect_Vehicle_No)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Vehicle, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_detail_id"));
                spinnerModel.setName(staff.getString("vehicle_no"));
                objects_vehicle_no.add(spinnerModel);
            }
            sp_vehicle_no.setAdapter(new SpinnerAdapter(this, objects_vehicle_no));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getUserName() {
        object_user = new ArrayList<>();
        object_user.add(new SpinnerModel("", getResources().getString(R.string.Select_Name)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Staff, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("user_id"));
//                String name = staff.getString("name");
//                String type = staff.getString("work_type");
//                String val = name + "(" + type + ")" ;
                spinnerModel.setName(staff.getString("name"));
                object_user.add(spinnerModel);
            }
            sp_user.setAdapter(new SpinnerAdapter(this, object_user));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVehicleType() {
        objects_vehicle_type = new ArrayList<>();
        objects_vehicle_type.add(new SpinnerModel("", getResources().getString(R.string.Select_VEhicle_Type)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.VehicleType, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_type_id"));
                spinnerModel.setName(staff.getString("vehicle_type"));
                objects_vehicle_type.add(spinnerModel);
            }
            sp_vehicle_type.setAdapter(new SpinnerAdapter(this, objects_vehicle_type));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getCompanyName() {
        object_company = new ArrayList<>();
        object_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                object_company.add(spinnerModel);
            }
            sp_company.setAdapter(new SpinnerAdapter(this, object_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refrence() {
        sp_vehicle_no = (Spinner) findViewById(R.id.sp_vehicle_no);
        sp_vehicle_type = (Spinner) findViewById(R.id.sp_vehicle_type);
        sp_user = (Spinner) findViewById(R.id.sp_user);
        sp_company = (Spinner) findViewById(R.id.sp_company);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_hour_pay = (EditText) findViewById(R.id.et_hour_pay);
        et_hours = (EditText) findViewById(R.id.et_hours);
        add_vehicle = (Button) findViewById(R.id.add_vehicle);
        update_vehicle = (Button) findViewById(R.id.update_vehicle);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        txt_end_date = (TextView) findViewById(R.id.txt_end_date);
        txt_s_date = (TextView) findViewById(R.id.txt_s_date);
    }

    private void setSpinner(Spinner spin_vehicle_type, ArrayList<SpinnerModel> objects_vehicle, String vehicle_type) {
        for (int i = 0; i < objects_vehicle.size(); i++) {
            if (vehicle_type.contentEquals(objects_vehicle.get(i).getName())) {
                spin_vehicle_type.setSelection(i);
                break;
            }
        }
    }


    private void setSpinnerVno(Spinner spin_vehicle_type, ArrayList<SpinnerModel> objects_vehicle, String vehicle_type) {
        for (int i = 0; i < objects_vehicle.size(); i++) {
            if (vehicle_type.contentEquals(objects_vehicle.get(i).getName().trim())) {
                spin_vehicle_type.setSelection(i);
                break;
            }
        }
    }

    private void setSpinnerVType(Spinner spin_vehicle_type, ArrayList<SpinnerModel> objects_vehicle, String vehicle_type) {
        for (int i = 0; i < objects_vehicle.size(); i++) {
            if (vehicle_type.contentEquals(objects_vehicle.get(i).getName().trim())) {
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
        if (flag == Config.APi_Get_vehicle_hourly_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    sp_user.setSelection(0);
                    sp_company.setSelection(0);
                    sp_vehicle_no.setSelection(0);
                    sp_vehicle_type.setSelection(0);
                    et_amount.setText("");
                    txt_start_date.setText("");
                    txt_end_date.setText("");
                    txt_s_date.setText("");
                    et_hour_pay.setText("");
                    et_hours.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_Get_vehicle_hourly_Update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    ArrayList<VehicleHourlyModel> userList = new ArrayList<>();

                    VehicleHourlyModel vehicleHourly = new VehicleHourlyModel();
                    vehicleHourly.setVehicle_hourly_id(jsonObject1.getString("vehicle_hourly_id"));
                    vehicleHourly.setVehicle_detail_id(jsonObject1.getString("vehicle_detail_id"));
                    vehicleHourly.setUser_id(jsonObject1.getString("user_id"));
                    vehicleHourly.setCompany_id(jsonObject1.getString("company_id"));
                    vehicleHourly.setStart_time(jsonObject1.getString("start_time"));
                    vehicleHourly.setAmount(jsonObject1.getString("amount"));
                    vehicleHourly.setCompanyName(jsonObject1.getString("companyName"));
                    vehicleHourly.setUserName(jsonObject1.getString("userName"));
                    vehicleHourly.setVehicle_no(jsonObject1.getString("vehicle_no"));
                    vehicleHourly.setVehicle_type(jsonObject1.getString("vehicle_type"));

                    userList.add(vehicleHourly);
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

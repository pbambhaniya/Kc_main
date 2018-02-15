package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.VehicleGeneralExpenseModel;
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

public class AddVehicleGeneralExpenseActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    Button btn_add, btn_update;
    Spinner sp_vehicle_no, sp_vehicle_type, sp_exp_type;
    Shared shared;
    Context context;
    String id;
    private ArrayList<SpinnerModel> objects_vehicle_type, objects_vehicle_no, object_exp_type;
    public ArrayList<VehicleGeneralExpenseModel> userList;
    EditText et_detail, et_amount;
    TextView txt_start_date;
    RelativeLayout rl_start_dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_general_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.vehicle_general_expense));
        context = this;
        shared = new Shared(context);
        new CallConfigDataApi(shared).execute();

        refrence();
        init();


    }

    private void init() {

        getVehicleType();
        getVehicleNo();
        object_exp_type = new ArrayList<>();
        object_exp_type.add(new SpinnerModel("", getString(R.string.Exp_type)));
        object_exp_type.add(new SpinnerModel("Digsel", "Digsel"));

        object_exp_type.add(new SpinnerModel("Maintenanc", "Maintenanc"));
        sp_exp_type.setAdapter(new SpinnerAdapter(this, object_exp_type));


        updateVehicleExpense();

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddVehicleGeneralExpenseActivity.this, txt_start_date);
            }
        });
        sp_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(context, ""+i, Toast.LENGTH_SHORT).show();
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
                    sp_vehicle_no.setAdapter(new SpinnerAdapter(AddVehicleGeneralExpenseActivity.this, objects_vehicle_no));
                    try {
                        setSpinner(sp_vehicle_no, objects_vehicle_no, getIntent().getStringExtra("vehicle_no").trim());
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
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edetail, eamount, spinner, date, exp_type;
                date = txt_start_date.getText().toString();
                edetail = et_detail.getText().toString();
                eamount = et_amount.getText().toString();
                exp_type = object_exp_type.get(sp_exp_type.getSelectedItemPosition()).getName();
                spinner = objects_vehicle_no.get(sp_vehicle_type.getSelectedItemPosition()).getid();
                if (spinner.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Name", Toast.LENGTH_SHORT).show();
                } else if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Detail", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Amount", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"user_id\": \"" + shared.getUserId().toString() + "\",\"vehicle_detail_id\": \"" + spinner + "\",\"etype\": \"" + exp_type + "\",\"detail\": \"" + edetail + "\",\"amount\": \"" + eamount + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateVehicleGenExp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleGeneralExpenseActivity.this, param, Config.APi_vehicle_general_Expense_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edetail, eamount, spinner, date,exp_type;
                edetail = et_detail.getText().toString();
                eamount = et_amount.getText().toString();
                date = txt_start_date.getText().toString();
                exp_type = object_exp_type.get(sp_exp_type.getSelectedItemPosition()).getName();
                spinner = objects_vehicle_no.get(sp_vehicle_type.getSelectedItemPosition()).getid();
                if (spinner.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Name", Toast.LENGTH_SHORT).show();
                } else if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Detail", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Vehicle Amount", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"" + id + "\",\"user_id\": \"" + shared.getUserId().toString() + "\",\"vehicle_detail_id\": \"" + spinner + "\",\"etype\": \"" + exp_type + "\",\"detail\": \"" + edetail + "\",\"amount\": \"" + eamount + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateVehicleGenExp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleGeneralExpenseActivity.this, param, Config.APi_vehicle_general_Expense_Update);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateVehicleExpense() {
        if (getIntent().getBooleanExtra("Update", false)) {
            try {
                id = getIntent().getStringExtra("vehicle_gen_exp_id");
                et_detail.setText(getIntent().getStringExtra("detail"));
                et_amount.setText(getIntent().getStringExtra("amount"));
                setSpinner(sp_vehicle_type, objects_vehicle_type, getIntent().getStringExtra("vehicle_type").trim());
                txt_start_date.setText(getIntent().getStringExtra("date"));
                setSpinner(sp_exp_type, object_exp_type, getIntent().getStringExtra("exp_type").trim());
                //setSpinner(sp_vehicle_no, objects_vehicle_detail, getIntent().getStringExtra("vehicle_no").trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
//            String vehical_no = getIntent().getStringExtra("vehicle_no");
//            sp_vehicle_no.setSelection(objects_vehicle_detail.indexOf(vehical_no));
//            setSpinner(sp_vehicle_no, objects_vehicle_detail, vehical_no);
            btn_add.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);
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

    private void refrence() {
        et_detail = (EditText) findViewById(R.id.et_detail);
        sp_exp_type = (Spinner) findViewById(R.id.sp_exp_type);
        et_amount = (EditText) findViewById(R.id.et_amount);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        sp_vehicle_no = (Spinner) findViewById(R.id.sp_vehicle_no);
        sp_vehicle_type = (Spinner) findViewById(R.id.sp_vehicle_type);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_update = (Button) findViewById(R.id.btn_update);
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
        if (flag == Config.APi_vehicle_general_Expense_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    sp_vehicle_type.setSelection(0);
                    et_amount.setText("");
                    et_detail.setText("");
                    sp_exp_type.setSelection(0);
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
        } else if (flag == Config.APi_vehicle_general_Expense_Update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject c = jsonObject.getJSONObject("data");
                    userList = new ArrayList<>();
                    VehicleGeneralExpenseModel vehicleGeneralExpense = new VehicleGeneralExpenseModel();
                    vehicleGeneralExpense.setVehicle_gen_exp_id(c.getString("vehicle_gen_exp_id"));
                    vehicleGeneralExpense.setVehicle_detail_id(c.getString("vehicle_detail_id"));
                    vehicleGeneralExpense.setUser_id(c.getString("user_id"));
                    vehicleGeneralExpense.setDetail(c.getString("detail"));
                    vehicleGeneralExpense.setAmount(c.getString("amount"));
                    vehicleGeneralExpense.setCdate(c.getString("cdate"));
                    vehicleGeneralExpense.setEtype(c.getString("etype"));
                    vehicleGeneralExpense.setVehicle_no(c.getString("vehicle_no"));
                    userList.add(vehicleGeneralExpense);
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

    private void setSpinner(Spinner spin_vehicle_type, ArrayList<SpinnerModel> objects_vehicle, String vehicle_type) {
        for (int i = 0; i < objects_vehicle.size(); i++) {
            if (vehicle_type.contentEquals(objects_vehicle.get(i).getName())) {
                spin_vehicle_type.setSelection(i);
                break;
            }
        }
    }
}

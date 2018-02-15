package com.multipz.kc.ManageStaffAttendance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.StaffAttendanceBaseTypeAdapter;
import com.multipz.kc.Model.GetStaffAsPerTypeModel;

import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StaffAttendanceActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener, ItemClickListener {

    private Spinner sp_staff_attendance_type;
    private RecyclerView listviewstaffattendance;
    private Button btn_submit_attendance;
    private ArrayList<GetStaffAsPerTypeModel> listAttendance;
    private StaffAttendanceBaseTypeAdapter adapter;
    Context context;
    Shared shared;
    private String staffData, userId, amount,date,cid;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_attendance);
        context = this;
        shared = new Shared(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("સ્ટાફની હાજરી");


        reference();
        init();

    }

    private void init() {
        addAttendaceBaseType();
        Intent i = getIntent();
        staffData = i.getStringExtra("staffData");
        userId = i.getStringExtra("userId");
        amount = i.getStringExtra("amount");
        date=i.getStringExtra("date");
        cid=i.getStringExtra("cid");
        if (staffData != null && staffData.contentEquals("staffData")) {
            getstaffAttendance();
        }
    }

    private void getstaffAttendance() {

        String param = null;
        String dates = txt_start_date.getText().toString();
        {
            if (shared.getSalaryBaseType().matches("F")) {
                param = "{\"data\":[{\"user_id\":\"" + userId + "\",\"company_id\": \"\",\"attd_date\":\"" + date + "\",\"attendance\": \"\",\"amount\":\"\"}],\"action\":\"" + Config.staffAttendance + "\"}";
            } else if (shared.getSalaryBaseType().matches("D")) {
                param = "{\"data\":[{\"user_id\":\"" + userId + "\",\"company_id\": \""+cid+"\",\"attd_date\":\"" + date + "\",\"attendance\": \"\",\"amount\":\""+amount+"\"}],\"action\":\"" + Config.staffAttendance + "\"}";
            }
            if (Constant_method.checkConn(context)) {
                MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, StaffAttendanceActivity.this, param, Config.APi_staffAttendance);
                myAsyncTask.execute();
            } else {
                Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void getparamStaffAsPerType() {
        String date = txt_start_date.getText().toString();
        if (Constant_method.checkConn(context)) {
            String param = "{\"salary_based_type\":\"" + shared.getSalaryBaseType() + "\",\"action\":\"" + Config.getStaffAsPerType + "\",\"date\":\"" + date + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, StaffAttendanceActivity.this, param, Config.APi_getStaffAsPerType);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void reference() {
        sp_staff_attendance_type = (Spinner) findViewById(R.id.sp_staff_attendance_type);
        listviewstaffattendance = (RecyclerView) findViewById(R.id.listviewstaffattendance);
        btn_submit_attendance = (Button) findViewById(R.id.btn_submit_attendance);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        sp_staff_attendance_type.setOnItemSelectedListener(this);

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(StaffAttendanceActivity.this, txt_start_date);
            }
        });
    }

    private void addAttendaceBaseType() {

        List<String> list = new ArrayList<String>();
        list.add("Select Base Type");
        list.add("Fix");
        list.add("Daily Base Type");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.company, list);
        sp_staff_attendance_type.setAdapter(adapter);

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
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_getStaffAsPerType) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                GetStaffAsPerTypeModel getStaffAsPerTypeModel = null;
                if (success == 1) {
                    JSONArray array = jsonObject.getJSONArray("data");
                    listAttendance = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        getStaffAsPerTypeModel = new GetStaffAsPerTypeModel();
                        JSONObject obj = array.getJSONObject(i);
                        getStaffAsPerTypeModel.setUser_id(obj.getString("user_id"));
                        getStaffAsPerTypeModel.setName(obj.getString("name"));
                        getStaffAsPerTypeModel.setWork_type_id(obj.getString("work_type_id"));
                        getStaffAsPerTypeModel.setMobile(obj.getString("mobile"));
                        getStaffAsPerTypeModel.setProfile_img(obj.getString("profile_img"));
                        getStaffAsPerTypeModel.setProof_img(obj.getString("proof_img"));
                        getStaffAsPerTypeModel.setPassword(obj.getString("password"));
                        getStaffAsPerTypeModel.setUser_type(obj.getString("user_type"));
                        getStaffAsPerTypeModel.setSalary(obj.getString("salary"));
                        getStaffAsPerTypeModel.setCreated_date(obj.getString("created_date"));
                        getStaffAsPerTypeModel.setModified_date(obj.getString("modified_date"));
                        getStaffAsPerTypeModel.setIs_status(obj.getString("is_status"));
                        getStaffAsPerTypeModel.setIs_delete(obj.getString("is_delete"));
                        listAttendance.add(getStaffAsPerTypeModel);
                    }
                    adapter = new StaffAttendanceBaseTypeAdapter(StaffAttendanceActivity.this, listAttendance);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    listviewstaffattendance.setLayoutManager(layoutManager);
                    listviewstaffattendance.setAdapter(adapter);

                } else {
                    Toast.makeText(context, "Date Not found", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_staffAttendance) {
            try {
                JSONObject object = new JSONObject(response);
                success = object.getInt("status");
                msg = object.getString("msg");
                if (success == 1) {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(StaffAttendanceActivity.this, showStaffAttendanceActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (!adapterView.getItemAtPosition(i).toString().equals("Select Base Type")) {
            if (adapterView.getItemAtPosition(i).toString().equals("Fix")) {
                shared.setSalaryBaseType("F");
                getparamStaffAsPerType();
            } else if (adapterView.getItemAtPosition(i).toString().equals("Daily Base Type")) {
                shared.setSalaryBaseType("D");
                getparamStaffAsPerType();
            }

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void itemClicked(View View, int position) {

    }


}

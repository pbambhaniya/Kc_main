package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
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

public class AddVehicleHourlyPayActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {
    Spinner sp_payment_type, sp_company, sp_bank_id;
    EditText et_amount, et_pay_info, et_detail;
    ArrayList<SpinnerModel> object_company, object_payment_type, objects_bank;
    Shared shared;
    Context context;
    RelativeLayout rel_detail;
    Button add_project, update_project, delete_project;
    TextView txt_start_date;
    RelativeLayout rl_start_dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_hourly_pay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicl_Hourly_Pay));
        context = this;
        shared = new Shared(context);
        new CallConfigDataApi(shared).execute();
        refrence();
        init();
    }


    private void init() {
        getCompanyName();
        getPaymentType();
        getBankName();

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddVehicleHourlyPayActivity.this,txt_start_date);
            }
        });
        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_amount, cid, bid, ptype, pinfo,date,detail;
                cid = object_company.get(sp_company.getSelectedItemPosition()).getid();
                ptype = object_payment_type.get(sp_payment_type.getSelectedItemPosition()).getid();
                e_amount = et_amount.getText().toString();
                pinfo = et_pay_info.getText().toString();
                date=txt_start_date.getText().toString();
//                detail=et_detail.getText().toString();

                bid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();

                if (cid.contentEquals("")) {
                    Toast.makeText(context, "Enter User Name", Toast.LENGTH_SHORT).show();
                } else if (ptype.contentEquals("")) {
                    Toast.makeText(context, "Enter Payment Type", Toast.LENGTH_SHORT).show();

                } else if (e_amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (ptype.contentEquals("CHEQUE")) {
                    if (bid.contentEquals("")) {
                        Toast.makeText(context, "Enter Bank Name", Toast.LENGTH_SHORT).show();
                    } else if (pinfo.contentEquals("")) {
                        Toast.makeText(context, "Enter Cheque No", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Constant_method.checkConn(context)) {
                            String param = "{\"company_id\":\"" + cid + "\",\"bank_id\":\"" + bid + "\",\"amount\":\"" + e_amount + "\",\"payment_type\":\"" + ptype + "\",\"payment_info\":\"" + pinfo + "\",\"cdate\":\"" + date + "\",\"action\":\"vehicleHourlyPay\"}";
                            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleHourlyPayActivity.this, param, Config.APi_vehicle_hourly_pay_Add);
                            myAsyncTask.execute();
                        } else {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"company_id\":\"" + cid + "\",\"bank_id\":\"" + bid + "\",\"amount\":\"" + e_amount + "\",\"payment_type\":\"" + ptype + "\",\"payment_info\":\"" + pinfo + "\",\"cdate\":\"" + date + "\",\"action\":\"vehicleHourlyPay\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleHourlyPayActivity.this, param, Config.APi_vehicle_hourly_pay_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void refrence() {
        sp_company = (Spinner) findViewById(R.id.sp_company);
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        sp_payment_type = (Spinner) findViewById(R.id.sp_payment_type);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_pay_info = (EditText) findViewById(R.id.et_pay_info);

        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        et_detail = (EditText) findViewById(R.id.et_detail);

        sp_payment_type.setOnItemSelectedListener(this);
        rel_detail = (RelativeLayout) findViewById(R.id.rel_detail);
        add_project = (Button) findViewById(R.id.btn_Add);
        update_project = (Button) findViewById(R.id.btn_Update);
        delete_project = (Button) findViewById(R.id.btn_delete);
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

    private void getPaymentType() {
        object_payment_type = new ArrayList<SpinnerModel>();
        object_payment_type.add(new SpinnerModel("", getString(R.string.Select_payment_type)));
        object_payment_type.add(new SpinnerModel("CASH", getResources().getString(R.string.Cash)));
        object_payment_type.add(new SpinnerModel("CHEQUE", getResources().getString(R.string.Cheque)));
        object_payment_type.add(new SpinnerModel("RTGS", getResources().getString(R.string.Rtgs_id)));
        sp_payment_type.setAdapter(new SpinnerAdapter(this, object_payment_type));

    }

    private void getBankName() {
        objects_bank = new ArrayList<>();
        objects_bank.add(new SpinnerModel("", getResources().getString(R.string.Select_Bank)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Bank, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("bank_id"));
                spinnerModel.setName(staff.getString("bank_name"));
                objects_bank.add(spinnerModel);
            }
            sp_bank_id.setAdapter(new SpinnerAdapter(this, objects_bank));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_vehicle_hourly_pay_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    sp_company.setSelection(0);
                    sp_payment_type.setSelection(0);
                    sp_bank_id.setSelection(0);
                    et_amount.setText("");
                    et_pay_info.setText("");
                    txt_start_date.setText("");

                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = object_payment_type.get(sp_payment_type.getSelectedItemPosition()).getid();
        if (item.contentEquals("Payment-Type")) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("CASH")) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("CHEQUE")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_pay_info.setHint(getResources().getString(R.string.chequeno));
        } else if (item.contentEquals("RTGS")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_pay_info.setHint(getResources().getString(R.string.Rtgs_id));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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



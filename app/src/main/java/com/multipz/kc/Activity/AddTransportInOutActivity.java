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
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddTransportInOutActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {
    Spinner sp_payment_type, sp_company, sp_amount_type, sp_bank_id;
    EditText et_amount, et_detail, et_pay_info;
    ArrayList<SpinnerModel> object_pay_type, object_company, objects_bank, object_amount_type;
    Shared shared;
    Context context;
    RelativeLayout rel_detail;
    Button add_project, update_project, delete_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transport_in_out);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Transport_In_Out));
        context = this;
        shared = new Shared(context);
        refrence();
        init();
    }

    private void init() {
        getPaymentType();
        getBankName();
        getCompanyName();
        getAmountType();

        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_pay_type, s_com, e_amount, s_amount_type, e_cheque_no, e_detail, s_bank = "";
                s_pay_type = object_pay_type.get(sp_payment_type.getSelectedItemPosition()).getid();
                s_com = object_company.get(sp_company.getSelectedItemPosition()).getid();
                e_amount = et_amount.getText().toString();
                s_amount_type = object_amount_type.get(sp_amount_type.getSelectedItemPosition()).getid();
                e_cheque_no = et_pay_info.getText().toString();
                e_detail = et_pay_info.getText().toString();
                s_bank = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();

                if (s_pay_type.contentEquals("")) {
                    Toast.makeText(context, "Enter Payment Type", Toast.LENGTH_SHORT).show();
                } else if (s_com.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (e_amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (s_amount_type.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount type", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"cname\":" + s_com + ",\"bank\":\"" + s_bank + "\",\"ptype\":\"" + s_pay_type + "\",\"amount\":" + e_amount + ",\"payment_type\":\"" + s_amount_type + "\",\"cheque_no\":\"" + e_cheque_no + "\",\"details\":\"" + e_detail + "\",\"action\":\"addTransportpartypayment\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddTransportInOutActivity.this, param, Config.APi_vehicle_in_out_add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void getAmountType() {
        object_amount_type = new ArrayList<SpinnerModel>();
        object_amount_type.add(new SpinnerModel("", getResources().getString(R.string.payment_type)));
        object_amount_type.add(new SpinnerModel("Cash", getResources().getString(R.string.Cash)));
        object_amount_type.add(new SpinnerModel("Cheque", getResources().getString(R.string.Cheque)));
        sp_amount_type.setAdapter(new SpinnerAdapter(this, object_amount_type));
    }

    private void refrence() {
        sp_payment_type = (Spinner) findViewById(R.id.sp_payment_type);
        sp_company = (Spinner) findViewById(R.id.sp_company);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_detail = (EditText) findViewById(R.id.et_detail);
        et_pay_info = (EditText) findViewById(R.id.et_pay_info);
        sp_amount_type = (Spinner) findViewById(R.id.sp_amount_type);
        sp_amount_type.setOnItemSelectedListener(this);
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        rel_detail = (RelativeLayout) findViewById(R.id.rel_detail);
        add_project = (Button) findViewById(R.id.btn_Add);
        update_project = (Button) findViewById(R.id.btn_Update);
        delete_project = (Button) findViewById(R.id.btn_delete);
    }

    private void getPaymentType() {
        object_pay_type = new ArrayList<SpinnerModel>();
        object_pay_type.add(new SpinnerModel("", getResources().getString(R.string.payment_type)));
        object_pay_type.add(new SpinnerModel("IN", getResources().getString(R.string.In)));
        object_pay_type.add(new SpinnerModel("OUT", getResources().getString(R.string.Out)));
        sp_payment_type.setAdapter(new SpinnerAdapter(this, object_pay_type));

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


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_vehicle_in_out_add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    et_pay_info.setText("");
                    et_amount.setText("");
                    et_detail.setText("");
                    sp_bank_id.setSelection(0);
                    sp_amount_type.setSelection(0);
                    sp_payment_type.setSelection(0);
                    sp_company.setSelection(0);
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = object_amount_type.get(sp_amount_type.getSelectedItemPosition()).getid();
        if (item.contentEquals(getResources().getString(R.string.payment_type))) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("Cash")) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("Cheque")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_pay_info.setHint(getResources().getString(R.string.chequeno));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void onResume() {
        super.onResume();

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

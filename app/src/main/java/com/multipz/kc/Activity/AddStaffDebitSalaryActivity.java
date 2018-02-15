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

public class AddStaffDebitSalaryActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {
    Spinner sp_payment_type, sp_user, sp_bank_id,sp_company;
    EditText et_amount, et_amount_detail, et_pay_info;
    ArrayList<SpinnerModel> object_user, object_payment_type, objects_bank,objects_company;
    Shared shared;
    Context context;
    RelativeLayout rel_detail;
    Button add_project, update_project, delete_project;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff_debit_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("સ્ટાફ નો ઉધાર પગાર");
        context = this;
        shared = new Shared(context);
        new CallConfigDataApi(shared).execute();

        refrence();
        init();


    }

    private void init() {
        getUserName();
        getCompanyName();
        getPaymentType();
        getBankName();
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddStaffDebitSalaryActivity.this,txt_start_date);
            }
        });
        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_amount, uid, bid, ptype, pinfo, amount_dateil,c_id,date;
                uid = object_user.get(sp_user.getSelectedItemPosition()).getid();
                c_id = objects_company.get(sp_company.getSelectedItemPosition()).getid();
                ptype = object_payment_type.get(sp_payment_type.getSelectedItemPosition()).getid();
                e_amount = et_amount.getText().toString();
                pinfo = et_pay_info.getText().toString();
                bid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();
                amount_dateil = et_amount_detail.getText().toString();
                date=txt_start_date.getText().toString();

                if (uid.contentEquals("")) {
                    Toast.makeText(context, "Enter User Name", Toast.LENGTH_SHORT).show();
                } else if (ptype.contentEquals("")) {
                    Toast.makeText(context, "Enter Payment Type", Toast.LENGTH_SHORT).show();
                } else if (e_amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (amount_dateil.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount Detail", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"user_id\": \"" + uid + "\",\"bank_id\": \"" + bid + "\",\"site_id\": \"" + c_id + "\",\"amount\": \"" + e_amount + "\",\"payment_type\": \"" + ptype + " \",\"cdate\": \"" + date + " \",\"payment_info\": \"" + pinfo + "\", \"detail\": \"" + amount_dateil + "\",\"action\": \"addUpdateDebitSalary\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddStaffDebitSalaryActivity.this, param, Config.APi_staff_debit_salary_add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void refrence() {
        sp_company = (Spinner) findViewById(R.id.sp_company);
        sp_user = (Spinner) findViewById(R.id.sp_user);
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        sp_payment_type = (Spinner) findViewById(R.id.sp_payment_type);
        et_amount = (EditText) findViewById(R.id.et_amount);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        et_amount_detail = (EditText) findViewById(R.id.et_amount_detail);
        et_pay_info = (EditText) findViewById(R.id.et_pay_info);
        sp_payment_type.setOnItemSelectedListener(this);
        rel_detail = (RelativeLayout) findViewById(R.id.rel_detail);
        add_project = (Button) findViewById(R.id.add_project);
        update_project = (Button) findViewById(R.id.update_project);
        delete_project = (Button) findViewById(R.id.btn_delete);
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
                spinnerModel.setName(staff.getString("name"));
                object_user.add(spinnerModel);
            }
            sp_user.setAdapter(new SpinnerAdapter(this, object_user));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getCompanyName() {
        objects_company = new ArrayList<>();
        objects_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                objects_company.add(spinnerModel);
            }
            sp_company.setAdapter(new SpinnerAdapter(this, objects_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getPaymentType() {
        object_payment_type = new ArrayList<SpinnerModel>();
        object_payment_type.add(new SpinnerModel("", "Payment-Type"));
        object_payment_type.add(new SpinnerModel("CASH", "Cash"));
        object_payment_type.add(new SpinnerModel("CHEQUE", "Cheque"));
        object_payment_type.add(new SpinnerModel("RTGS", "Rtgs"));
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
        if (flag == Config.APi_staff_debit_salary_add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    et_amount.setText("");
                    et_pay_info.setText("");
                    et_amount_detail.setText("");
                    sp_company.setSelection(0);
                    sp_user.setSelection(0);
                    sp_bank_id.setSelection(0);
                    txt_start_date. setText("");
                    sp_payment_type.setSelection(0);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
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
        String item = object_payment_type.get(sp_payment_type.getSelectedItemPosition()).getName();
        if (item.contentEquals("Payment-Type")) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("Cash")) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("Cheque")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_pay_info.setHint("Cheque No");
        } else if (item.contentEquals("Rtgs")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_pay_info.setHint("Rtgs ID");
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

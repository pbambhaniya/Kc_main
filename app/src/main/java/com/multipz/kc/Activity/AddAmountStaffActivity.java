package com.multipz.kc.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.AmountStaffModel;
import com.multipz.kc.Model.ProjectModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddAmountStaffActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {
    Spinner sp_username, sp_project_id, sp_payment_type, sp_bank_id;
    EditText et_amount, et_detail;
    TextView txt_start_date;
    private int mYear, mMonth, mDay;
    Shared shared;
    Context context;
    RelativeLayout rel_detail;
    Button add_project, update_project, delete_project;
    ArrayList<SpinnerModel> objects_bank, object_project, object_user, object_payment;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_amount_staff);
        setTitle(getResources().getString(R.string.amount_staff));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        shared = new Shared(this);
        new CallConfigDataApi(shared).execute();
        refrence();
        init();

    }

    private void init() {
        getBankName();
        getProjectName();
        getUserName();
        getPaymentType();

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddAmountStaffActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txt_start_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        if (getIntent().getBooleanExtra("Update", false)) {
            id = getIntent().getStringExtra("staff_amount_id");
            et_amount.setText(getIntent().getStringExtra("amount"));
            txt_start_date.setText(getIntent().getStringExtra("amount_date"));
            et_detail.setText(getIntent().getStringExtra("payment_info"));
            setSpinnerCapital(sp_payment_type, object_payment, getIntent().getStringExtra("payment_type").trim());
            setSpinner(sp_project_id, object_project, getIntent().getStringExtra("side_sort_name"));
            setSpinner(sp_bank_id, objects_bank, getIntent().getStringExtra("bankName"));
            setSpinner(sp_username, object_user, getIntent().getStringExtra("userName"));
            add_project.setVisibility(View.GONE);
            update_project.setVisibility(View.VISIBLE);
        }


        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_amount, e_detail, sdate, uid, pid, bid, ptype, pinfo;
                e_amount = et_amount.getText().toString();
                e_detail = et_detail.getText().toString();
                sdate = txt_start_date.getText().toString();
                uid = object_user.get(sp_username.getSelectedItemPosition()).getid();
                pid = object_project.get(sp_project_id.getSelectedItemPosition()).getid();
                bid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();
                ptype = object_payment.get(sp_payment_type.getSelectedItemPosition()).getid();

                if (e_amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Side Sort Name", Toast.LENGTH_SHORT).show();
                } else if (sdate.contentEquals("")) {
                    Toast.makeText(context, "Enter Budget", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"user_id\": \"" + uid + "\",\"project_id\": \"" + pid + "\",\"amount\": \"" + e_amount + "\",\"amount_date\": \"" + sdate + "\",\"payment_type\": \"" + ptype + "\",\"payment_info\": \"" + e_detail + "\",\"bank_id\": \"" + bid + "\",\"action\": \"addUpdateAmtToStaff\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddAmountStaffActivity.this, param, Config.APi_Amount_Staff_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e_amount, e_detail, sdate, uid, pid, bid, ptype;
                e_amount = et_amount.getText().toString();
                e_detail = et_detail.getText().toString();
                sdate = txt_start_date.getText().toString();
                uid = object_user.get(sp_username.getSelectedItemPosition()).getid();
                pid = object_project.get(sp_project_id.getSelectedItemPosition()).getid();
                bid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();
                ptype = object_payment.get(sp_payment_type.getSelectedItemPosition()).getid();

                if (e_amount.contentEquals("")) {
                    Toast.makeText(context, "Enter Side Sort Name", Toast.LENGTH_SHORT).show();
                } else if (sdate.contentEquals("")) {
                    Toast.makeText(context, "Enter Budget", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"" + id + "\",\"user_id\": \"" + uid + "\",\"project_id\": \"" + pid + "\",\"amount\": \"" + e_amount + "\",\"amount_date\": \"" + sdate + "\",\"payment_type\": \"" + ptype + "\",\"payment_info\": \"" + e_detail + "\",\"bank_id\": \"" + bid + "\",\"action\": \"addUpdateAmtToStaff\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddAmountStaffActivity.this, param, Config.APi_Amount_Staff_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        delete_project.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Constant_method.checkConn(context)) {
//                    String param = "{\"id\":\"1\",\"optFlag\":\"d\",\"action\":\"editDeleteProject\"}";
//                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectActivity.this, param, Config.API_TOKEN_DELETE_PROJ);
//                    myAsyncTask.execute();
//                } else {
//                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void refrence() {
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        sp_username = (Spinner) findViewById(R.id.sp_username);
        sp_project_id = (Spinner) findViewById(R.id.sp_project_id);
        sp_payment_type = (Spinner) findViewById(R.id.sp_payment_type);
        et_amount = (EditText) findViewById(R.id.et_amount);
        et_detail = (EditText) findViewById(R.id.et_detail);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        rel_detail = (RelativeLayout) findViewById(R.id.rel_detail);
        sp_payment_type.setOnItemSelectedListener(this);
        add_project = (Button) findViewById(R.id.btn_Add);
        update_project = (Button) findViewById(R.id.btn_Update);
        delete_project = (Button) findViewById(R.id.btn_delete);
    }

    private void getPaymentType() {
        object_payment = new ArrayList<SpinnerModel>();
        object_payment.add(new SpinnerModel("", getResources().getString(R.string.Select_payment_type)));
        object_payment.add(new SpinnerModel("CASH", getResources().getString(R.string.Cash)));
        object_payment.add(new SpinnerModel("CHEQUE", getResources().getString(R.string.Cheque)));
        object_payment.add(new SpinnerModel("RTGS", getResources().getString(R.string.Rtgs)));
        sp_payment_type.setAdapter(new SpinnerAdapter(this, object_payment));
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
            sp_username.setAdapter(new SpinnerAdapter(this, object_user));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getProjectName() {
        object_project = new ArrayList<>();
        object_project.add(new SpinnerModel("", getResources().getString(R.string.Select_project)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Project, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();

                spinnerModel.setid(staff.getString("project_id"));
                spinnerModel.setName(staff.getString("side_sort_name"));
                object_project.add(spinnerModel);
            }
            sp_project_id.setAdapter(new SpinnerAdapter(this, object_project));
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        if (flag == Config.APi_Amount_Staff_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    et_amount.setText("");
                    et_detail.setText("");
                    txt_start_date.setText("");
                    sp_payment_type.setSelection(0);
                    sp_username.setSelection(0);
                    sp_bank_id.setSelection(0);
                    sp_project_id.setSelection(0);
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_Amount_Staff_Update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    ArrayList<AmountStaffModel> userList = new ArrayList<>();
                    AmountStaffModel amountstaff = new AmountStaffModel();
                    amountstaff.setStaff_amount_id(jsonObject1.getString("staff_amount_id"));
                    amountstaff.setUser_id(jsonObject1.getString("user_id"));
                    amountstaff.setProject_id(jsonObject1.getString("project_id"));
                    amountstaff.setBank_id(jsonObject1.getString("bank_id"));
                    amountstaff.setAmount(jsonObject1.getString("amount"));
                    amountstaff.setAmount_date(jsonObject1.getString("amount_date"));
                    amountstaff.setPayment_type(jsonObject1.getString("payment_type"));
                    amountstaff.setPayment_info(jsonObject1.getString("payment_info"));
                    amountstaff.setUserName(jsonObject1.getString("userName"));
                    amountstaff.setWork_type(jsonObject1.getString("work_type"));
                    amountstaff.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    amountstaff.setBankName(jsonObject1.getString("bankName"));
                    userList.add(amountstaff);
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//        } else if (flag == Config.API_TOKEN_DELETE_PROJ) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                Log.e("Responce", jsonObject.toString());
//                msg = jsonObject.getString("msg");
//                success = jsonObject.getInt("status");
//                if (success == 1) {
//                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = object_payment.get(sp_payment_type.getSelectedItemPosition()).getid();
        if (item.contentEquals("Payment-Type")) {
            rel_detail.setVisibility(View.GONE);


        } else if (item.contentEquals("CASH")) {
            rel_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("CHEQUE")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_detail.setHint(getResources().getString(R.string.chequeno));
        } else if (item.contentEquals("RTGS")) {
            rel_detail.setVisibility(View.VISIBLE);
            et_detail.setHint(getResources().getString(R.string.Rtgs_id));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinner(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }

    private void setSpinnerCapital(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName().toUpperCase())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }

}

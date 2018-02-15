package com.multipz.kc.Staff;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SalaryDataReportAdapter;
import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.Report.SalaryStatementActivity;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class GetSalaryDataForPaySalary extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {

    private EditText edt_credit_debit, edt_presentdaytotalday, et_pay_info, edt_currunt_salary, edt_pay_salary, edt_total_pay_salary, edt_detail;
    private Spinner sp_payment_type, sp_bank_id;
    private Button btn_update_pay_salary;
    private String userId;
    private TextView txt_select_month_year_pay;
    private int mMonth, mYear, date;
    private Context mcontext;
    private ArrayList<SpinnerModel> object_payment_type, objects_bank;
    Shared shared;
    private String monthYear;
    String currentSelectedDate = "", basetype, ptype_id;
    String credit_debit, presentday, csalary, paysalary, total_pay_salary, detail, pinfo, bid;
    LinearLayout rel_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_salary_data_for_pay_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.paid_salary);
        mcontext = this;
        shared = new Shared(getApplicationContext());
        reference();
        init();
    }

    private void init() {
        getPaymentType();
        getBankName();
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        basetype = i.getStringExtra("basetype");

        txt_select_month_year_pay.setOnClickListener(new View.OnClickListener() {
            public boolean isOkayClicked;

            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);

                final DatePickerDialog datePickerDialog = new DatePickerDialog(GetSalaryDataForPaySalary.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            currentSelectedDate = "0" + (monthOfYear + 1) + "-" + year;
                        } else {
                            currentSelectedDate = (monthOfYear + 1) + "-" + year;
                        }
                    }
                }, mYear, mMonth, date);

                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mMonth > 1 && mMonth < 9) {
                                    currentSelectedDate = "0" + (mMonth + 1) + "-" + mYear;
                                } else {
                                    currentSelectedDate = (mMonth + 1) + "-" + mYear;
                                }
                                txt_select_month_year_pay.setText(currentSelectedDate);
                                datePickerDialog.getDatePicker().clearFocus();
                                isOkayClicked = true;
                                getmonthYear();
                            }
                        });
                datePickerDialog.show();
            }
        });


        btn_update_pay_salary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                credit_debit = edt_credit_debit.getText().toString();
                presentday = edt_presentdaytotalday.getText().toString();
                csalary = edt_currunt_salary.getText().toString();
                paysalary = edt_pay_salary.getText().toString();
                total_pay_salary = edt_total_pay_salary.getText().toString();
                detail = edt_detail.getText().toString();
                ptype_id = object_payment_type.get(sp_payment_type.getSelectedItemPosition()).getid();
                pinfo = et_pay_info.getText().toString();
                bid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();

                if (currentSelectedDate.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter month/year", Toast.LENGTH_SHORT).show();
                } else if (credit_debit.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter credit_debit", Toast.LENGTH_SHORT).show();
                } else if (presentday.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter present day total day", Toast.LENGTH_SHORT).show();
                } else if (csalary.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter current salary", Toast.LENGTH_SHORT).show();
                } else if (paysalary.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter pay salary", Toast.LENGTH_SHORT).show();
                } else if (total_pay_salary.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter total pay salary", Toast.LENGTH_SHORT).show();
                } else if (detail.contentEquals("")) {
                    Toast.makeText(mcontext, "please enter detail", Toast.LENGTH_SHORT).show();
                } else {
                    getSalarypaid();
                }


            }
        });

    }

    private void getSalarypaid() {
        String month = monthYear.toString().replace("-", "/");
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"payment_info\":\"" + pinfo + "\",\"detail\":\"" + detail + "\",\"user_id\":\"" + userId + "\",\"bank_id\":\"" + bid + "\",\"payment_type\":\"" + ptype_id + "\",\"totalPaySalary\":\"" + total_pay_salary + "\",\"paySalary\":\"" + paysalary + "\",\"monthYear\":\"" + month + "\",\"et\":\"" + basetype + "\",\"action\":\"salaryPaid\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, GetSalaryDataForPaySalary.this, params, Config.APi_salaryPaid);
            myAsyncTask.execute();
        } else {
            Toast.makeText(mcontext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void getPaymentType() {
        object_payment_type = new ArrayList<SpinnerModel>();
        object_payment_type.add(new SpinnerModel("", "Payment-Type"));
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

    private void getmonthYear() {
        monthYear = txt_select_month_year_pay.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            getParamForgetSalaryData();
        }
    }

    private void getParamForgetSalaryData() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"user_id\":\"" + userId + "\",\"monthYear\":\"" + monthYear + "\",\"action\":\"getDataForPaySalary\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, GetSalaryDataForPaySalary.this, params, Config.APi_getDataForPaySalary);
            myAsyncTask.execute();
        } else {
            Toast.makeText(mcontext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void reference() {
        txt_select_month_year_pay = (TextView) findViewById(R.id.txt_select_month_year_pay);
        edt_credit_debit = (EditText) findViewById(R.id.edt_credit_debit);
        edt_presentdaytotalday = (EditText) findViewById(R.id.edt_presentdaytotalday);
        edt_currunt_salary = (EditText) findViewById(R.id.edt_currunt_salary);
        edt_pay_salary = (EditText) findViewById(R.id.edt_pay_salary);
        edt_total_pay_salary = (EditText) findViewById(R.id.edt_total_pay_salary);
        et_pay_info = (EditText) findViewById(R.id.et_pay_info);
        edt_detail = (EditText) findViewById(R.id.edt_detail);
        sp_payment_type = (Spinner) findViewById(R.id.sp_payment_type);
        btn_update_pay_salary = (Button) findViewById(R.id.btn_update_pay_salary);
        rel_detail = (LinearLayout) findViewById(R.id.lay_rel_detail);
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        sp_payment_type.setOnItemSelectedListener(this);
        edt_credit_debit.setEnabled(false);
        edt_presentdaytotalday.setEnabled(false);
        edt_currunt_salary.setEnabled(false);


    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_getDataForPaySalary) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {


                    JSONObject obj = jsonObject.getJSONObject("data");
                    String totalDays = obj.getString("totalDays");
                    String presentDays = obj.getString("presentDays");
                    String currentSalary = obj.getString("currentSalary");
                    String paidSalary = obj.getString("paidSalary");
                    edt_credit_debit.setText("0");
                    edt_presentdaytotalday.setText(presentDays + "/" + totalDays);
                    edt_currunt_salary.setText(currentSalary);
                } else if (success == 0) {
                    Toast.makeText(mcontext, "", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_salaryPaid) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    Toast.makeText(mcontext, "" + msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mcontext, "" + msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
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

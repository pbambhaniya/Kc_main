package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddKCExpenseActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    Button btn_add, btn_update;
    Shared shared;
    Context context;
    String id;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;
    private ArrayList<KCExpenseModel> userList;

    EditText et_detail, et_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff_expense);
        setTitle("કેસી નો ખર્ચ ");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(context);

        et_detail = (EditText) findViewById(R.id.et_detail);
        et_amount = (EditText) findViewById(R.id.et_amount);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_update = (Button) findViewById(R.id.btn_update);

        if (getIntent().getBooleanExtra("Update", false)) {
            et_detail.setText(getIntent().getStringExtra("detail"));
            et_amount.setText(getIntent().getStringExtra("amount"));
            id = getIntent().getStringExtra("kc_exp_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            btn_add.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);
        }

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddKCExpenseActivity.this, txt_start_date);
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edetail, eamount, date;
                date = txt_start_date.getText().toString();
                edetail = et_detail.getText().toString();
                eamount = et_amount.getText().toString();
                if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter expense Detail", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"detail\":\"" + edetail + "\",\"amount\":\"" + eamount + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateKcExp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddKCExpenseActivity.this, param, Config.APi_KC_Expense_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edetail, eamount, date;
                date = txt_start_date.getText().toString();
                edetail = et_detail.getText().toString();
                eamount = et_amount.getText().toString();
                if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter expense Detail", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"detail\":\"" + edetail + "\",\"amount\":\"" + eamount + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateKcExp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddKCExpenseActivity.this, param, Config.APi_KC_Expense_update);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


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
        if (flag == Config.APi_KC_Expense_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    et_amount.setText("");
                    et_detail.setText("");
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_KC_Expense_update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    userList = new ArrayList<>();
                    KCExpenseModel kcExpense = new KCExpenseModel();
                    kcExpense.setKc_exp_id(jsonObject1.getString("kc_exp_id"));
                    kcExpense.setDetail(jsonObject1.getString("detail"));
                    kcExpense.setAmount(jsonObject1.getString("amount"));
                    userList.add(kcExpense);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

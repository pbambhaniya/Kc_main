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
import com.multipz.kc.Model.KCExpenseModel;
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

public class AddSiteGeneralExpenseActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    Button btn_add, btn_update;
    Shared shared;
    Context context;
    String id;
    Spinner sp_pro_id;
    public ArrayList<KCExpenseModel> userList;
    EditText et_detail, et_amount;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;
    ArrayList<SpinnerModel> object_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site_general_expense);
        setTitle("સાઇટ નો ખર્ચ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(context);

        new CallConfigDataApi(shared).execute();
        sp_pro_id = (Spinner) findViewById(R.id.sp_pro_id);

        et_detail = (EditText) findViewById(R.id.et_detail);
        et_amount = (EditText) findViewById(R.id.et_amount);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_update = (Button) findViewById(R.id.btn_update);

        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddSiteGeneralExpenseActivity.this, txt_start_date);
            }
        });

        object_project = new ArrayList<>();
        object_project.add(new SpinnerModel("", getResources().getString(R.string.Select_project)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Project, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("project_id"));
//                String name = staff.getString("name");
//                String type = staff.getString("work_type");
//                String val = name + "(" + " " + ")" + type;
                spinnerModel.setName(staff.getString("side_sort_name"));
                object_project.add(spinnerModel);
            }
            sp_pro_id.setAdapter(new SpinnerAdapter(this, object_project));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (getIntent().getBooleanExtra("Update", false)) {
            et_detail.setText(getIntent().getStringExtra("detail"));
            et_amount.setText(getIntent().getStringExtra("amount"));
            id = getIntent().getStringExtra("side_gen_exp_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            setSpinner(sp_pro_id, object_project, getIntent().getStringExtra("side_sort_name"));
            btn_add.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edetail, eamount, sproject, date;
                sproject = object_project.get(sp_pro_id.getSelectedItemPosition()).getid();
                edetail = et_detail.getText().toString();
                date = txt_start_date.getText().toString();
                eamount = et_amount.getText().toString();
                if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter site expense Detail", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"user_id\": \"" + shared.getUserId().toString() + "\",\"project_id\": \"" + sproject + "\",\"detail\": \"" + edetail + "\",\"amount\": \"" + eamount + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdatesSiteGenExp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddSiteGeneralExpenseActivity.this, param, Config.APi_Site_general_Expense_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String edetail, eamount, sproject, date;
                sproject = object_project.get(sp_pro_id.getSelectedItemPosition()).getid();
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
                    String param = "{\"id\": \"" + id + "\",\"user_id\": \"" + shared.getUserId().toString() + "\",\"project_id\": \"" + sproject + "\",\"detail\": \"" + edetail + "\",\"amount\": \"" + eamount + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdatesSiteGenExp\"}\n";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddSiteGeneralExpenseActivity.this, param, Config.APi_Site_general_Expense_update);
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
        if (flag == Config.APi_Site_general_Expense_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    et_amount.setText("");
                    et_detail.setText("");
                    sp_pro_id.setSelection(0);
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
        } else if (flag == Config.APi_Site_general_Expense_update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    userList = new ArrayList<>();
                    KCExpenseModel kcExpense = new KCExpenseModel();
                    kcExpense.setSide_gen_exp_id(jsonObject1.getString("side_gen_exp_id"));
                    kcExpense.setProject_id(jsonObject1.getString("project_id"));
                    kcExpense.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    kcExpense.setDetail(jsonObject1.getString("detail"));
                    kcExpense.setAmount(jsonObject1.getString("amount"));
                    userList.add(kcExpense);
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

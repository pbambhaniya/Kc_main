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

import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.WorkTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddWorkTypeActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText et_worktype;
    Button btn_work_type_add, btn_work_type_update;
    Shared shared;
    Context context;
    String id;
    private ArrayList<SpinnerModel> objects_work_type;
    private ArrayList<WorkTypeModel> userList;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_work_type);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(context);
        setTitle("કામ નો પ્રકાર ");
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        et_worktype = (EditText) findViewById(R.id.et_worktype);
        btn_work_type_add = (Button) findViewById(R.id.btn_work_type_add);
        btn_work_type_update = (Button) findViewById(R.id.btn_work_type_update);



        if (getIntent().getBooleanExtra("Update", false)) {
            et_worktype.setText(getIntent().getExtras().getString("work_type"));
            id = getIntent().getStringExtra("work_type_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            btn_work_type_add.setVisibility(View.GONE);
            btn_work_type_update.setVisibility(View.VISIBLE);
        }

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new DatePickerForTextSet(AddWorkTypeActivity.this,txt_start_date);
            }
        });

        btn_work_type_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eworktype,date;
                date=txt_start_date.getText().toString();
                eworktype = et_worktype.getText().toString();
                if (eworktype.contentEquals("")) {
                    Toast.makeText(context, "Enter Work Type", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals(""))
                {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                }
                else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"cdate\":\""+date+"\",\"work_type\":\"" + eworktype + "\",\"action\":\"addUpdateWorkType\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddWorkTypeActivity.this, param, Config.APi_WorkType_Add);
                    myAsyncTask.execute();
//                    Intent company_master=new Intent(AddVehicleDetailsActivity.this,VehicleDetailsActivity.class);
//                    startActivity(company_master);
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


        btn_work_type_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eworktype,date;
                eworktype = et_worktype.getText().toString();
                date=txt_start_date.getText().toString();
                if (eworktype.contentEquals("")) {
                    Toast.makeText(context, "Enter Work Type", Toast.LENGTH_SHORT).show();
                }else if (date.contentEquals(""))
                {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();

                }

                else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"cdate\":\"" +date+ "\",\"work_type\":\"" + eworktype + "\",\"action\":\"addUpdateWorkType\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddWorkTypeActivity.this, param, Config.APi_WorkType_Update);
                    myAsyncTask.execute();
//                    Intent company_master=new Intent(AddVehicleDetailsActivity.this,VehicleDetailsActivity.class);
//                    startActivity(company_master);
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
        if (flag == Config.APi_WorkType_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    et_worktype.setText("");
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_WorkType_Update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    userList = new ArrayList<>();
                    WorkTypeModel workType = new WorkTypeModel();
                    workType.setWork_type_id(jsonObject1.getString("work_type_id"));
                    workType.setWork_type(jsonObject1.getString("work_type"));
                    userList.add(workType);
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

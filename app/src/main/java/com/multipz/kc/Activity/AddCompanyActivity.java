package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONObject;


public class AddCompanyActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText name, mobile;
    Button add_company_mst, update_company_mst;
    Shared shared;
    Context context;
    String id;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company));
        context = this;
        shared = new Shared(context);


        new CallConfigDataApi(shared).execute();
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        name = (EditText) findViewById(R.id.company_name);
        mobile = (EditText) findViewById(R.id.mobile);
        add_company_mst = (Button) findViewById(R.id.add_company_master);
        update_company_mst = (Button) findViewById(R.id.update_company_master);

        if (getIntent().getBooleanExtra("Update", false)) {
            name.setText(getIntent().getExtras().getString("name"));
            mobile.setText(getIntent().getExtras().getString("contact_no"));
            id = getIntent().getStringExtra("company_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            add_company_mst.setVisibility(View.GONE);
            update_company_mst.setVisibility(View.VISIBLE);
        }
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddCompanyActivity.this, txt_start_date);
            }
        });

        add_company_mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ename, emobile, date;
                ename = name.getText().toString();
                date = txt_start_date.getText().toString();
                emobile = mobile.getText().toString();
                if (ename.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (emobile.contentEquals("")) {
                    Toast.makeText(context, "Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"name\":\"" + ename + "\",\"cdate\":\"" + date + "\",\"contact_no\":\"" + emobile + "\",\"action\":\"addUpdateCompany\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyActivity.this, param, Config.API_TOKEN_ADD_COMPANY_MASTER);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        update_company_mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ename, emobile, date;
                ename = name.getText().toString();
                date = txt_start_date.getText().toString();
                emobile = mobile.getText().toString();

                if (ename.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (emobile.contentEquals("")) {
                    Toast.makeText(context, "Enter Contact Number", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"name\":\"" + ename + "\",\"contact_no\":\"" + emobile + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateCompany\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyActivity.this, param, Config.API_TOKEN_UPDATE_COMPANY_MASTER);
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

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_TOKEN_ADD_COMPANY_MASTER) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    name.setText("");
                    mobile.setText("");
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
        } else if (flag == Config.API_TOKEN_UPDATE_COMPANY_MASTER) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
//                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//                    ArrayList<CompanyModel> userList = new ArrayList<>();
//                    CompanyModel companyMasterModel = new CompanyModel();
//                    companyMasterModel.setCompany_id(jsonObject1.getString("company_id"));
//                    companyMasterModel.setName(jsonObject1.getString("name"));
//                    companyMasterModel.setContact_no(jsonObject1.getString("contact_no"));
//                    userList.add(companyMasterModel);
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

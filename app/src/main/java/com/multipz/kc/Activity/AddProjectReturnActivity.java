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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.ProjectReturnsModel;
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

public class AddProjectReturnActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    String id;
    Shared shared;
    private ArrayList<SpinnerModel> objects_company;
    Button add_project_return, update_project;
    Context context;
    EditText edit_sort_name;
    EditText edit_sd, edit_fmg, edit_tds;
    Spinner sp_pro_name;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project_return);

        setTitle("પ્રોજેક્ટ રિટર્ન્સ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared = new Shared(this);
        context = this;

        new CallConfigDataApi(shared).execute();
        edit_sort_name = (EditText) findViewById(R.id.edit_sort_name);
        edit_sd = (EditText) findViewById(R.id.edit_sd);
        edit_fmg = (EditText) findViewById(R.id.edit_fmg);
        edit_tds = (EditText) findViewById(R.id.edit_tds);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        sp_pro_name = (Spinner) findViewById(R.id.sp_pro_name);

        add_project_return = (Button) findViewById(R.id.btn_Add_project);
        update_project = (Button) findViewById(R.id.btn_Update_project);


        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddProjectReturnActivity.this, txt_start_date);
            }
        });


        objects_company = new ArrayList<>();
        objects_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Project, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("project_id"));
                spinnerModel.setName(staff.getString("side_sort_name"));
                objects_company.add(spinnerModel);
            }
            sp_pro_name.setAdapter(new SpinnerAdapter(this, objects_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (getIntent().getBooleanExtra("Update", false)) {
            id = getIntent().getStringExtra("project_id");
            setSpinner(sp_pro_name, objects_company, getIntent().getStringExtra("side_sort_name"));
            edit_sd.setText(getIntent().getStringExtra("sd"));
            edit_fmg.setText(getIntent().getStringExtra("fmg"));
            edit_tds.setText(getIntent().getStringExtra("tds"));
            txt_start_date.setText(getIntent().getStringExtra("date"));
            add_project_return.setVisibility(View.GONE);
            update_project.setVisibility(View.VISIBLE);
        }
        add_project_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String esd, efmg, etds, spinner, date;
                esd = edit_sd.getText().toString();
                efmg = edit_fmg.getText().toString();
                etds = edit_tds.getText().toString();
                date = txt_start_date.getText().toString();
                spinner = objects_company.get(sp_pro_name.getSelectedItemPosition()).getid();
                if (esd.contentEquals("")) {
                    Toast.makeText(context, "Select Project Name", Toast.LENGTH_SHORT).show();
                } else if (efmg.contentEquals("")) {
                    Toast.makeText(context, "Enter SD", Toast.LENGTH_SHORT).show();
                } else if (etds.contentEquals("")) {
                    Toast.makeText(context, "Enter Fmg", Toast.LENGTH_SHORT).show();
                } else if (spinner.contentEquals("")) {
                    Toast.makeText(context, "Enter Tds", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String params = "{\"id\":\"\",\"project_id\":\"" + spinner + "\",\"sd\":\"" + esd + "\",\"fmg\":\"" + efmg + "\",\"tds\":\"" + etds + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdatProjectReturn\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectReturnActivity.this, params, Config.API_Add_Project_return);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        update_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String esd, efmg, etds, spinner, date;
                esd = edit_sd.getText().toString();
                efmg = edit_fmg.getText().toString();
                date = txt_start_date.getText().toString();
                etds = edit_tds.getText().toString();
                spinner = objects_company.get(sp_pro_name.getSelectedItemPosition()).getid();
                if (esd.contentEquals("")) {
                    Toast.makeText(context, "Select Project Name", Toast.LENGTH_SHORT).show();
                } else if (efmg.contentEquals("")) {
                    Toast.makeText(context, "Enter SD", Toast.LENGTH_SHORT).show();
                } else if (etds.contentEquals("")) {
                    Toast.makeText(context, "Enter Fmg", Toast.LENGTH_SHORT).show();
                } else if (spinner.contentEquals("")) {
                    Toast.makeText(context, "Enter Tds", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String params = "{\"id\":\"" + id + "\",\"project_id\":\"" + spinner + "\",\"sd\":\"" + esd + "\",\"fmg\":\"" + efmg + "\",\"tds\":\"" + etds + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdatProjectReturn\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectReturnActivity.this, params, Config.API_Update_Project_return);
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
//                    String param = "{\"id\":\"1\",\"optFlag\":\"e\",\"action\":\"editDeleteProjectReturn\"}";
//                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectReturnActivity.this, param, Config.API_TOKEN_DELETE_PROJ);
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
        if (flag == Config.API_Add_Project_return) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    edit_tds.setText("");
                    edit_fmg.setText("");
                    edit_sd.setText("");
                    sp_pro_name.setSelection(0);
                    txt_start_date.setText("");
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_Update_Project_return) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    ArrayList<ProjectReturnsModel> userList = new ArrayList<>();
                    ProjectReturnsModel project = new ProjectReturnsModel();
                    project.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    project.setProject_return_id(jsonObject1.getString("project_return_id"));
                    project.setProject_id(jsonObject1.getString("project_id"));
                    project.setSd(jsonObject1.getString("sd"));
                    project.setFmg(jsonObject1.getString("fmg"));
                    project.setTds(jsonObject1.getString("tds"));
                    project.setIs_status(jsonObject1.getString("is_status"));
                    userList.add(project);
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//
//            } else if (flag == Config.API_TOKEN_DELETE_PROJ) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    Log.e("Responce", jsonObject.toString());
//                    msg = jsonObject.getString("msg");
//                    success = jsonObject.getInt("status");
//
//                    if (success == 1) {
//                        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
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

    private void setSpinner(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }


}


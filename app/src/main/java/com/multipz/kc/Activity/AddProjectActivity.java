package com.multipz.kc.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Model.ProjectModel;
import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AddProjectActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    TextView date;
    String id;
    private int mYear, mMonth, mDay;
    Shared shared;
    Button add_project, update_project, delete_project;
    Context context;
    EditText edit_sort_name, edit_side_now, edit_budget, edit_deposite, edit_location;
    RelativeLayout rl_start_dates;
    TextView txt_start_date, txt_end_date, txt_s_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);
        setTitle("પ્રોજેક્ટ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shared = new Shared(this);
        context = this;
        new CallConfigDataApi(shared).execute();
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        edit_sort_name = (EditText) findViewById(R.id.edit_sort_name);
        edit_side_now = (EditText) findViewById(R.id.edit_side_now);
        edit_budget = (EditText) findViewById(R.id.edit_budget);
        edit_deposite = (EditText) findViewById(R.id.edit_deposite);
        edit_location = (EditText) findViewById(R.id.edit_location);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        txt_end_date = (TextView) findViewById(R.id.txt_end_date);
        txt_s_date = (TextView) findViewById(R.id.txt_s_date);

        add_project = (Button) findViewById(R.id.btn_Add_project);
        update_project = (Button) findViewById(R.id.btn_Update_project);
        delete_project = (Button) findViewById(R.id.btn_delete_project);

        if (getIntent().getBooleanExtra("Update", false)) {
            edit_sort_name.setText(getIntent().getExtras().getString("side_sort_name"));
            txt_start_date.setText(getIntent().getExtras().getString("start_date"));
            txt_end_date.setText(getIntent().getExtras().getString("end_date"));
            edit_budget.setText(getIntent().getExtras().getString("budget"));
            edit_side_now.setText(getIntent().getExtras().getString("name_of_work"));
            edit_deposite.setText(getIntent().getExtras().getString("deposite"));
            edit_location.setText(getIntent().getExtras().getString("location"));
            txt_s_date.setText(getIntent().getStringExtra("date"));
            id = getIntent().getStringExtra("project_id");
            add_project.setVisibility(View.GONE);
            update_project.setVisibility(View.VISIBLE);
        }


        txt_s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddProjectActivity.this, txt_s_date);
            }
        });
        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sname, snow, ebudget, edeposite, elocation, sdate, edate, date;
                sname = edit_sort_name.getText().toString();
                snow = edit_side_now.getText().toString();
                ebudget = edit_budget.getText().toString();
                date = txt_s_date.getText().toString();
                edeposite = edit_deposite.getText().toString();
                elocation = edit_location.getText().toString();
                sdate = txt_start_date.getText().toString();
                edate = txt_end_date.getText().toString();
                if (sname.contentEquals("")) {
                    Toast.makeText(context, "Enter Side Sort Name", Toast.LENGTH_SHORT).show();
                } else if (snow.contentEquals("")) {
                    Toast.makeText(context, "Enter SIde Now", Toast.LENGTH_SHORT).show();
                } else if (ebudget.contentEquals("")) {
                    Toast.makeText(context, "Enter Budget", Toast.LENGTH_SHORT).show();
                } else if (edeposite.contentEquals("")) {
                    Toast.makeText(context, "Enter Deposite", Toast.LENGTH_SHORT).show();
                } else if (sdate.contentEquals("")) {
                    Toast.makeText(context, "Enter Start Date", Toast.LENGTH_SHORT).show();
                } else if (edate.contentEquals("")) {
                    Toast.makeText(context, "Enter End date", Toast.LENGTH_SHORT).show();
                } else if (elocation.contentEquals("")) {
                    Toast.makeText(context, "Enter Location", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter  Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
//                    {"action":"addUpdateProject","side_sort_name":"KC Recidency","name_of_work":"Recidency","budget":"5000000","deposite":"50000","start_date":"2017-12-22","end_date":"2018-12-22","location":"surat","cdate":"2017-12-22","id":""}

                    String param = "{\"id\":\"\",\"side_sort_name\":\"" + sname + "\",\"name_of_work\":\"" + snow + "\",\"budget\":\"" + ebudget + "\",\"deposite\": \"" + edeposite + "\",\"start_date\": \"" + sdate + "\",\"end_date\": \"" + edate + "\",\"location\": \"" + elocation + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateProject\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectActivity.this, param, Config.API_TOKEN_ADD_PROJ);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sname, snow, ebudget, edeposite, elocation, sdate, edate, date;
                sname = edit_sort_name.getText().toString();
                snow = edit_side_now.getText().toString();
                date = txt_s_date.getText().toString();
                ebudget = edit_budget.getText().toString();
                edeposite = edit_deposite.getText().toString();
                elocation = edit_location.getText().toString();
                sdate = txt_start_date.getText().toString();
                edate = txt_end_date.getText().toString();

                if (sname.contentEquals("")) {
                    Toast.makeText(context, "Enter Side Sort Name", Toast.LENGTH_SHORT).show();
                } else if (snow.contentEquals("")) {
                    Toast.makeText(context, "Enter SIde Now", Toast.LENGTH_SHORT).show();
                } else if (ebudget.contentEquals("")) {
                    Toast.makeText(context, "Enter Budget", Toast.LENGTH_SHORT).show();
                } else if (edeposite.contentEquals("")) {
                    Toast.makeText(context, "Enter Deposite", Toast.LENGTH_SHORT).show();
                } else if (sdate.contentEquals("")) {
                    Toast.makeText(context, "Enter Start Date", Toast.LENGTH_SHORT).show();
                } else if (edate.contentEquals("")) {
                    Toast.makeText(context, "Enter End date", Toast.LENGTH_SHORT).show();
                } else if (elocation.contentEquals("")) {
                    Toast.makeText(context, "Enter Location", Toast.LENGTH_SHORT).show();
                }  else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                }
                else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"side_sort_name\":\"" + sname + "\",\"name_of_work\":\"" + snow + "\",\"budget\":\"" + ebudget + "\",\"deposite\": \"" + edeposite + "\",\"start_date\": \"" + sdate + "\",\"end_date\": \"" + edate + "\",\"location\": \"" + elocation + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateProject\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectActivity.this, param, Config.API_TOKEN_UPDATE_PROJ);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"1\",\"optFlag\":\"d\",\"action\":\"editDeleteProject\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddProjectActivity.this, param, Config.API_TOKEN_DELETE_PROJ);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddProjectActivity.this,
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
        txt_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddProjectActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                txt_end_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_TOKEN_ADD_PROJ) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    edit_sort_name.setText("");
                    edit_side_now.setText("");
                    edit_budget.setText("");
                    edit_deposite.setText("");
                    edit_location.setText("");
                    txt_start_date.setText("");
                    txt_end_date.setText("");
                    txt_s_date.setText("");
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_UPDATE_PROJ) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    ArrayList<ProjectModel> userList = new ArrayList<>();
                    ProjectModel project = new ProjectModel();
                    project.setProject_id(jsonObject1.getString("project_id"));
                    project.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    project.setName_of_work(jsonObject1.getString("name_of_work"));
                    project.setBudget(jsonObject1.getString("budget"));
                    project.setStart_date(jsonObject1.getString("start_date"));
                    project.setEnd_date(jsonObject1.getString("end_date"));
                    project.setLocation(jsonObject1.getString("location"));
                    project.setDeposite(jsonObject1.getString("deposite"));
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

        } else if (flag == Config.API_TOKEN_DELETE_PROJ) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                msg = jsonObject.getString("msg");
                success = jsonObject.getInt("status");
                if (success == 1) {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
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

package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.AttendanceDataReportAdapter;
import com.multipz.kc.Adapter.PartyItemAdapter;
import com.multipz.kc.Adapter.ProjectItemListAdapter;
import com.multipz.kc.Adapter.SiteGeneralExpenseReportAdapter;
import com.multipz.kc.Model.AttendanceDateModel;
import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.SiteExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ProjectGeneralExpLIstActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private TextView txt_select_gen_exp_list;
    private int mYear, mMonth, date;
    private Button btn_send_project_list;
    private Context mcontext;
    private ArrayList<SiteExReportModel> list;
    private ListView listviewproject_exp_list;
    private ProjectItemListAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private String monthYear, project_Id, user_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_general_exp_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.SiteGenaralExpense));
        mcontext = this;
        reference();
        init();
    }


    private void reference() {
        txt_select_gen_exp_list = (TextView) findViewById(R.id.txt_select_gen_exp_list);
        btn_send_project_list = (Button) findViewById(R.id.btn_send_project_list);
        listviewproject_exp_list = (ListView) findViewById(R.id.listviewproject_exp_list);
    }

    private void init() {
        Intent i = getIntent();
        project_Id = i.getStringExtra("project_Id");
        user_Id = i.getStringExtra("user_Id");

        txt_select_gen_exp_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
/*
                DatePickerDialog datePickerDialog = new DatePickerDialog(SalaryStatementActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        txt_select_month_year_attendance.setText(year + "-" + monthOfYear);
                    }
                }, mYear, mMonth, date);
                datePickerDialog.findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
                datePickerDialog.show();*/

                DatePickerDialog datePickerDialog = new DatePickerDialog(ProjectGeneralExpLIstActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_gen_exp_list.setText("0" + (monthOfYear + 1) + "-" + year);
                        } else {
                            txt_select_gen_exp_list.setText((monthOfYear + 1) + "-" + year);
                        }
                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();

            }
        });

        listviewproject_exp_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamForSalaryReoprt();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });
        btn_send_project_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReport();
            }
        });

    }

    private void setReport() {
        monthYear = txt_select_gen_exp_list.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            getParamForSalaryReoprt();
        }
    }

    private void getParamForSalaryReoprt() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"user_id\":\"" + user_Id + "\",\"monthYear\":\"" + monthYear + "\",\"project_id\":\"" + project_Id + "\",\"page\":\"" + pagecount + "\",\"action\":\"" + Config.siteExpDetail + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, ProjectGeneralExpLIstActivity.this, params, Config.APi_siteExpDetail);
            myAsyncTask.execute();
        } else {
            Toast.makeText(mcontext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_siteExpDetail) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");

                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        list = new ArrayList<>();
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            SiteExReportModel siteExReportModel = new SiteExReportModel();
                            siteExReportModel.setSide_gen_exp_id(c.getString("side_gen_exp_id"));
                            siteExReportModel.setUser_id(c.getString("user_id"));
                            siteExReportModel.setProject_id(c.getString("project_id"));
                            siteExReportModel.setDetail(c.getString("detail"));
                            siteExReportModel.setAmount(c.getString("amount"));
                            siteExReportModel.setName_of_work(c.getString("name_of_work"));
                            siteExReportModel.setTotalSum(c.getString("totalSum"));
                            siteExReportModel.setUserName(c.getString("name"));
                            list.add(siteExReportModel);
                        }
                        adapter = new ProjectItemListAdapter(getApplicationContext(), list);
                        listviewproject_exp_list.setAdapter(adapter);
                    } else {
                        Toast.makeText(ProjectGeneralExpLIstActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProjectGeneralExpLIstActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
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

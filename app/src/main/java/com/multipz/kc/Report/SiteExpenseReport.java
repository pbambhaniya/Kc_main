package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Activity.DrawerActivity;
import com.multipz.kc.Adapter.ProjectAdapter;
import com.multipz.kc.Adapter.SiteExReportAdapter;
import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.ProjectModel;
import com.multipz.kc.Model.ProjectReturnsModel;
import com.multipz.kc.Model.SiteExReportModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.R.attr.data;

public class SiteExpenseReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    Spinner sp_pro_id;
    Constant_method method;
    Context context;
    ImageView imageView;
    TextView startdate, enddate, total_amount;
    private int mYear, mMonth, mDay;
    String Sdate = "", Edate = "";
    ListView report_list;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private ArrayList<SpinnerModel> objects_project;
    ArrayList<SiteExReportModel> userList;
    Shared shared;
    private SiteExReportAdapter adapter;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_report);

        setTitle(getResources().getString(R.string.Site_exp_report));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared = new Shared(this);
        context = this;

        sp_pro_id = (Spinner) findViewById(R.id.sp_pro_id);
        imageView = (ImageView) findViewById(R.id.img_clear);
        startdate = (TextView) findViewById(R.id.txt_start_date);
        enddate = (TextView) findViewById(R.id.txt_end_date);
        report_list = (ListView) findViewById(R.id.report_list);
        add = (Button) findViewById(R.id.btn_add);
        total_amount = (TextView) findViewById(R.id.total_amount);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_pro_id.setSelection(0);
                startdate.setText("");
                enddate.setText("");
            }
        });

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SiteExpenseReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Sdate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SiteExpenseReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Edate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        report_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    callservice();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callservice();
            }
        });

        objects_project = new ArrayList<>();
        objects_project.add(new SpinnerModel("", "Select Project Name"));

        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Project, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);


                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("project_id"));
                spinnerModel.setName(staff.getString("side_sort_name"));
                objects_project.add(spinnerModel);
            }
            sp_pro_id.setAdapter(new SpinnerAdapter(this, objects_project));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callservice() {
        String spinner;
        spinner = objects_project.get(sp_pro_id.getSelectedItemPosition()).getid();

        if (Constant_method.checkConn(context)) {

            String params;

            if (Sdate.contentEquals("") && Edate.contentEquals("")) {


                params = "{\"page\":\"" + pagecount + "\",\"is_report\":\"\",\"project_id\":\"" + spinner + "\",\"date_range\":\"\",\"action\":\"reportSiteExp\"}";
            } else if (Sdate.contentEquals("") || Edate.contentEquals("")) {

                if (Sdate.contentEquals("")) {
                    Edate = Sdate;
                } else {
                    Sdate = Edate;
                }


                params = "{\"page\":\"" + pagecount + "\",\"is_report\":\"\",\"project_id\":\"" + spinner + "\",\"date_range\":\"" + Sdate + "-" + Edate + "\",\"action\":\"reportSiteExp\"}";
            } else {
                params = "{\"page\":\"" + pagecount + "\",\"is_report\":\"\",\"project_id\":\"" + spinner + "\",\"date_range\":\"" + Sdate + "-" + Edate + "\",\"action\":\"reportSiteExp\"}";
            }

            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, SiteExpenseReport.this, params, Config.APi_Get_site_expense_report);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_Get_site_expense_report) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                userList = new ArrayList<>();
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject c = jsonArray.getJSONObject(i1);
                    SiteExReportModel siteExReport = new SiteExReportModel();
                    siteExReport.setSide_gen_exp_id(c.getString("side_gen_exp_id"));
                    siteExReport.setUser_id(c.getString("user_id"));
                    siteExReport.setProject_id(c.getString("project_id"));
                    siteExReport.setDetail(c.getString("detail"));
                    siteExReport.setAmount(c.getString("amount"));
                    siteExReport.setCreated_date(Constant_method.getOnlyDate(c.getString("created_date")));
                    siteExReport.setUserName(c.getString("userName"));
                    siteExReport.setSide_sort_name(c.getString("side_sort_name"));
                    userList.add(siteExReport);
                }
                JSONArray array = jsonObject.getJSONArray("totamount");
                for (int j = 0; j < array.length(); j++) {
                    JSONObject obj = array.getJSONObject(j);
                    String amt = obj.getString("totAmt");
                    total_amount.setText(amt);
                }


                adapter = new SiteExReportAdapter(context, userList);
                report_list.setAdapter(adapter);

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
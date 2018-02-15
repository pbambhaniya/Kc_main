package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
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
import com.multipz.kc.Model.AttendanceDateModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class ShowAttendanceReportActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {


    private TextView txt_select_month_year_attendance;
    private int mYear, mMonth, date;
    private Button btn_send_attendance_report;
    private Context mcontext;
    private ArrayList<AttendanceDateModel> listatendancedata;
    private ListView listviewattendanceData;
    private AttendanceDataReportAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private String monthYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_attendance_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Attendense_report));
        mcontext = this;

        reference();
        init();
    }

    private void reference() {
        txt_select_month_year_attendance = (TextView) findViewById(R.id.txt_select_month_year_attendance);
        btn_send_attendance_report = (Button) findViewById(R.id.btn_send_attendance_report);
        listviewattendanceData = (ListView) findViewById(R.id.listviewattendanceData);
    }

    private void init() {
        listatendancedata = new ArrayList<>();
        txt_select_month_year_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ShowAttendanceReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_month_year_attendance.setText(year + "-" + "0" + (monthOfYear + 1));
                        } else {
                            txt_select_month_year_attendance.setText(year + "-" + (monthOfYear + 1));
                        }
                    }
                }, mYear, mMonth + 1, date);

                datePickerDialog.show();

            }
        });

        listviewattendanceData.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        btn_send_attendance_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReport();
            }
        });

    }

    private void setReport() {
        monthYear = txt_select_month_year_attendance.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            listatendancedata.clear();
            getParamForSalaryReoprt();
        }
    }

    private void getParamForSalaryReoprt() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"" + Config.attendanceData + "\",\"monthyear\":\"" + monthYear + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, ShowAttendanceReportActivity.this, params, Config.APi_attendanceData);
            myAsyncTask.execute();
        } else {
            Toast.makeText(mcontext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_attendanceData) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            AttendanceDateModel attendanceDateModel = new AttendanceDateModel();
                            attendanceDateModel.setUser_id(c.getString("user_id"));
                            attendanceDateModel.setName(c.getString("name"));
                            attendanceDateModel.setSalary_based_type(c.getString("salary_based_type"));
                            attendanceDateModel.setProfile_img(c.getString("profile_img"));
                            listatendancedata.add(attendanceDateModel);
                        }
                        adapter = new AttendanceDataReportAdapter(getApplicationContext(), listatendancedata);
                        listviewattendanceData.setAdapter(adapter);
                    } else {
                        Toast.makeText(mcontext, "Data not found", Toast.LENGTH_SHORT).show();
                    }


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

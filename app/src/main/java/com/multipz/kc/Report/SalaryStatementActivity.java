package com.multipz.kc.Report;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SalaryDataReportAdapter;
import com.multipz.kc.Adapter.SiteExReportAdapter;
import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.Model.SiteExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class SalaryStatementActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private TextView txt_select_month_year;
    private int mYear, mMonth, date;
    private Button btn_send_salary_report;
    private Context mcontext;
    private ArrayList<SalaryDataModel> listSalaryData;
    private ListView listviewSalaryData;
    private SalaryDataReportAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private String monthYear;
    private int pagecount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_statement);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Salary_Statement));
        mcontext = this;
        listSalaryData = new ArrayList<>();
        reference();
        init();
    }

    private void reference() {
        txt_select_month_year = (TextView) findViewById(R.id.txt_select_month_year);
        btn_send_salary_report = (Button) findViewById(R.id.btn_send_salary_report);
        listviewSalaryData = (ListView) findViewById(R.id.listviewSalaryData);
    }

    private void init() {
        txt_select_month_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SalaryStatementActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_month_year.setText(year + "-" + "0" + (monthOfYear + 1));
                        } else {
                            txt_select_month_year.setText(year + "-" + (monthOfYear + 1));
                        }

                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();


            }
        });

        listviewSalaryData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             /*   if (view.getId() == R.id.btn_view_salary) {
                    Intent intent = new Intent(SalaryStatementActivity.this, ViewSalaryDataActivity.class);
                    intent.putExtra("monthYear", monthYear);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }*/
            }
        });

        listviewSalaryData.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamForSalaryData();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });


        btn_send_salary_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmonthYear();
            }
        });

    }


    private void getmonthYear() {
        monthYear = txt_select_month_year.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            listSalaryData.clear();
            getParamForSalaryData();
        }
    }

    private void getParamForSalaryData() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"" + Config.salaryData + "\",\"monthyear\":\"" + monthYear + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, SalaryStatementActivity.this, params, Config.APi_salaryData);
            myAsyncTask.execute();
        } else {
            Toast.makeText(mcontext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDetailClickFromAdapter(String userId) {
        Intent intent = new Intent(SalaryStatementActivity.this, ViewSalaryDataActivity.class);
        intent.putExtra("monthYear", monthYear);
        intent.putExtra("userId", userId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_salaryData) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            SalaryDataModel salaryDataModel = new SalaryDataModel();
                            salaryDataModel.setUser_id(c.getString("user_id"));
                            salaryDataModel.setName(c.getString("name"));
                            salaryDataModel.setDay(c.getString("day"));
                            salaryDataModel.setActual_salary(c.getString("actual_salary"));
                            salaryDataModel.setPay_salary(c.getString("pay_salary"));
                            salaryDataModel.setActual_pay_salary(c.getString("actual_pay_salary"));
                            salaryDataModel.setPay_status(c.getString("pay_status"));
                            salaryDataModel.setAmount(c.getString("amount"));
                            listSalaryData.add(salaryDataModel);
                        }
                        adapter = new SalaryDataReportAdapter(SalaryStatementActivity.this, listSalaryData);
                        listviewSalaryData.setAdapter(adapter);
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

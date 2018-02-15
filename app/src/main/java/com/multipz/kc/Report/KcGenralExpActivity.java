package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
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

import com.multipz.kc.Adapter.GenExpAdapter;
import com.multipz.kc.Model.GenExpReport;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class KcGenralExpActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private TextView txt_select_gen_exp;
    private int mYear, mMonth, date;
    private Button btn_send_gen_Exp_report;
    private Context mcontext;
    private ArrayList<GenExpReport> listGenExpReoprt;
    private ListView listviewGenExpReport;
    private GenExpAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private String monthYear;
    private int pagecount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kc_genral_exp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.KcGenralExpense));
        listGenExpReoprt = new ArrayList<>();
        mcontext = this;
        reference();
        init();
    }


    private void reference() {
        txt_select_gen_exp = (TextView) findViewById(R.id.txt_select_gen_exp);
        btn_send_gen_Exp_report = (Button) findViewById(R.id.btn_send_gen_Exp_report);
        listviewGenExpReport = (ListView) findViewById(R.id.listviewGenExpReport);
    }

    private void init() {
        txt_select_gen_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(KcGenralExpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_gen_exp.setText("0" + (monthOfYear + 1) + "-" + year);
                        } else {
                            txt_select_gen_exp.setText((monthOfYear + 1) + "-" + year);
                        }

                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();


            }
        });

        listviewGenExpReport.setOnScrollListener(new AbsListView.OnScrollListener() {
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


        btn_send_gen_Exp_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getmonthYear();
            }
        });

    }


    private void getmonthYear() {
        monthYear = txt_select_gen_exp.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            listGenExpReoprt.clear();
            getParamForSalaryData();
        }
    }

    private void getParamForSalaryData() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"monthYear\":\"" + monthYear + "\",\"action\" :\"" + Config.kcGeneralExp + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, KcGenralExpActivity.this, params, Config.APi_kcGeneralExp);
            myAsyncTask.execute();
        } else {
            Toast.makeText(mcontext, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_kcGeneralExp) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            GenExpReport salaryDataModel = new GenExpReport();

                            salaryDataModel.setKc_exp_id(c.getString("kc_exp_id"));
                            salaryDataModel.setDetail(c.getString("detail"));
                            salaryDataModel.setAmount(c.getString("amount"));
                            listGenExpReoprt.add(salaryDataModel);
                        }
                        adapter = new GenExpAdapter(getApplicationContext(), listGenExpReoprt);
                        listviewGenExpReport.setAdapter(adapter);
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

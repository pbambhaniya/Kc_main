package com.multipz.kc.Staff;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SalaryPayReport;
import com.multipz.kc.Adapter.ViewSalaryDataAdapter;
import com.multipz.kc.Model.SalaryPayModel;
import com.multipz.kc.Model.ViewSalaryDataModel;
import com.multipz.kc.R;
import com.multipz.kc.Report.SalaryStatementActivity;
import com.multipz.kc.Report.ViewSalaryDataActivity;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SalaryPayAcivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private ListView listviewSalarypayActivity;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private Context mcontex;
    Shared shared;
    int count = 1;
    private SalaryPayReport adapter;
    private ArrayList<SalaryPayModel> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_pay_acivity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.paid_salary);
        mcontex = this;
        shared = new Shared(getApplicationContext());

        reference();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        count = 1;
        list = new ArrayList<>();
        getParamforViewSalaryPay();
    }

    private void init() {

        listviewSalarypayActivity.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    count++;
                    getParamforViewSalaryPay();

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });
    }

    private void reference() {
        listviewSalarypayActivity = (ListView) findViewById(R.id.listviewSalarypayActivity);
    }

    private void getParamforViewSalaryPay() {
        if (Constant_method.checkConn(mcontex)) {
            String params = "{\"page\":\"" + count + "\",\"action\":\"getSalaryData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, SalaryPayAcivity.this, params, Config.APi_getSalaryDatapay);
            myAsyncTask.execute();
        } else {
            Toast.makeText(SalaryPayAcivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDetailClickFromAdapter(String userId, String basetype) {
        Intent intent = new Intent(SalaryPayAcivity.this, GetSalaryDataForPaySalary.class);
        intent.putExtra("userId", userId);
        intent.putExtra("basetype", basetype);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_getSalaryDatapay) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            SalaryPayModel model = new SalaryPayModel();
                            model.setUser_id(c.getString("user_id"));
                            model.setName(c.getString("name"));
                            model.setSalary_based_type(c.getString("salary_based_type"));
                            model.setSalary(c.getString("salary"));
                            model.setCreditDebit(c.getString("creditDebit"));
                            list.add(model);
                        }
                        adapter = new SalaryPayReport(SalaryPayAcivity.this, list);
                        listviewSalarypayActivity.setAdapter(adapter);
                    } else {
                        Toast.makeText(mcontex, "Data not found", Toast.LENGTH_SHORT).show();
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

package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.ViewSalaryDataAdapter;
import com.multipz.kc.Model.ViewSalaryDataModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewSalaryDataActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private ListView listviewSalaryData;
    private Context mcontex;
    private ArrayList<ViewSalaryDataModel> list;
    private ViewSalaryDataAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int count = 1;
    Shared shared;
    private String monthYear, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salary_data);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Salary_Statement));
        mcontex = this;
        shared = new Shared(getApplicationContext());
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        Intent i = getIntent();
        monthYear = i.getStringExtra("monthYear");
        userId = i.getStringExtra("userId");
        getParamforViewSalaryData();

        listviewSalaryData.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    count++;
                    getParamforViewSalaryData();

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });


      /*  listviewSalaryData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PartyWisePayModel partyWisePayModel = list.get(i);
                Intent intent = new Intent(ViewSalaryDataActivity.this, PartyItemActivty.class);
                intent.putExtra("companyid", partyWisePayModel.getCompany_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });*/

    }


    private void reference() {
        listviewSalaryData = (ListView) findViewById(R.id.listviewSalaryData);
    }

    private void getParamforViewSalaryData() {
        if (Constant_method.checkConn(mcontex)) {
            String params = "{\"page\":\"" + count + "\",\"monthyear\":\"" + monthYear + "\",\"uid\":\"" + userId + "\",\"action\":\"viewSalaryData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, ViewSalaryDataActivity.this, params, Config.APi_viewSalaryData);
            myAsyncTask.execute();
        } else {
            Toast.makeText(ViewSalaryDataActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_viewSalaryData) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            ViewSalaryDataModel model = new ViewSalaryDataModel();
                            model.setSallary_id(c.getString("sallary_id"));
                            model.setUser_id(c.getString("user_id"));
                            model.setBank_id(c.getString("bank_id"));
                            model.setPayment_type(c.getString("payment_type"));
                            model.setPayment_info(c.getString("payment_info"));
                            model.setDetail(c.getString("detail"));
                            model.setAmount(c.getString("amount"));
                            model.setNet_salary(c.getString("net_salary"));
                            model.setDate(Constant_method.getOnlyDate(c.getString("date")));
                            model.setIs_delete(c.getString("is_delete"));
                            list.add(model);
                        }
                        adapter = new ViewSalaryDataAdapter(getApplicationContext(), list);
                        listviewSalaryData.setAdapter(adapter);
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

package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.PartyWisePaymeentAdapter;
import com.multipz.kc.Adapter.SalaryDataReportAdapter;
import com.multipz.kc.Model.PartyWisePayModel;
import com.multipz.kc.Model.SalaryDataModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PartywisepaymentActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private ListView listviewpartywisepayment;
    private Context mcontex;
    private ArrayList<PartyWisePayModel> list;
    private PartyWisePaymeentAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partywisepayment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.PartyWisePayment));
        mcontex = this;
        list = new ArrayList<>();
        reference();
        init();


    }

    private void init() {
        getParamforPartywisepayment();

        listviewpartywisepayment.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    count++;
                    getParamforPartywisepayment();

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });


        listviewpartywisepayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                PartyWisePayModel partyWisePayModel = list.get(i);
                Intent intent = new Intent(PartywisepaymentActivity.this, PartyItemActivty.class);
                intent.putExtra("companyid", partyWisePayModel.getCompany_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }


    private void reference() {
        listviewpartywisepayment = (ListView) findViewById(R.id.listviewpartywisepayment);
    }

    private void getParamforPartywisepayment() {
        if (Constant_method.checkConn(mcontex)) {
            String params = "{\"page\":\"" + count + "\",\"action\" :\"" + Config.partywishpayment + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, PartywisepaymentActivity.this, params, Config.APi_partywishpayment);
            myAsyncTask.execute();
        } else {
            Toast.makeText(PartywisepaymentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_partywishpayment) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            PartyWisePayModel partyWisePayModel = new PartyWisePayModel();
                            partyWisePayModel.setCompany_id(c.getString("company_id"));
                            partyWisePayModel.setName(c.getString("name"));
                            partyWisePayModel.setTotal_amount(c.getString("total_amount"));
                            partyWisePayModel.setTotal_pay(c.getString("total_pay"));
                            list.add(partyWisePayModel);
                        }
                        adapter = new PartyWisePaymeentAdapter(getApplicationContext(), list);
                        listviewpartywisepayment.setAdapter(adapter);
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

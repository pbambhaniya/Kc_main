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
import com.multipz.kc.Adapter.SiteGeneralExpenseReportAdapter;
import com.multipz.kc.Model.PartyWisePayModel;
import com.multipz.kc.Model.SiteExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SiteGenralExpReportActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private ListView listviewSiteGenExp;
    private Context mcontex;
    private ArrayList<SiteExReportModel> list;
    private SiteGeneralExpenseReportAdapter adapter;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_genral_exp_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.SiteGenaralExpense));
        mcontex = this;
        list = new ArrayList<>();
        reference();
        init();
    }


    private void init() {
        getParamforPartywisepayment();

        listviewSiteGenExp.setOnScrollListener(new AbsListView.OnScrollListener() {
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


        listviewSiteGenExp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SiteExReportModel partyWisePayModel = list.get(i);
                Intent intent = new Intent(SiteGenralExpReportActivity.this, ProjectGeneralExpLIstActivity.class);
                intent.putExtra("project_Id", partyWisePayModel.getProject_id());
                intent.putExtra("user_Id", partyWisePayModel.getUser_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

    }


    private void reference() {
        listviewSiteGenExp = (ListView) findViewById(R.id.listviewSiteGenExp);
    }

    private void getParamforPartywisepayment() {
        if (Constant_method.checkConn(mcontex)) {
            String params = "{\"page\":\"" + count + "\",\"action\":\"" + Config.siteGeneralExp + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, SiteGenralExpReportActivity.this, params, Config.APi_siteGeneralExp);
            myAsyncTask.execute();
        } else {
            Toast.makeText(SiteGenralExpReportActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_siteGeneralExp) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            SiteExReportModel siteExReportModel = new SiteExReportModel();
                            siteExReportModel.setSide_gen_exp_id(c.getString("side_gen_exp_id"));
                            siteExReportModel.setUser_id(c.getString("user_id"));
                            siteExReportModel.setProject_id(c.getString("project_id"));
                            siteExReportModel.setDetail(c.getString("detail"));
                            siteExReportModel.setAmount(c.getString("amount"));
                            siteExReportModel.setName_of_work(c.getString("name_of_work"));
                            siteExReportModel.setSide_sort_name(c.getString("side_sort_name"));
                            siteExReportModel.setTotalSum(c.getString("totalSum"));

                            list.add(siteExReportModel);
                        }
                        adapter = new SiteGeneralExpenseReportAdapter(getApplicationContext(), list);
                        listviewSiteGenExp.setAdapter(adapter);
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

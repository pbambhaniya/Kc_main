package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.PartyItemAdapter;
import com.multipz.kc.Adapter.SiteWisePaymentAdapter;
import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.SiteWisePayment;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SiteWisePaymentActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private Context mcontext;
    private ArrayList<SiteWisePayment> list;
    private SiteWisePaymentAdapter adapter;
    private ListView listviewsitewisepayment;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_wise_payment);
        setTitle(getResources().getString(R.string.SiteWisePAyment));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {

        getParamforPartywisepayment();
        listviewsitewisepayment.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
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
    }

    private void reference() {
        listviewsitewisepayment = (ListView) findViewById(R.id.listviewsitewisepayment);
    }

    private void getParamforPartywisepayment() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"" + Config.sitewishpayment + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, SiteWisePaymentActivity.this, params, Config.APi_sitewishpayment);
            myAsyncTask.execute();
        } else {
            Toast.makeText(SiteWisePaymentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_sitewishpayment) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            SiteWisePayment model = new SiteWisePayment();
                            model.setProject_id(c.getString("project_id"));
                            model.setSide_sort_name(c.getString("side_sort_name"));
                            model.setStart_date(c.getString("start_date"));
                            model.setAmount(c.getString("amount"));
                            list.add(model);
                        }
                        adapter = new SiteWisePaymentAdapter(getApplicationContext(), list);
                        listviewsitewisepayment.setAdapter(adapter);
                    } else {
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

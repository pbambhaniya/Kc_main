package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Adapter.TransportCompanyReportAdapter;
import com.multipz.kc.Model.TransportCompanyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransportCompanyModule extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {
    private RecyclerView listviewtransportcompanyreport;
    private Context mcontex;
    private ArrayList<TransportCompanyModel> list;
    private TransportCompanyReportAdapter adapter;
    private int lastVisibleItem, firstVisible;
    private boolean userScrolled = true;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private LinearLayoutManager layoutManager;
    int count = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_company_module);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.TransportCompany));
        mcontex = this;
        list = new ArrayList<>();
        reference();
        init();
    }


    private void init() {
        getParamfortransportCompanyreport();
        listviewtransportcompanyreport.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        count++;
                        getParamfortransportCompanyreport();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void reference() {
        listviewtransportcompanyreport = (RecyclerView) findViewById(R.id.listviewtransportcompanyreport);
    }


    private void getParamfortransportCompanyreport() {
        if (Constant_method.checkConn(mcontex)) {
            String params = "{\"page\":\"" + count + "\",\"action\":\"transportCompanyReportGet\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, TransportCompanyModule.this, params, Config.APi_transportCompanyReportGet);
            myAsyncTask.execute();
        } else {
            Toast.makeText(TransportCompanyModule.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_transportCompanyReportGet) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            TransportCompanyModel model = new TransportCompanyModel();
                            model.setTbl_transport_payment_id(c.getString("tbl_transport_payment_id"));
                            model.setPartyName(c.getString("name"));
                            model.setAmt1(c.getString("amt1"));
                            list.add(model);
                        }
                        adapter = new TransportCompanyReportAdapter(getApplicationContext(), list);
                        layoutManager = new LinearLayoutManager(this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        listviewtransportcompanyreport.setLayoutManager(layoutManager);
                        listviewtransportcompanyreport.setAdapter(adapter);
                        adapter.setClickListener(this);
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
    public void itemClicked(View View, int position) {
        TransportCompanyModel model = list.get(position);
        if (View.getId() == R.id.btnTransportDetail) {
            Intent intent = new Intent(TransportCompanyModule.this, TransportDetailActivity.class);
            intent.putExtra("id", model.getTbl_transport_payment_id());
            startActivity(intent);
        } else if (View.getId() == R.id.btnPaymentDetail) {
            Intent intent = new Intent(TransportCompanyModule.this, PaymentDetailActivity.class);
            intent.putExtra("id", model.getTbl_transport_payment_id());
            startActivity(intent);
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

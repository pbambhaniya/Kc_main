package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.TransportDetailAdapter;
import com.multipz.kc.Model.TransportDetailModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TransportDetailActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private String transport_id;
    private Context mcontext;
    private ArrayList<TransportDetailModel> list;
    private TransportDetailAdapter adapter;
    private ListView listviewtransportDetail;
    int pagecount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport_detail);
        setTitle(getResources().getString(R.string.TransportCompany));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        list = new ArrayList<>();
        reference();
        init();
    }


    private void init() {
        Intent i = getIntent();
        transport_id = i.getStringExtra("id");
        getParamfortransportDetail();

    }

    private void reference() {
        listviewtransportDetail = (ListView) findViewById(R.id.listviewtransportDetail);

    }

    private void getParamfortransportDetail() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"transportDetails\",\"id\":\"" + transport_id + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, TransportDetailActivity.this, params, Config.APitransportDetails);
            myAsyncTask.execute();
        } else {
            Toast.makeText(TransportDetailActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APitransportDetails) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            TransportDetailModel model = new TransportDetailModel();
                            model.setLocation(c.getString("location"));
                            model.setPay_amount(c.getString("pay_amount"));
                            model.setRevert_amount(c.getString("revert_amount"));
                            model.setCreated_date(c.getString("created_date"));
                            list.add(model);
                        }
                        adapter = new TransportDetailAdapter(getApplicationContext(), list);
                        listviewtransportDetail.setAdapter(adapter);
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

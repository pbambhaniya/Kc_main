package com.multipz.kc.Report;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.PaymentDetailAdapter;
import com.multipz.kc.Adapter.TransportDetailAdapter;
import com.multipz.kc.Model.PaymentDetailModel;
import com.multipz.kc.Model.TransportDetailModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PaymentDetailActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private String transport_id;
    private Context mcontext;
    private ArrayList<PaymentDetailModel> list;
    private PaymentDetailAdapter adapter;
    private ListView listviewpaymentdetail;
    int pagecount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail);
        setTitle(getResources().getString(R.string.Payment_detil));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        Intent i = getIntent();
        transport_id = i.getStringExtra("id");
        getParamforpaymentDetail();

    }

    private void reference() {
        listviewpaymentdetail = (ListView) findViewById(R.id.listviewpaymentdetail);

    }

    private void getParamforpaymentDetail() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"paymentDetails\",\"id\":\"" + transport_id + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, PaymentDetailActivity.this, params, Config.APi_paymentDetails);
            myAsyncTask.execute();
        } else {
            Toast.makeText(PaymentDetailActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_paymentDetails) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            PaymentDetailModel model = new PaymentDetailModel();
                            model.setTbl_master_payment_in_out_id(c.getString("tbl_master_payment_in_out_id"));
                            model.setType(c.getString("type"));
                            model.setPayment_type(c.getString("payment_type"));
                            model.setAmount(c.getString("amount"));
                            model.setCheque_no(c.getString("cheque_no"));
                            model.setCreate_date(Constant_method.getOnlyDate(c.getString("create_date")));
                            list.add(model);
                        }
                        adapter = new PaymentDetailAdapter(getApplicationContext(), list);
                        listviewpaymentdetail.setAdapter(adapter);
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

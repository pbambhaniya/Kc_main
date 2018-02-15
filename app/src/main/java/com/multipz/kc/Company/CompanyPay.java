package com.multipz.kc.Company;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Activity.AddCompanyActivity;
import com.multipz.kc.Activity.AddCompanyPayActivity;
import com.multipz.kc.Adapter.CompanyPayAdapter;
import com.multipz.kc.Adapter.Company_Master_Adapter;
import com.multipz.kc.Details.CompanyDetailActivity;
import com.multipz.kc.Details.CompanyPayDetailActivity;
import com.multipz.kc.Model.CompanyModel;
import com.multipz.kc.Model.CompanyPayModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanyPay extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    ListView company_mst_list;
    Shared shared;
    Context context;
    private CompanyPayAdapter adapter;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    ArrayList<CompanyPayModel> userList;
    private int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_pay);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_pay));
        context = this;
        shared = new Shared(context);
        reference();
        init();


    }

    private void init() {

        company_mst_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CompanyPayModel item = userList.get(i);
                Intent intent = new Intent(context, CompanyPayDetailActivity.class);
                intent.putExtra("comp_pay_id", item.getComp_pay_id());
                intent.putExtra("companyName", item.getCompanyName());
                intent.putExtra("bank_name", item.getBank_name());
                intent.putExtra("amount", item.getAmount());
                intent.putExtra("date",Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("payment_type", item.getPayment_type());
                intent.putExtra("payment_info", item.getPayment_info());
                startActivity(intent);
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddCompanyPayActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        company_mst_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    paramforcompanypayData();
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
        company_mst_list = (ListView) findViewById(R.id.company_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }

    @Override
    public void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        paramforcompanypayData();
    }

    private void paramforcompanypayData() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getCompanyPayData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_Company_Pay);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponseService(String response, int flag) {
        if (flag == Config.APi_Get_Company_Pay) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                    CompanyPayModel companyPayModel = new CompanyPayModel();
                    companyPayModel.setBank_name(jsonObject1.getString("bank_name"));
                    companyPayModel.setCompanyName(jsonObject1.getString("companyName"));
                    companyPayModel.setComp_pay_id(jsonObject1.getString("comp_pay_id"));
                    companyPayModel.setCompany_id(jsonObject1.getString("company_id"));
                    companyPayModel.setBank_id(jsonObject1.getString("bank_id"));
                    companyPayModel.setCdate(jsonObject1.getString("cdate"));
                    companyPayModel.setAmount(jsonObject1.getString("amount"));
                    companyPayModel.setPayment_type(jsonObject1.getString("payment_type"));
                    companyPayModel.setPayment_info(jsonObject1.getString("payment_info"));
                    userList.add(companyPayModel);
                }
                adapter = new CompanyPayAdapter(context, userList);
                company_mst_list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

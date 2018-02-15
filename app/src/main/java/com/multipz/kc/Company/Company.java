package com.multipz.kc.Company;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Activity.AddCompanyActivity;
import com.multipz.kc.Adapter.Company_Master_Adapter;
import com.multipz.kc.Details.CompanyDetailActivity;
import com.multipz.kc.Model.CompanyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Company extends AppCompatActivity {

    ListView company_mst_list;
    Shared shared;
    Context context;
    private Company_Master_Adapter adapter;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    ArrayList<CompanyModel> userList;

    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                if (flag == Config.API_TOKEN_GET_COMPANY_MASTER) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        userList = new ArrayList<>();

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);

                            CompanyModel companyMasterModel = new CompanyModel();
                            companyMasterModel.setCompany_id(c.getString("company_id"));
                            companyMasterModel.setName(c.getString("name"));
                            companyMasterModel.setCdate(c.getString("cdate"));
                            companyMasterModel.setContact_no(c.getString("contact_no"));
                            userList.add(companyMasterModel);
                        }
                        adapter = new Company_Master_Adapter(context, userList);
                        company_mst_list.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        setTitle(getResources().getString(R.string.Company));
        context = this;
        shared = new Shared(context);
        callServiceResponse();

        company_mst_list = (ListView) findViewById(R.id.company_list);

        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getCompanyData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_COMPANY_MASTER);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        company_mst_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CompanyModel item = userList.get(i);
                Intent intent = new Intent(context, CompanyDetailActivity.class);
                intent.putExtra("company_id", item.getCompany_id());
                intent.putExtra("name", item.getName());
                intent.putExtra("date",Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("contact_no", item.getContact_no());
                startActivity(intent);
            }
        });

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddCompanyActivity.class);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        String param = "{\"action\":\"getCompanyData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_COMPANY_MASTER);
        myAsyncTask.execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

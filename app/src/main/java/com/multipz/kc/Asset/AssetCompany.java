package com.multipz.kc.Asset;

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

import com.multipz.kc.Activity.AddMaterialCompanyActivity;
import com.multipz.kc.Adapter.MaterialCompanyAdapter;
import com.multipz.kc.Details.MaterialCompanyDetailActivity;
import com.multipz.kc.Model.MaterialCompanyModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AssetCompany extends AppCompatActivity {
    Context context;
    Shared shared;
    MyAsyncTask.AsyncInterface asyncInterface;

    private MaterialCompanyAdapter adapter;
    ListView comapany_list;
    FloatingActionButton fabAdd;

    ArrayList<MaterialCompanyModel> userList;

    private void callServiceResponse() {

        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                String msg;
                int sucess;

                if (flag == Config.API_TOKEN_GET_COMPANY) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        userList = new ArrayList<>();

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            MaterialCompanyModel companyModel = new MaterialCompanyModel();
                            companyModel.setMaterial_comp_id(c.getString("material_comp_id"));
                            companyModel.setMatetailName(c.getString("MaterialName"));
                            companyModel.setName(c.getString("name"));
                            userList.add(companyModel);
                        }
                        adapter = new MaterialCompanyAdapter(context, userList);
                        comapany_list.setAdapter(adapter);
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
        setContentView(R.layout.activity_asset_company);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Assets Company");
        context = this;
        shared = new Shared(context);
        callServiceResponse();
        comapany_list = (ListView) findViewById(R.id.company_list);

        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getMateCompData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_COMPANY);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        comapany_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MaterialCompanyModel item = userList.get(i);
                Intent intent = new Intent(context, MaterialCompanyDetailActivity.class);
                intent.putExtra("material_comp_id", item.getMaterial_comp_id());
                intent.putExtra("matetailName", item.getMatetailName());
                intent.putExtra("name", item.getName());
                startActivity(intent);
            }
        });

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddMaterialCompanyActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        String param = "{\"action\":\"getMateCompData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_COMPANY);
        myAsyncTask.execute();

    }

}

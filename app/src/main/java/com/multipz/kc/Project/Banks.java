package com.multipz.kc.Project;

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

import com.multipz.kc.Activity.AddBankActivity;
import com.multipz.kc.Adapter.BankAdapter;
import com.multipz.kc.Details.BankDetailActivity;
import com.multipz.kc.Model.BankModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Banks extends AppCompatActivity {
    Shared shared;
    private BankAdapter adapter;
    ListView bank_list;
    MyAsyncTask.AsyncInterface asyncInterface;
    Context context;
    ArrayList<BankModel> userList;
    FloatingActionButton fabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("બેંક");


        context = this;
        shared = new Shared(context);
        refrence();
        init();


    }

    private void init() {
        callServiceResponse();
        getBankData();
        bank_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BankModel item = userList.get(i);
                Intent intent = new Intent(context, BankDetailActivity.class);
                intent.putExtra("bank_id", item.getBank_id());
                intent.putExtra("bankname", item.getBank_name());
                intent.putExtra("ifsc", item.getIfsc_code());
                intent.putExtra("branch", item.getBranch());
                intent.putExtra("date",Constant_method.getMyDate(item.getCdate()));
                startActivity(intent);
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddBankActivity.class);

                startActivity(intent);
            }
        });
    }

    private void refrence() {
        bank_list = (ListView) findViewById(R.id.bank_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }

    @Override
    public void onResume() {
        super.onResume();
        getBankData();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getBankData() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getBankData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_BANK);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void callServiceResponse() {

        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                int success;
                String msg;

                if (flag == Config.API_TOKEN_GET_BANK) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        success = jsonObject.getInt("status");
                        msg = jsonObject.getString("msg");

                        if (success == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            userList = new ArrayList<>();
                            for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                                JSONObject c = jsonArray.getJSONObject(i1);
                                BankModel bankModel = new BankModel();
                                bankModel.setBank_id(c.getString("bank_id"));
                                bankModel.setBank_name(c.getString("bank_name"));
                                bankModel.setIfsc_code(c.getString("ifsc_code"));
                                bankModel.setCdate(c.getString("cdate"));
                                bankModel.setBranch(c.getString("branch"));
                                userList.add(bankModel);

                            }
//                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            adapter = new BankAdapter(context, userList);
                            bank_list.setAdapter(adapter);

                        } else {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }


}

package com.multipz.kc.Staff;

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

import com.multipz.kc.Activity.AddAmountStaffActivity;
import com.multipz.kc.Adapter.AmountStaffAdapter;
import com.multipz.kc.Details.AmountStaffDetailActivity;
import com.multipz.kc.Model.AmountStaffModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AmountStaff extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    ListView work_type_list;
    Context context;
    Shared shared;
    MyAsyncTask.AsyncInterface asyncInterface;
    private AmountStaffAdapter adapter;
    private ArrayList<AmountStaffModel> userList;
    FloatingActionButton fabAdd;
    private int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_staff);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("સ્ટાફની રકમ");

        context = this;
        shared = new Shared(context);
        reference();
        init();


    }

    private void init() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAmountStaffActivity.class);
                startActivity(intent);
            }
        });


        work_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AmountStaffModel item = userList.get(i);
                Intent intent = new Intent(context, AmountStaffDetailActivity.class);
                intent.putExtra("staff_amount_id", item.getStaff_amount_id());
                intent.putExtra("user_id", item.getUser_id());
                intent.putExtra("project_id", item.getProject_id());
                intent.putExtra("bank_id", item.getBank_id());
                intent.putExtra("amount", item.getAmount());
                intent.putExtra("amount_date", Constant_method.getMyDate(item.getAmount_date()));
                intent.putExtra("payment_type", item.getPayment_type());
                intent.putExtra("payment_info", item.getPayment_info());
                intent.putExtra("userName", item.getUserName());
                intent.putExtra("work_type", item.getWork_type());
                intent.putExtra("side_sort_name", item.getSide_sort_name());
                intent.putExtra("bankName", item.getBankName());
                startActivity(intent);
            }
        });

        work_type_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    paramforDataAmtStff();
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

    @Override
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        paramforDataAmtStff();
    }

    private void paramforDataAmtStff() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getDataAmtToStaff\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_AMount_staff);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void reference() {
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        work_type_list = (ListView) findViewById(R.id.work_type_list);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponseService(String response, int flag) {
        if (flag == Config.APi_Get_AMount_staff) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                    AmountStaffModel amountStaff = new AmountStaffModel();
                    amountStaff.setStaff_amount_id(jsonObject1.getString("staff_amount_id"));
                    amountStaff.setUser_id(jsonObject1.getString("user_id"));
                    amountStaff.setProject_id(jsonObject1.getString("project_id"));
                    amountStaff.setBank_id(jsonObject1.getString("bank_id"));
                    amountStaff.setAmount(jsonObject1.getString("amount"));
                    amountStaff.setAmount_date(jsonObject1.getString("amount_date"));
                    amountStaff.setPayment_type(jsonObject1.getString("payment_type"));
                    amountStaff.setPayment_info(jsonObject1.getString("payment_info"));
                    amountStaff.setUserName(jsonObject1.getString("userName"));
                    amountStaff.setWork_type(jsonObject1.getString("work_type"));
                    amountStaff.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    amountStaff.setBankName(jsonObject1.getString("bankName"));
                    userList.add(amountStaff);
                }
                adapter = new AmountStaffAdapter(context, userList);
                work_type_list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.multipz.kc.GeneralExpense;

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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.multipz.kc.Activity.AddKCExpenseActivity;
import com.multipz.kc.Activity.AddSiteGeneralExpenseActivity;
import com.multipz.kc.Adapter.KCExpenseAdapter;
import com.multipz.kc.Adapter.SiteGeneralExpenseAdapter;
import com.multipz.kc.Details.KCExpenseDetailActivity;
import com.multipz.kc.Details.SiteGeneralExpenseDetailActivity;
import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SiteGeneralExpense extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    ListView work_type_list;
    Context context;
    Shared shared;
    MyAsyncTask.AsyncInterface asyncInterface;
    private SiteGeneralExpenseAdapter adapter;
    private ArrayList<KCExpenseModel> userList;
    RelativeLayout rl_start_dates;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    FloatingActionButton fabAdd;
    private int pagecount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_general_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("સાઇટ નો ખર્ચ");
        context = this;
        shared = new Shared(context);
        reference();
        init();

    }

    private void init() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddSiteGeneralExpenseActivity.class);
                startActivity(intent);
            }
        });


        work_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                KCExpenseModel item = userList.get(i);
                Intent intent = new Intent(context, SiteGeneralExpenseDetailActivity.class);
                intent.putExtra("side_gen_exp_id", item.getSide_gen_exp_id());
                intent.putExtra("detail", item.getDetail());
                intent.putExtra("side_sort_name", item.getSide_sort_name());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("amount", item.getAmount());
                startActivity(intent);
            }
        });


        //   getParamforsiteGnExpData();

        work_type_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamforsiteGnExpData();
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

    private void getParamforsiteGnExpData() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"user_id\":\"" + shared.getUserId() + "\",\"page\":\"" + pagecount + "\",\"action\":\"getSiteGenExpData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_Site_general_Expense);
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
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        getParamforsiteGnExpData();
    }

    @Override
    public void onResponseService(String response, int flag) {
        int sucess;
        if (flag == Config.APi_Get_Site_general_Expense) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
                if (sucess == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        KCExpenseModel staffExpenseModel = new KCExpenseModel();
                        staffExpenseModel.setSide_gen_exp_id(c.getString("side_gen_exp_id"));
                        staffExpenseModel.setCdate(c.getString("cdate"));
                        staffExpenseModel.setSide_sort_name(c.getString("side_sort_name"));
                        staffExpenseModel.setDetail(c.getString("detail"));
                        staffExpenseModel.setAmount(c.getString("amount"));
                        userList.add(staffExpenseModel);
                    }
                    adapter = new SiteGeneralExpenseAdapter(context, userList);
                    work_type_list.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

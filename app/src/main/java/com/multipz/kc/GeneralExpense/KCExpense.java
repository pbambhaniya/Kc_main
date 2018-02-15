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
import android.widget.Toast;

import com.multipz.kc.Activity.AddKCExpenseActivity;
import com.multipz.kc.Adapter.KCExpenseAdapter;
import com.multipz.kc.Details.KCExpenseDetailActivity;
import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class KCExpense extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    ListView work_type_list;
    Context context;
    Shared shared;
    private KCExpenseAdapter adapter;
    private ArrayList<KCExpenseModel> userList;
    FloatingActionButton fabAdd;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private boolean flag_loading = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_expense);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("કેસી નો ખર્ચ ");
        context = this;
        shared = new Shared(context);
        reference();
        init();
    }

    private void init() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddKCExpenseActivity.class);
                startActivity(intent);
            }
        });


        work_type_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamKcGeneralExp();

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;

            }
        });


        work_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                KCExpenseModel item = userList.get(i);
                Intent intent = new Intent(context, KCExpenseDetailActivity.class);
                intent.putExtra("kc_exp_id", item.getKc_exp_id());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("detail", item.getDetail());
                intent.putExtra("amount", item.getAmount());
                startActivity(intent);
            }
        });
    }

    private void reference() {
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        work_type_list = (ListView) findViewById(R.id.work_type_list);
    }

    private void getParamKcGeneralExp() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getKcExpData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_KC_Expense);
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
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        getParamKcGeneralExp();
    }

    @Override
    public void onResponseService(String response, int flag) {

        int sucess;
        if (flag == Config.APi_Get_KC_Expense) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
                if (sucess == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        KCExpenseModel staffExpenseModel = new KCExpenseModel();
                        staffExpenseModel.setKc_exp_id(c.getString("kc_exp_id"));
                        staffExpenseModel.setDetail(c.getString("detail"));
                        staffExpenseModel.setAmount(c.getString("amount"));
                        staffExpenseModel.setCdate(c.getString("cdate"));
                        userList.add(staffExpenseModel);
                    }
                    adapter = new KCExpenseAdapter(context, userList);
                    work_type_list.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

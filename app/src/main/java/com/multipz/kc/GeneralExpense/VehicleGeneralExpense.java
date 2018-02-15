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
import com.multipz.kc.Activity.AddSiteGeneralExpenseActivity;
import com.multipz.kc.Activity.AddVehicleGeneralExpenseActivity;
import com.multipz.kc.Adapter.KCExpenseAdapter;
import com.multipz.kc.Adapter.SiteGeneralExpenseAdapter;
import com.multipz.kc.Adapter.VehicleGeneralExpenceAdapter;
import com.multipz.kc.Details.KCExpenseDetailActivity;
import com.multipz.kc.Details.SiteGeneralExpenseDetailActivity;
import com.multipz.kc.Details.VehicleGeneralEXpenseDetailActivity;
import com.multipz.kc.Model.KCExpenseModel;
import com.multipz.kc.Model.VehicleGeneralExpenseModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleGeneralExpense extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    ListView work_type_list;
    Context context;
    Shared shared;
    MyAsyncTask.AsyncInterface asyncInterface;
    private VehicleGeneralExpenceAdapter adapter;
    private ArrayList<VehicleGeneralExpenseModel> userList;
    FloatingActionButton fab_add;
    private int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_general_expense);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.vehicle_general_expense));
        context = this;
        shared = new Shared(context);

        reference();
        init();


    }

    private void init() {

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddVehicleGeneralExpenseActivity.class);
                startActivity(intent);
            }
        });
        work_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VehicleGeneralExpenseModel item = userList.get(i);
                Intent intent = new Intent(context, VehicleGeneralEXpenseDetailActivity.class);
                intent.putExtra("vehicle_gen_exp_id", item.getVehicle_gen_exp_id());
                intent.putExtra("detail", item.getDetail());
                intent.putExtra("amount", item.getAmount());
                intent.putExtra("vehicle_no", item.getVehicle_no());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("exp_type", item.getEtype());
                intent.putExtra("vehicle_type", item.getVehicle_type());
                startActivity(intent);
            }
        });


        work_type_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamForVehicalGenExpData();

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

    private void getParamForVehicalGenExpData() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"user_id\":\"" + shared.getUserId() + "\",\"page\":\"" + pagecount + "\",\"action\":\"getVehicleGenExpData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_vehicle_general_Expense);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void reference() {
        work_type_list = (ListView) findViewById(R.id.work_type_list);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);
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
        getParamForVehicalGenExpData();
    }

    @Override
    public void onResponseService(String response, int flag) {
        if (flag == Config.APi_Get_vehicle_general_Expense) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject c = jsonArray.getJSONObject(i1);
                    VehicleGeneralExpenseModel vehicleGeneralExpenseModel = new VehicleGeneralExpenseModel();
                    vehicleGeneralExpenseModel.setVehicle_gen_exp_id(c.getString("vehicle_gen_exp_id"));
                    vehicleGeneralExpenseModel.setDetail(c.getString("detail"));
                    vehicleGeneralExpenseModel.setAmount(c.getString("amount"));
                    vehicleGeneralExpenseModel.setVehicle_no(c.getString("vehicle_no"));
                    vehicleGeneralExpenseModel.setCdate(c.getString("cdate"));
                    vehicleGeneralExpenseModel.setEtype(c.getString("etype"));
                    vehicleGeneralExpenseModel.setVehicle_type(c.getString("vehicle_type"));
                    userList.add(vehicleGeneralExpenseModel);
                }

                adapter = new VehicleGeneralExpenceAdapter(context, userList);
                work_type_list.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

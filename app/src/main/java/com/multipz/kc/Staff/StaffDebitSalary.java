package com.multipz.kc.Staff;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Activity.AddStaffDebitSalaryActivity;
import com.multipz.kc.Adapter.StaffDebitSalaryAdapter;
import com.multipz.kc.Model.StaffDebitSalaryModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaffDebitSalary extends AppCompatActivity implements MyAsyncTask.AsyncInterface, ItemClickListener {

    RecyclerView staff_salary_list;
    Context context;
    Shared shared;
    private StaffDebitSalaryAdapter adapter;
    private ArrayList<StaffDebitSalaryModel> userList;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    LinearLayoutManager layoutManager;
    private boolean userScrolled = true;
    private int lastVisibleItem, firstVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_debit_salary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("સ્ટાફ નો ઉધાર પગાર");
        context = this;
        shared = new Shared(context);

        refrence();
        init();

    }

    private void init() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddStaffDebitSalaryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        staff_salary_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        getStaffDebitSalary();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void getStaffDebitSalary() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getDebitSalaryData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_staff_debit_salary);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void refrence() {
        staff_salary_list = (RecyclerView) findViewById(R.id.staff_salary_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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


    @Override
    public void onResponseService(String response, int flag) {
        int sucess;
        String msg;
        if (flag == Config.APi_Get_staff_debit_salary) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
                if (sucess == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        StaffDebitSalaryModel staffDebitSalaryModel = new StaffDebitSalaryModel();
                        staffDebitSalaryModel.setSallary_id(c.getString("sallary_id"));
                        staffDebitSalaryModel.setUser_id(c.getString("user_id"));
                        staffDebitSalaryModel.setBank_id(c.getString("bank_id"));
                        staffDebitSalaryModel.setPayment_type(c.getString("payment_type"));
                        staffDebitSalaryModel.setPayment_info(c.getString("payment_info"));
                        staffDebitSalaryModel.setDetail(c.getString("detail"));
                        staffDebitSalaryModel.setAmount(c.getString("amount"));
                        staffDebitSalaryModel.setNet_salary(c.getString("net_salary"));
                        staffDebitSalaryModel.setDate(c.getString("date"));
                        staffDebitSalaryModel.setBankName(c.getString("bankName"));
                        staffDebitSalaryModel.setUserName(c.getString("userName"));
                        staffDebitSalaryModel.setSalary_based_type(c.getString("salary_based_type"));
                        userList.add(staffDebitSalaryModel);
                    }

                    adapter = new StaffDebitSalaryAdapter(context, userList);
                    layoutManager = new LinearLayoutManager(this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    staff_salary_list.setLayoutManager(layoutManager);
                    staff_salary_list.setAdapter(adapter);
                    adapter.setClickListener(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_staff_debit_salary_delete) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                sucess = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (sucess == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void delete(String id) {
        if (Constant_method.checkConn(context)) {
            String param = "{\"sallary_id\": \"" + id + "\",\"optFlag\":\"d\",\"action\": \"editDeleteDebitSalary\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, (Activity) context, param, Config.APi_staff_debit_salary_delete);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        getStaffDebitSalary();
    }

    @Override
    public void itemClicked(View View, int position) {
        StaffDebitSalaryModel data = userList.get(position);
        if (View.getId() == R.id.img_delete) {
            delete(data.getSallary_id());
            remove(position);
        }

    }

    private void remove(int pos) {
        userList.remove(pos);
        adapter.notifyItemRemoved(pos);
        adapter.notifyItemRangeChanged(pos, userList.size());
    }


}

package com.multipz.kc.Staff;

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

import com.multipz.kc.Activity.AddStaffsActivity;
import com.multipz.kc.Adapter.StaffAdapter;
import com.multipz.kc.Adapter.WorkTypeAdapter;
import com.multipz.kc.Details.StaffDetailActivity;
import com.multipz.kc.Model.StaffModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Staffs extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    ListView work_type_list;
    Context context;
    Shared shared;
    MyAsyncTask.AsyncInterface asyncInterface;
    private StaffAdapter adapter;
    private ArrayList<StaffModel> userList;
    FloatingActionButton fabAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("સ્ટાફ");
        work_type_list = (ListView) findViewById(R.id.work_type_list);

        context = this;
        shared = new Shared(context);


        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddStaffsActivity.class);
                startActivity(intent);
            }
        });


        work_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                StaffModel item = userList.get(i);
                Intent intent = new Intent(context, StaffDetailActivity.class);
                intent.putExtra("user_id", item.getUser_id());
                intent.putExtra("name", item.getName());
                intent.putExtra("mobile", item.getMobile());
                intent.putExtra("profile_img", item.getProfile_img());
                intent.putExtra("proof_img", item.getProof_img());
                intent.putExtra("salary", item.getSalary());
                intent.putExtra("work_type", item.getWork_type());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("password", item.getPassword());
                intent.putExtra("salary_based_type", item.getSalary_based_type());
                intent.putExtra("profile_img", item.getProfile_img());
                intent.putExtra("proof_img", item.getProof_img());
                startActivity(intent);
            }
        });

    }

    private void getStaff() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getStaffData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_staff);
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
        userList = new ArrayList<>();
        getStaff();
    }

    @Override
    public void onResponseService(String response, int flag) {
        if (flag == Config.APi_Get_staff) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                    StaffModel staff = new StaffModel();
                    staff.setUser_id(jsonObject1.getString("user_id"));
                    staff.setName(jsonObject1.getString("name"));
                    staff.setWork_type_id(jsonObject1.getString("work_type_id"));
                    staff.setMobile(jsonObject1.getString("mobile"));
                    staff.setProfile_img(jsonObject1.getString("profile_img"));
                    staff.setProof_img(jsonObject1.getString("proof_img"));
                    staff.setPassword(jsonObject1.getString("password"));
                    staff.setCdate(jsonObject1.getString("cdate"));
                    staff.setUser_type(jsonObject1.getString("user_type"));
                    staff.setSalary(jsonObject1.getString("salary"));
                    String salarybase = jsonObject1.getString("salary_based_type");
                    if (salarybase.equals("F")) {
                        salarybase = "Fix";
                    } else if (salarybase.equals("D")) {
                        salarybase = "Daily Base";
                    }
                    staff.setSalary_based_type(salarybase);
                    staff.setWork_type(jsonObject1.getString("work_type"));
                    userList.add(staff);

                }

                adapter = new StaffAdapter(context, userList);
                work_type_list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

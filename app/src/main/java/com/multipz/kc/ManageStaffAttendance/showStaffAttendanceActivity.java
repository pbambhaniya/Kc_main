package com.multipz.kc.ManageStaffAttendance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.StaffAttendanceBaseTypeAdapter;
import com.multipz.kc.Adapter.showAttendanceAdapter;
import com.multipz.kc.Model.GetStaffAsPerTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class showStaffAttendanceActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private RecyclerView listviewShowStaffAttendance;
    private Context mContext;
    private ArrayList<GetStaffAsPerTypeModel> listAttendance;
    private showAttendanceAdapter adapter;
    private int pagecount = 1;
    private int lastVisibleItem, totalItemCount, firstVisible;
    private LinearLayoutManager layoutManager;
    private boolean userScrolled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_staff_attendance);
        mContext = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Show Staff Attendance");
        listAttendance = new ArrayList<>();
        refernce();
        init();

    }

    private void refernce() {
        listviewShowStaffAttendance = (RecyclerView) findViewById(R.id.listviewShowStaffAttendance);
    }

    private void init() {

        adapter = new showAttendanceAdapter(getApplicationContext(), listAttendance);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listviewShowStaffAttendance.setLayoutManager(layoutManager);

        getparamshowStaffAttendance();
        listviewShowStaffAttendance.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                lastVisibleItem = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        getparamshowStaffAttendance();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    private void getparamshowStaffAttendance() {
        if (Constant_method.checkConn(mContext)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"" + Config.getStaffAtten + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, showStaffAttendanceActivity.this, param, Config.APi_getStaffAtten);
            myAsyncTask.execute();
        } else {
            Toast.makeText(showStaffAttendanceActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                Intent intent = new Intent(this, StaffAttendanceActivity.class);      //I suppose they are in same package
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_getStaffAtten) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                GetStaffAsPerTypeModel getStaffAsPerTypeModel = null;
                if (success == 1) {
                    JSONArray array = jsonObject.getJSONArray("data");

                    for (int i = 0; i < array.length(); i++) {
                        getStaffAsPerTypeModel = new GetStaffAsPerTypeModel();
                        JSONObject obj = array.getJSONObject(i);
                        getStaffAsPerTypeModel.setAttendance_id(obj.getString("attendance_id"));
                        getStaffAsPerTypeModel.setUser_id(obj.getString("user_id"));
                        getStaffAsPerTypeModel.setAmount(obj.getString("amount"));
                        getStaffAsPerTypeModel.setName(obj.getString("name"));
                        getStaffAsPerTypeModel.setDate(obj.getString("date"));
                        getStaffAsPerTypeModel.setWork_type_id(obj.getString("work_type_id"));
                        getStaffAsPerTypeModel.setMobile(obj.getString("mobile"));
                        getStaffAsPerTypeModel.setProfile_img(obj.getString("profile_img"));
                        getStaffAsPerTypeModel.setProof_img(obj.getString("proof_img"));
                        getStaffAsPerTypeModel.setPassword(obj.getString("password"));
                        getStaffAsPerTypeModel.setUser_type(obj.getString("user_type"));
                        getStaffAsPerTypeModel.setSalary(obj.getString("salary"));
                        getStaffAsPerTypeModel.setCreated_date(obj.getString("created_date"));
                        getStaffAsPerTypeModel.setModified_date(obj.getString("modified_date"));
                        getStaffAsPerTypeModel.setIs_status(obj.getString("is_status"));
                        getStaffAsPerTypeModel.setIs_delete(obj.getString("is_delete"));
                        getStaffAsPerTypeModel.setWork_type(obj.getString("work_type"));

                        listAttendance.add(getStaffAsPerTypeModel);
                    }

                    listviewShowStaffAttendance.setAdapter(adapter);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

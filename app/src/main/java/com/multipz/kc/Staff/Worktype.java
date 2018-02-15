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

import com.multipz.kc.Activity.AddWorkTypeActivity;
import com.multipz.kc.Adapter.WorkTypeAdapter;
import com.multipz.kc.Details.WorkTypeDetailActivity;
import com.multipz.kc.Model.WorkTypeModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Worktype extends AppCompatActivity {
    ListView work_type_list;
    Context context;
    Shared shared;
    MyAsyncTask.AsyncInterface asyncInterface;
    private WorkTypeAdapter adapter;
    private ArrayList<WorkTypeModel> userList;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worktype);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("કામ નો પ્રકાર ");
        work_type_list = (ListView) findViewById(R.id.work_type_list);

        context = this;
        shared = new Shared(context);
        callServiceResponse();


        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddWorkTypeActivity.class);
                startActivity(intent);
            }
        });

        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getWorkTypeData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.APi_Get_Worktype);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        work_type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                WorkTypeModel item = userList.get(i);
                Intent intent = new Intent(context, WorkTypeDetailActivity.class);
                intent.putExtra("work_type_id", item.getWork_type_id());
                intent.putExtra("work_type", item.getWork_type());
                intent.putExtra("cdate",Constant_method.getMyDate(item.getCdate()));
                startActivity(intent);
            }
        });

    }

    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {

                if (flag == Config.APi_Get_Worktype) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        userList = new ArrayList<>();

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            WorkTypeModel worktypeModel = new WorkTypeModel();
                            worktypeModel.setWork_type_id(c.getString("work_type_id"));
                            worktypeModel.setWork_type(c.getString("work_type"));
                            worktypeModel.setCdate(c.getString("cdate"));
                            worktypeModel.setCreated_date(c.getString("created_date"));
                            worktypeModel.setModified_date(c.getString("modified_date"));
                            worktypeModel.setIs_status(c.getString("is_status"));
                            worktypeModel.setIs_delete(c.getString("is_delete"));
                            worktypeModel.setWork_type_id(c.getString("work_type_id"));
                            worktypeModel.setWork_type(c.getString("work_type"));
                            worktypeModel.setCreated_date(c.getString("created_date"));
                            userList.add(worktypeModel);

                        }

                        adapter = new WorkTypeAdapter(context, userList);
                        work_type_list.setAdapter(adapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();

        String param = "{\"action\":\"getWorkTypeData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.APi_Get_Worktype);
        myAsyncTask.execute();
    }
}

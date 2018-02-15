package com.multipz.kc.Project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Activity.AddProjectReturnActivity;
import com.multipz.kc.Adapter.ProjectReturnsAdapter;
import com.multipz.kc.Details.ProjectReturnDetailActivity;
import com.multipz.kc.Model.ProjectReturnsModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectReturns extends AppCompatActivity {
    ListView project;
    private ProjectReturnsAdapter adapter;
    Shared shared;
    String name, budget, date, location;
    MyAsyncTask.AsyncInterface asyncInterface;
    Context context;
    FloatingActionButton fabAdd;
    ArrayList<ProjectReturnsModel> userList;

    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                int success;
                String msg;

                if (flag == Config.API_Get_Project_return) {
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
                                ProjectReturnsModel projectModel = new ProjectReturnsModel();
                                projectModel.setIs_complete(c.getString("is_complete"));
                                projectModel.setProject_id(c.getString("project_id"));
                                projectModel.setSide_sort_name(c.getString("side_sort_name"));
                                projectModel.setBudget(c.getString("budget"));
                                projectModel.setDeposite(c.getString("deposite"));
                                projectModel.setSd(c.getString("sd"));
                                projectModel.setSddate(c.getString("sddate"));
                                projectModel.setSdstatus(c.getString("sdstatus"));
                                projectModel.setSdrid(c.getString("sdrid"));
                                projectModel.setTds(c.getString("tds"));
                                projectModel.setTdsdate(c.getString("tdsdate"));
                                projectModel.setTdsstatus(c.getString("tdsstatus"));
                                projectModel.setTdsrid(c.getString("tdsrid"));
                                projectModel.setFmg(c.getString("fmg"));
                                projectModel.setCdate((c.getString("cdate")));
                                projectModel.setFmgdate(c.getString("fmgdate"));
                                projectModel.setFmgstatus(c.getString("fmgstatus"));
                                projectModel.setFmgrid(c.getString("fmgrid"));
                                userList.add(projectModel);
                            }
                            adapter = new ProjectReturnsAdapter(context, userList);
                            project.setAdapter(adapter);
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_returns);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("પ્રોજેક્ટ રિટર્ન્સ");
        callServiceResponse();
        context = this;
        shared = new Shared(context);

        refrence();
        init();
    }

    private void init() {
        String param = "{\"action\":\"getProjectReturn\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_Get_Project_return);
        myAsyncTask.execute();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProjectReturnActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProjectReturnsModel item = userList.get(i);
                Intent intent = new Intent(context, ProjectReturnDetailActivity.class);
                // intent.putExtra("project_return_id", item.getProject_return_id());
                intent.putExtra("side_sort_name", item.getSide_sort_name());
                intent.putExtra("project_id", item.getProject_id());
                intent.putExtra("sd", item.getSd());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("fmg", item.getFmg());
                intent.putExtra("tds", item.getTds());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void refrence() {
        project = (ListView) findViewById(R.id.listview);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }

    @Override
    public void onResume() {
        super.onResume();
        String param = "{\"action\":\"getProjectReturn\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_Get_Project_return);
        myAsyncTask.execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

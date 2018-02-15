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

import com.multipz.kc.Activity.AddProjectActivity;
import com.multipz.kc.Adapter.ProjectAdapter;
import com.multipz.kc.Details.ProjectDetailActivity;
import com.multipz.kc.Model.ProjectModel;


import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Projects extends AppCompatActivity {
    ListView project;
    private ProjectAdapter adapter;
    Shared shared;
    String name, budget, date, location;
    MyAsyncTask.AsyncInterface asyncInterface;
    Context context;
    FloatingActionButton fabAdd;
    ArrayList<ProjectModel> userList;

    private void callServiceResponse() {
        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                if (flag == Config.API_TOKEN_GET_PROJ) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Log.e("Responce", jsonObject.toString());
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        userList = new ArrayList<>();

                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);

                            ProjectModel projectModel = new ProjectModel();
                            projectModel.setProject_id(c.getString("project_id"));
                            projectModel.setSide_sort_name(c.getString("side_sort_name"));
                            projectModel.setName_of_work(c.getString("name_of_work"));
                            projectModel.setStart_date(c.getString("start_date"));
                            projectModel.setLocation(c.getString("location"));
                            projectModel.setEnd_date(c.getString("end_date"));
                            projectModel.setBudget(c.getString("budget"));
                            projectModel.setCdate(c.getString("cdate"));
                            projectModel.setDeposite(c.getString("deposite"));

                            projectModel.setIs_complete(c.getString("is_complete"));


                            userList.add(projectModel);
                        }

                        adapter = new ProjectAdapter(context, userList);
                        project.setAdapter(adapter);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        setTitle("પ્રોજેક્ટ");
        //getActivity().setTitle("Project");
        callServiceResponse();
        context = this;
        shared = new Shared(context);
        project = (ListView) findViewById(R.id.listview);
        String param = "{\"action\":\"getProjectData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_PROJ);
        myAsyncTask.execute();

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProjectActivity.class);
                startActivity(intent);
            }
        });

        project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ProjectModel item = userList.get(i);
                Intent intent = new Intent(context, ProjectDetailActivity.class);
                intent.putExtra("project_id", item.getProject_id());
                intent.putExtra("side_sort_name", item.getSide_sort_name());
                intent.putExtra("name_of_work", item.getName_of_work());
                intent.putExtra("budget", item.getBudget());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("deposite", item.getDeposite());
                intent.putExtra("start_date", item.getStart_date());
                intent.putExtra("end_date", item.getEnd_date());
                intent.putExtra("location", item.getLocation());
                intent.putExtra("is_complete", item.getIs_complete());
                intent.putExtra("got_return", item.getGot_return());
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        String param = "{\"action\":\"getProjectData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_PROJ);
        myAsyncTask.execute();
    }
}

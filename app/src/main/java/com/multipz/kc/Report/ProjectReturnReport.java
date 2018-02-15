package com.multipz.kc.Report;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.multipz.kc.Adapter.ProjectReturnReportAdapter;
import com.multipz.kc.Adapter.VehicleExpenseItemAdapter;
import com.multipz.kc.Model.ProjectReturnReportModel;
import com.multipz.kc.Model.VehicleExReportItemModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProjectReturnReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    ListView list_project_return;
    Context context;
    Shared shared;
    private ArrayList<ProjectReturnReportModel> list;
    private ProjectReturnReportAdapter adapter;
    int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_return_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Project_return_report));
        context = this;
        shared = new Shared(context);

        refrence();


        getProjectReturn();


        list_project_return.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getProjectReturn();
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

    private void getProjectReturn() {
        if (Constant_method.checkConn(context)) {
            String params = "{\"page\":\"" + pagecount + "\",\"id\":\"" + shared.getUserId().toString() + "\",\"action\" :\"projectReturnReport\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, ProjectReturnReport.this, params, Config.API_Project_return_report);
            myAsyncTask.execute();
        } else {
            Toast.makeText(ProjectReturnReport.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void refrence() {
        list_project_return = (ListView) findViewById(R.id.list_project_return);
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
        int success;
        String msg;
        if (flag == Config.API_Project_return_report) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    list = new ArrayList<>();
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        ProjectReturnReportModel model = new ProjectReturnReportModel();
                        model.setSide_sort_name(c.getString("side_sort_name"));
                        model.setBudget(c.getString("budget"));
                        model.setDeposite(c.getString("deposite"));
                        model.setSd(c.getString("sd"));
                        model.setSddate(c.getString("sddate"));
                        model.setSdstatus(c.getString("sdstatus"));
                        model.setSdrid(c.getString("sdrid"));
                        model.setTds(c.getString("tds"));
                        model.setTdsdate(c.getString("tdsdate"));
                        model.setTdsstatus(c.getString("tdsstatus"));
                        model.setTdsrid(c.getString("tdsrid"));
                        model.setStart_date(c.getString("start_date"));
                        model.setFmg(c.getString("fmg"));
                        model.setFmgdate(c.getString("fmgdate"));
                        model.setFmgstatus(c.getString("fmgstatus"));
                        model.setFmgrid(c.getString("fmgrid"));
                        model.setTotalpay(c.getString("totalpay"));
                        model.setTotalback(c.getString("totalback"));
                        model.setRemaining(c.getString("remaining"));


                        list.add(model);
                    }
                    adapter = new ProjectReturnReportAdapter(context, list);
                    list_project_return.setAdapter(adapter);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

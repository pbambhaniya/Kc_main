package com.multipz.kc.Company;

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

import com.multipz.kc.Activity.AddCompanyImportActivity;
import com.multipz.kc.Adapter.CompanyImporAdapter;
import com.multipz.kc.Details.CompanyImportDetailActivity;
import com.multipz.kc.Model.CompanyImportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompanyImport extends AppCompatActivity implements MyAsyncTask.AsyncInterface {


    ListView company_mst_list;
    Shared shared;
    Context context;
    private CompanyImporAdapter adapter;
    FloatingActionButton fabAdd;
    ArrayList<CompanyImportModel> userList;
    private int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_import);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_Import));
        context = this;

        shared = new Shared(context);
        reference();
        init();


    }

    private void init() {

        company_mst_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CompanyImportModel item = userList.get(i);
                Intent intent = new Intent(context, CompanyImportDetailActivity.class);
                intent.putExtra("comp_import_id", item.getComp_import_id());
                intent.putExtra("companyName", item.getCompanyName());
                intent.putExtra("challan_no", item.getChallan_no());
                intent.putExtra("amount", item.getAmount());
                intent.putExtra("detail", item.getDetail());
                intent.putExtra("date", Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("challan_image", item.getChallan_image());
                intent.putExtra("side_sort_name", item.getSide_sort_name());
                intent.putExtra("material_type", item.getMaterial_type());
                intent.putExtra("weight_bags", item.getWeight_bags());
                intent.putExtra("weight_unit", item.getWeight_unit());
                intent.putExtra("price_ton", item.getPrice_per_ton());
                intent.putExtra("total_price_ton", item.getTotal_price_per_ton());

                startActivity(intent);
            }
        });
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddCompanyImportActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        company_mst_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getcompanyimportdata();
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

    @Override
    protected void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        getcompanyimportdata();
    }

    private void getcompanyimportdata() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getCompImportData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.API_Get_Company_import);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void reference() {
        company_mst_list = (ListView) findViewById(R.id.company_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponseService(String response, int flag) {
        if (flag == Config.API_Get_Company_import) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                    CompanyImportModel companyimport = new CompanyImportModel();
                    companyimport.setComp_import_id(jsonObject1.getString("comp_import_id"));
                    companyimport.setChallan_no(jsonObject1.getString("challan_no"));
                    companyimport.setChallan_image(jsonObject1.getString("challan_image"));
                    companyimport.setAmount(jsonObject1.getString("amount"));
                    companyimport.setDetail(jsonObject1.getString("detail"));
                    companyimport.setCdate(jsonObject1.getString("cdate"));
                    companyimport.setCompanyName(jsonObject1.getString("companyName"));
                    companyimport.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    companyimport.setMaterial_type(jsonObject1.getString("material_type"));
                    companyimport.setWeight_bags(jsonObject1.getString("weight_bags"));
                    companyimport.setWeight_unit(jsonObject1.getString("weight_unit"));
                    companyimport.setPrice_per_ton(jsonObject1.getString("price_per_ton"));
                    companyimport.setTotal_price_per_ton(jsonObject1.getString("total_price_per_ton"));
                    userList.add(companyimport);
                }
                adapter = new CompanyImporAdapter(context, userList);
                company_mst_list.setAdapter(adapter);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

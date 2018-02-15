package com.multipz.kc.Asset;

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

import com.multipz.kc.Activity.AddMaterialActivity;
import com.multipz.kc.Adapter.MaterialAdapter;
import com.multipz.kc.Details.MaterialDetailActivity;
import com.multipz.kc.Model.MaterialModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MaterialType extends AppCompatActivity {

    Shared shared;
    private MaterialAdapter adapter;
    ListView assets_list;
    Context context;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    ArrayList<MaterialModel> userList;

    private void callServiceResponse() {

        asyncInterface = new MyAsyncTask.AsyncInterface() {
            @Override
            public void onResponseService(String response, int flag) {
                int success;
                String msg;

                if (flag == Config.API_TOKEN_GET_Material) {

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
                                MaterialModel assetsModel = new MaterialModel();
                                assetsModel.setMaterial_type(c.getString("material_type"));
                                assetsModel.setMaterial_id(c.getString("material_id"));
                                assetsModel.setCdate(c.getString("cdate"));
                                userList.add(assetsModel);
                            }
//                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            adapter = new MaterialAdapter(context, userList);
                            assets_list.setAdapter(adapter);
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
        setContentView(R.layout.activity_asset_type);


        assets_list = (ListView) findViewById(R.id.assets_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Material_Type));
        context = this;
        callServiceResponse();
        shared = new Shared(context);

        assets_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MaterialModel item = userList.get(i);
                Intent intent = new Intent(context, MaterialDetailActivity.class);
                intent.putExtra("date",Constant_method.getMyDate(item.getCdate()));
                intent.putExtra("material_type", item.getMaterial_type());
                intent.putExtra("material_id", item.getMaterial_id());
                startActivity(intent);
            }
        });

        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"getMaterialData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_Material);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddMaterialActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        String param = "{\"action\":\"getMaterialData\"}";
        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, asyncInterface, param, Config.API_TOKEN_GET_Material);
        myAsyncTask.execute();

    }
}
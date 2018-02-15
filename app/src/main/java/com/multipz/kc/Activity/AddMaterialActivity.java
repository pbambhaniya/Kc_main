package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Model.MaterialModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddMaterialActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText assets_name;
    Button add_assets, update_assets, delete_assets;
    Context context;
    String id;
TextView txt_start_date;
    RelativeLayout rl_start_dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_asset);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Material));
        context = this;

        assets_name = (EditText) findViewById(R.id.asset_name);
        add_assets = (Button) findViewById(R.id.add_assets);
        update_assets = (Button) findViewById(R.id.update_assets);
        delete_assets = (Button) findViewById(R.id.delete_assets);
        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);

        if (getIntent().getBooleanExtra("Update", false)) {
            assets_name.setText(getIntent().getExtras().getString("material_type"));
            id = getIntent().getStringExtra("material_id");
            txt_start_date.setText(getIntent().getStringExtra("date"));
            add_assets.setVisibility(View.GONE);
            update_assets.setVisibility(View.VISIBLE);
        }


        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddMaterialActivity.this, txt_start_date);
            }
        });

        add_assets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eassets,date;
                eassets = assets_name.getText().toString();
                date=txt_start_date.getText().toString();

                if (eassets.contentEquals("")) {
                    Toast.makeText(AddMaterialActivity.this, "Enter Material Name", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {

                    String param = "{\"id\":\"\",\"material_type\":\"" + eassets + "\",\"cdate\":\""+date+"\",\"action\":\"addUpdateMaterial\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddMaterialActivity.this, param, Config.API_TOKEN_ADD_Material);
                    myAsyncTask.execute();
                }
            }
        });

        update_assets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eassets,date;
date=txt_start_date.getText().toString();
                eassets = assets_name.getText().toString();
                if (eassets.contentEquals("")) {
                    Toast.makeText(AddMaterialActivity.this, "Enter Material Name", Toast.LENGTH_SHORT).show();
                }else if (date.contentEquals(""))
                {

                }
                else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"material_type\":\"" + eassets + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateMaterial\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddMaterialActivity.this, param, Config.API_TOKEN_UPDATE_Material);
                    myAsyncTask.execute();
                }
            }
        });

//        delete_assets.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String eassets;
//
//                eassets = assets_name.getText().toString();
//                if (eassets.contentEquals("")) {
//                    Toast.makeText(AddMaterialActivity.this, "Enter Material Name", Toast.LENGTH_SHORT).show();
//                } else if (Constant_method.checkConn(context)) {
//
//                    String param = "{\"id\":\"1\",\"optFlag\":\"d\",\"action\":\"editDeleteMaterial\"}";
//                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddMaterialActivity.this, param, Config.API_TOKEN_DELETE_Material);
//                    myAsyncTask.execute();
//                }
//            }
//        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_TOKEN_ADD_Material) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    assets_name.setText("");
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_UPDATE_Material) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    ArrayList<MaterialModel> userList = new ArrayList<>();
                    MaterialModel material = new MaterialModel();
                    material.setMaterial_id(jsonObject1.getString("material_id"));
                    material.setMaterial_type(jsonObject1.getString("material_type"));
                    userList.add(material);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_DELETE_Material) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


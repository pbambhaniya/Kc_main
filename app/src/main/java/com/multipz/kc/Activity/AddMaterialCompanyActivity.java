package com.multipz.kc.Activity;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddMaterialCompanyActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    Spinner sp_company_name;
    EditText et_asset_id;
    Typeface custom_font;
    Context context;
    Shared shared;
    private ArrayList<SpinnerModel> objects_company;
    Button add_company, update_company, delete_company;






    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material_company);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Material_Company));
        shared = new Shared(this);
        context = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_asset_id = (EditText) findViewById(R.id.et_company_asset_id);
        custom_font = Typeface.createFromAsset(getAssets(), "font/Oxygen-Regular.ttf");
        et_asset_id.setTypeface(custom_font);


        add_company = (Button) findViewById(R.id.add_company);
        update_company = (Button) findViewById(R.id.update_company);
        delete_company = (Button) findViewById(R.id.delete_company);
        add_company.setTypeface(custom_font);

        sp_company_name = (Spinner) findViewById(R.id.sp_company_name);

        objects_company = new ArrayList<>();
        objects_company.add(new SpinnerModel("", "Select Material"));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Material, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("material_id"));
                spinnerModel.setName(staff.getString("material_type"));
                objects_company.add(spinnerModel);
            }
            sp_company_name.setAdapter(new SpinnerAdapter(this, objects_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (getIntent().getBooleanExtra("Update", false)) {
            et_asset_id.setText(getIntent().getExtras().getString("name"));
            setSpinner(sp_company_name, objects_company, getIntent().getStringExtra("matetailName"));
            id = getIntent().getStringExtra("material_comp_id");
            add_company.setVisibility(View.GONE);
            update_company.setVisibility(View.VISIBLE);
        }

        add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String asset, spinner;
                asset = et_asset_id.getText().toString();
                spinner = objects_company.get(sp_company_name.getSelectedItemPosition()).getid();

                if (asset.contentEquals("")) {
                    Toast.makeText(context, "Enter Material Name", Toast.LENGTH_SHORT).show();
                } else if (spinner.contentEquals("")) {
                    Toast.makeText(context, "Select Material", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"material_id\":\"" + spinner + "\",\"name\":\"" + asset + "\",\"action\":\"addUpdateMateComp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddMaterialCompanyActivity.this, param, Config.API_TOKEN_ADD_COMPANY);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        update_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String asset, spinner;
                asset = et_asset_id.getText().toString();
                spinner = objects_company.get(sp_company_name.getSelectedItemPosition()).getid();
                if (asset.contentEquals("")) {
                    Toast.makeText(context, "Enter Material Name", Toast.LENGTH_SHORT).show();
                } else if (spinner.contentEquals("")) {
                    Toast.makeText(context, "Select Material", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"material_id\":\"" + spinner + "\",\"name\":\"" + asset + "\",\"action\":\"addUpdateMateComp\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddMaterialCompanyActivity.this, param, Config.API_TOKEN_UPDATE_COMPANY);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        delete_company.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(Constant_method.checkConn(context)){
//                    String param = "{\"id\":\"1\",\"optFlag\":\"d\",\"action\":\"editDeleteCompany\"}\n";
//                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddMaterialCompanyActivity.this, param, Config.API_TOKEN_UPDATE_COMPANY);
//                    myAsyncTask.execute();
//                } else {
//                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setSpinner(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_TOKEN_ADD_COMPANY) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");

                if (success == 1) {
                    et_asset_id.setText("");
                    sp_company_name.setSelection(0);
                    DrawerActivity.activity.callComponenet();
                }

                msg = jsonObject.getString("msg");

                Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_UPDATE_COMPANY) {
            try {
                JSONObject jsonObject1 = new JSONObject(response);
                Log.e("Responce", jsonObject1.toString());
//                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                success = jsonObject1.getInt("status");
                msg = jsonObject1.getString("msg");
                if (success == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
//                if (success == 1) {
//                    ArrayList<MaterialCompanyModel> userList = new ArrayList<>();
//                    MaterialCompanyModel company = new MaterialCompanyModel();
//                    company.setMaterial_comp_id(jsonObject1.getString("material_comp_id"));
//                    company.setMaterial_id(jsonObject1.getString("material_id"));
//                    company.setName(jsonObject1.getString("name"));
//                    company.setMatetailName(jsonObject1.getString("matetailName"));
//                    userList.add(company);
//                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//                    DrawerActivity.activity.callComponenet();
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_DELETE_COMPANY) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

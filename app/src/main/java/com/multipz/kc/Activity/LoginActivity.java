package com.multipz.kc.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Model.LoginModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText etusername, etpassword;
    Button btnlogin;
    TextView txtforgot, txtwelcome;
    Context context;
    Shared shared;
    LoginModel login;
    String password, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Application.setFontDefault((RelativeLayout) findViewById(R.id.rel_root));


        context = this;
        shared = new Shared(context);
        if (shared.getBoolean(Config.isLogin, false)) {

            try {
                JSONObject job = new JSONObject(shared.getString(Config.CurrentUser, "{}"));
                if (job.getString("user_type").contentEquals("A")) {
                    Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                    startActivity(intent);
                } else if (job.getString("user_type").contentEquals("S")) {
                    Intent i = new Intent(LoginActivity.this, StaffDrawerActivity.class);
                    startActivity(i);
                }
                finish();
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etusername = (EditText) findViewById(R.id.etusername);
        etpassword = (EditText) findViewById(R.id.etpassword);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        txtforgot = (TextView) findViewById(R.id.txtforgot);
        txtwelcome = (TextView) findViewById(R.id.txtwelcome);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etusername.getText().toString();
                password = etpassword.getText().toString();

                if (etusername.length() == 0) {
                    Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (etpassword.length() == 0) {
                    Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"mobile\":\"" + username + "\",\"password\":\"" + password + "\",\"action\":\"userLogin\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, LoginActivity.this, param, Config.API_TOKEN_ADD_LOGIN);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.API_TOKEN_ADD_LOGIN) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    login = new LoginModel();
                    login.setUser_id(jsonObject1.getString("user_id"));
                    shared.setUserId(jsonObject1.getString("user_id"));
                    login.setName(jsonObject1.getString("name"));
                    login.setWork_type(jsonObject1.getString("work_type"));
                    login.setMobile(jsonObject1.getString("mobile"));
                    login.setPassword(jsonObject1.getString("password"));
                    login.setUser_type(jsonObject1.getString("user_type"));
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    shared.putString(Config.CurrentUser, jsonObject1.toString());
                    shared.putBoolean(Config.isLogin, true);

                    String param = "{\"action\":\"getComponents\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.API_TOKEN_CONTAINER);
                    myAsyncTask.execute();

                   /* if (login.getUser_type().contentEquals("A")) {
                        Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (login.getUser_type().contentEquals("S")) {
                        Intent i = new Intent(LoginActivity.this, StaffDrawerActivity.class);
                        startActivity(i);
                        finish();
                    }*/
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_CONTAINER) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    shared.putString(Config.Staff, jsonObject1.getString("staff"));
                    shared.putString(Config.Project, jsonObject1.getString("project"));
                    shared.putString(Config.Material, jsonObject1.getString("material"));
                    shared.putString(Config.company, jsonObject1.getString("company"));
                    shared.putString(Config.Bank, jsonObject1.getString("bank"));
                    shared.putString(Config.Vehicle, jsonObject1.getString("vehicle"));
                    shared.putString(Config.VehicleType, jsonObject1.getString("vehicle_type"));
                    shared.putString(Config.Work_type, jsonObject1.getString("work_type"));

                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (login.getUser_type().contentEquals("A")) {
                Intent intent = new Intent(LoginActivity.this, DrawerActivity.class);
                startActivity(intent);
                finish();
            } else if (login.getUser_type().contentEquals("S")) {
                Intent i = new Intent(LoginActivity.this, StaffDrawerActivity.class);
                startActivity(i);
                finish();
            }
        }
    }


}



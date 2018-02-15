package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Model.BankModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONObject;

import java.util.ArrayList;

public class AddBankActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    EditText bank_name, bank_ifsc, bank_branch;
    Button add_bank, update_bank;
    Shared shared;
    Context context;
    String id;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);

        setTitle("બેંક");
        context = this;
        shared = new Shared(context);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        bank_name = (EditText) findViewById(R.id.bank_name);
        bank_ifsc = (EditText) findViewById(R.id.bank_ifsc);
        bank_branch = (EditText) findViewById(R.id.bank_branch);
        add_bank = (Button) findViewById(R.id.add_bank);
        update_bank = (Button) findViewById(R.id.update_bank);

        if (getIntent().getBooleanExtra("Update", false)) {
            bank_name.setText(getIntent().getExtras().getString("bankname"));
            bank_ifsc.setText(getIntent().getExtras().getString("ifsc"));
            bank_branch.setText(getIntent().getExtras().getString("branch"));
            txt_start_date.setText(getIntent().getStringExtra("date"));
            id = getIntent().getStringExtra("bank_id");
            add_bank.setVisibility(View.GONE);
            update_bank.setVisibility(View.VISIBLE);
        }
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddBankActivity.this, txt_start_date);
            }
        });

        add_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ebank_name, ebank_ifsc, ebank_branch, date;

                ebank_name = bank_name.getText().toString();
                ebank_ifsc = bank_ifsc.getText().toString();
                date = txt_start_date.getText().toString();
                ebank_branch = bank_branch.getText().toString();

                if (ebank_name.contentEquals("")) {
                    Toast.makeText(context, "Enter Bank Name", Toast.LENGTH_SHORT).show();
                } else if (ebank_ifsc.contentEquals("")) {
                    Toast.makeText(context, "Enter IFSC", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (ebank_branch.contentEquals("")) {
                    Toast.makeText(context, "Enter Branch", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"\",\"bank_name\":\"" + ebank_name + "\",\"ifsc_code\":\"" + ebank_ifsc + "\",\"cdate\":\"" + date + "\",\"branch\":\"" + ebank_branch + "\",\"action\":\"addUpdateBank\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddBankActivity.this, param, Config.API_TOKEN_ADD_BANK);
                    myAsyncTask.execute();
//                    Intent company_master=new Intent(Add_BAnk_Activity.this,BankActivity.class);
//                    startActivity(company_master);
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });



        update_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ebank_name, ebank_ifsc, ebank_branch,date;

                ebank_name = bank_name.getText().toString();
                ebank_ifsc = bank_ifsc.getText().toString();
                ebank_branch = bank_branch.getText().toString();
                date = txt_start_date.getText().toString();


                if (ebank_name.contentEquals("")) {
                    Toast.makeText(context, "Enter Bank Name", Toast.LENGTH_SHORT).show();
                } else if (ebank_ifsc.contentEquals("")) {
                    Toast.makeText(context, "Enter IFSC", Toast.LENGTH_SHORT).show();
                } else if (ebank_branch.contentEquals("")) {
                    Toast.makeText(context, "Enter Branch", Toast.LENGTH_SHORT).show();
                }else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                }
                else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\":\"" + id + "\",\"bank_name\":\"" + ebank_name + " \",\"ifsc_code\":\"" + ebank_ifsc + "\",\"branch\":\"" + ebank_branch + "\",\"cdate\":\"" + date + "\",\"action\":\"addUpdateBank\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddBankActivity.this, param, Config.API_TOKEN_UPDATE_BANK);
                    myAsyncTask.execute();
//                    Intent company_master=new Intent(AddBankActivity.this,BankActivity.class);
//                    startActivity(company_master);
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
        if (flag == Config.API_TOKEN_ADD_BANK) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    bank_name.setText("");
                    bank_ifsc.setText("");
                    bank_branch.setText("");
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_TOKEN_UPDATE_BANK) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                    ArrayList<BankModel> userList = new ArrayList<>();

                    BankModel bankModel = new BankModel();

                    bankModel.setBank_id(jsonObject1.getString("bank_id"));
                    bankModel.setBank_name(jsonObject1.getString("bank_name"));
                    bankModel.setIfsc_code(jsonObject1.getString("ifsc_code"));
                    bankModel.setBranch(jsonObject1.getString("branch"));
                    userList.add(bankModel);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                    DrawerActivity.activity.callComponenet();
                } else {

                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


}

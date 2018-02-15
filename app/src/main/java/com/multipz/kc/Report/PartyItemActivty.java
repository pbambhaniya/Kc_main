package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.PartyItemAdapter;
import com.multipz.kc.Adapter.PartyWisePaymeentAdapter;
import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.PartyWisePayModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class PartyItemActivty extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private String company_id;
    private Context mcontext;
    private TextView txt_select_month_year_partItem;
    private ArrayList<PartyItemModel> list;
    private PartyItemAdapter adapter;
    private ListView listviewpartyData;
    private Button btn_send_party_item;
    private int mYear, mMonth, date;
    private String monthYear;
    int pagecount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_item_activty);
        setTitle(getResources().getString(R.string.PartyWisePayment));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        Intent i = getIntent();
        company_id = i.getStringExtra("companyid");

        txt_select_month_year_partItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(PartyItemActivty.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_month_year_partItem.setText(year + "-" + "0" + (monthOfYear + 1));
                        } else {
                            txt_select_month_year_partItem.setText(year + "-" + (monthOfYear + 1));
                        }
                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();

            }
        });


        btn_send_party_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReport();
            }
        });
    }

    private void setReport() {
        monthYear = txt_select_month_year_partItem.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            list.clear();
            getParamforPartywisepayment();
        }
    }

    private void reference() {
        listviewpartyData = (ListView) findViewById(R.id.listviewpartyData);
        txt_select_month_year_partItem = (TextView) findViewById(R.id.txt_select_month_year_partItem);
        btn_send_party_item = (Button) findViewById(R.id.btn_send_party_item);

    }

    private void getParamforPartywisepayment() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"1\",\"action\" :\"" + Config.partyItem + "\",\"cid\":\"" + company_id + "\",\"date\":\"" + monthYear + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, PartyItemActivty.this, params, Config.APi_partyItem);
            myAsyncTask.execute();
        } else {
            Toast.makeText(PartyItemActivty.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_partyItem) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            PartyItemModel model = new PartyItemModel();
                            model.setComp_import_id(c.getString("comp_import_id"));
                            model.setSide_sort_name(c.getString("side_sort_name"));
                            model.setMaterial_type(c.getString("material_type"));
                            model.setAmount(c.getString("amount"));
                            list.add(model);
                        }
                        adapter = new PartyItemAdapter(getApplicationContext(), list);
                        listviewpartyData.setAdapter(adapter);
                    } else {
                    }
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

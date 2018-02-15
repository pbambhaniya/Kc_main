package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.PartyItemAdapter;
import com.multipz.kc.Adapter.VehicleExpenseItemAdapter;
import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.VehicleExReportItemModel;
import com.multipz.kc.Model.VehicleExReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleExpenseItemActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private String vehicle_detail_id;
    private Context context;
    private TextView txt_select_month_year_partItem;
    private ArrayList<VehicleExReportItemModel> list;
    private VehicleExpenseItemAdapter adapter;
    private ListView listviewpartyData;
    private Button btn_send_party_item;
    private int mYear, mMonth, date;
    private String monthYear;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    Shared shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_expense_item);
        setTitle(getResources().getString(R.string.Vehicle_Expense));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(context);
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        Intent i = getIntent();
        vehicle_detail_id = i.getStringExtra("vehicle_detail_id");

        txt_select_month_year_partItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(VehicleExpenseItemActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        listviewpartyData.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamforPartywisepayment();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
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
            Toast.makeText(context, "Please month / year", Toast.LENGTH_SHORT).show();
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
        if (Constant_method.checkConn(context)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"vahecleDetails\",\"cid\":\"" + vehicle_detail_id + "\",\"uid\":\"" + shared.getUserId().toString() + "\",\"date\":\"" + monthYear + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleExpenseItemActivity.this, params, Config.API_Vehicle_item);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleExpenseItemActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_Vehicle_item) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            VehicleExReportItemModel model = new VehicleExReportItemModel();
                            model.setName(c.getString("name"));
                            model.setUser_type(c.getString("user_type"));
                            model.setDetail(c.getString("detail"));
                            model.setAmount(c.getString("amount"));
                            list.add(model);
                        }
                        adapter = new VehicleExpenseItemAdapter(context, list);
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

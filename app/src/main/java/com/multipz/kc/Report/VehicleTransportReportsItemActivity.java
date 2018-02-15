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

import com.multipz.kc.Adapter.VehicleExpenseItemAdapter;
import com.multipz.kc.Adapter.VehicleTransportReportsItemAdapter;
import com.multipz.kc.Model.VehicleExReportItemModel;
import com.multipz.kc.Model.VehicleTransportReportItemModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleTransportReportsItemActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private String vehicle_detail_id;
    private Context context;
    private TextView txt_select_month_year_partItem;
    private ArrayList<VehicleTransportReportItemModel> list;
    private VehicleTransportReportsItemAdapter adapter;
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
        setContentView(R.layout.activity_vehicle_transport_reports_item);

        setTitle(getResources().getString(R.string.Vehicle_Transport));
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(VehicleTransportReportsItemActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_month_year_partItem.setText("0" + (monthOfYear + 1) + "-" + year);
                        } else {
                            txt_select_month_year_partItem.setText((monthOfYear + 1) + "-" + year);
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
                    getparamforVehicleTransport();
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
            getparamforVehicleTransport();
        }
    }

    private void reference() {
        listviewpartyData = (ListView) findViewById(R.id.listviewpartyData);
        txt_select_month_year_partItem = (TextView) findViewById(R.id.txt_select_month_year_partItem);
        btn_send_party_item = (Button) findViewById(R.id.btn_send_party_item);

    }

    private void getparamforVehicleTransport() {
        if (Constant_method.checkConn(context)) {
            String params = "{\"monthYear\":\"" + monthYear + "\",\"vehicle_detail_id\":\"" + vehicle_detail_id + "\",\"action\":\"vehicleTransportReportDetail\",\"page\":\"" + pagecount + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleTransportReportsItemActivity.this, params, Config.API_Vehicle_Transports_Report_Item);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleTransportReportsItemActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_Vehicle_Transports_Report_Item) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    list = new ArrayList<>();

                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleTransportReportItemModel model = new VehicleTransportReportItemModel();
                        model.setVehicle_transport_id(c.getString("vehicle_transport_id"));
                        model.setVehicle_detail_id(c.getString("vehicle_detail_id"));
                        model.setUser_id(c.getString("user_id"));
                        model.setMaterial_id(c.getString("material_id"));
                        model.setLoad_to(c.getString("load_to"));
                        model.setLoad_compid(c.getString("load_compid"));
                        model.setLoad_amount(c.getString("load_amount"));
                        model.setEMPTY_TO(c.getString("empty_to"));
                        model.setEmpty_compid(c.getString("empty_compid"));
                        model.setEmpty_amount(c.getString("empty_amount"));
                        model.setTotalLoadAmount(c.getString("totalLoadAmount"));
                        model.setTotalEmptyAmount(c.getString("totalEmptyAmount"));
                        model.setName(c.getString("name"));
                        model.setLoadCompanyName(c.getString("loadCompanyName"));
                        model.setEmptyCompanyName(c.getString("emptyCompanyName"));
                        model.setVehicle_no(c.getString("vehicle_no"));
                        model.setVehicle_type(c.getString("vehicle_type"));
                        model.setWork_type(c.getString("work_type"));
                        model.setCreated_date(c.getString("created_date"));
                        model.setProfit(c.getString("Profit"));
                        list.add(model);
                    }
                    adapter = new VehicleTransportReportsItemAdapter(context, list);
                    listviewpartyData.setAdapter(adapter);
                } else {
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

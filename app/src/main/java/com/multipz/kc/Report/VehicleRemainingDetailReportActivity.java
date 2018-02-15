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

import com.multipz.kc.Adapter.VehicleHourlyDetailRemainigAdapter;
import com.multipz.kc.Model.VehicleRemainDetailReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleRemainingDetailReportActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private String company_id;
    private Context mcontext;
    private TextView txt_select_month_year_vehicle_remain_rep;
    private ArrayList<VehicleRemainDetailReportModel> remainlist;
    private VehicleHourlyDetailRemainigAdapter adapter;
    private ListView listviewvehicleremainrep;
    private Button btn_vehicle_remain_rep;
    private int mYear, mMonth, date;
    private String monthYear;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_remaining_detail_report);
        setTitle(getResources().getString(R.string.Vehicle_Hourly));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        remainlist = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        Intent i = getIntent();
        company_id = i.getStringExtra("companyid");

        txt_select_month_year_vehicle_remain_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(VehicleRemainingDetailReportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_month_year_vehicle_remain_rep.setText("0" + (monthOfYear + 1) + "-" + year);
                        } else {
                            txt_select_month_year_vehicle_remain_rep.setText((monthOfYear + 1) + "-" + year);
                        }
                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();

            }
        });


        btn_vehicle_remain_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReport();
            }
        });

        listviewvehicleremainrep.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    }

    private void setReport() {
        monthYear = txt_select_month_year_vehicle_remain_rep.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            remainlist.clear();
            getParamforPartywisepayment();
        }
    }

    private void reference() {
        listviewvehicleremainrep = (ListView) findViewById(R.id.listviewvehicleremainrep);
        txt_select_month_year_vehicle_remain_rep = (TextView) findViewById(R.id.txt_select_month_year_vehicle_remain_rep);
        btn_vehicle_remain_rep = (Button) findViewById(R.id.btn_vehicle_remain_rep);

    }

    private void getParamforPartywisepayment() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"monthYear\":\"" + monthYear + "\",\"company_id\":\"" + company_id + "\",\"page\":\"" + pagecount + "\",\"action\":\"vehicleDetailHourlyReportRemainAmt\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleRemainingDetailReportActivity.this, params, Config.APi_vehicleDetailHourlyReportRemainAmt);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleRemainingDetailReportActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onResponseService(String response, int flag) {

        int success;
        String msg;
        if (flag == Config.APi_vehicleDetailHourlyReportRemainAmt) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleRemainDetailReportModel model = new VehicleRemainDetailReportModel();
                        model.setStart_time(c.getString("start_time"));
                        model.setEnd_time(c.getString("end_time"));
                        model.setVehicle_hourly_id(c.getString("vehicle_hourly_id"));
                        model.setAmount(c.getString("amount"));
                        model.setCreated_date(Constant_method.getOnlyDate(c.getString("created_date")));
                        model.setCompanyName(c.getString("companyName"));
                        model.setUserName(c.getString("userName"));
                        model.setVehicle_no(c.getString("vehicle_no"));
                        model.setVehicle_type(c.getString("vehicle_type"));
                        model.setTotalSum(c.getString("totalSum"));
                        remainlist.add(model);
                    }
                    adapter = new VehicleHourlyDetailRemainigAdapter(getApplicationContext(), remainlist);
                    listviewvehicleremainrep.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

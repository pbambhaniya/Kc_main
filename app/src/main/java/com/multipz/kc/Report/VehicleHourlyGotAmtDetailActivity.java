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

import com.multipz.kc.Adapter.VehicleHourlyDetailGotAmtReportAdapter;
import com.multipz.kc.Adapter.VehicleHourlyDetailRemainigAdapter;
import com.multipz.kc.Model.VehicleHourlyGotAmtDetailModel;
import com.multipz.kc.Model.VehicleRemainDetailReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleHourlyGotAmtDetailActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {


    private String company_id;
    private Context mcontext;
    private TextView txt_select_month_year_vehicle_got_rep;
    private ArrayList<VehicleHourlyGotAmtDetailModel> list;
    private VehicleHourlyDetailGotAmtReportAdapter adapter;
    private ListView listviewvehiclegotrep;
    private Button btn_vehicle_got_rep;
    private int mYear, mMonth, date;
    private String monthYear;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_hourly_got_amt_detail);
        setTitle(getResources().getString(R.string.Vehicle_Hourly));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        list = new ArrayList<>();
        reference();
        init();

    }

    private void init() {
        Intent i = getIntent();
        company_id = i.getStringExtra("companyid");

        txt_select_month_year_vehicle_got_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(VehicleHourlyGotAmtDetailActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        if (monthOfYear > 1 && monthOfYear < 9) {
                            txt_select_month_year_vehicle_got_rep.setText("0" + (monthOfYear + 1) + "-" + year);
                        } else {
                            txt_select_month_year_vehicle_got_rep.setText((monthOfYear + 1) + "-" + year);
                        }
                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();

            }
        });

        listviewvehiclegotrep.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamforGotAmt();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });

        btn_vehicle_got_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReport();
            }
        });
    }

    private void setReport() {
        monthYear = txt_select_month_year_vehicle_got_rep.getText().toString();
        if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            list.clear();
            getParamforGotAmt();
        }
    }

    private void reference() {
        listviewvehiclegotrep = (ListView) findViewById(R.id.listviewvehiclegotrep);
        txt_select_month_year_vehicle_got_rep = (TextView) findViewById(R.id.txt_select_month_year_vehicle_got_rep);
        btn_vehicle_got_rep = (Button) findViewById(R.id.btn_vehicle_got_rep);

    }

    private void getParamforGotAmt() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"monthYear\":\"" + monthYear + "\",\"company_id\":\"" + company_id + "\",\"page\":\"" + pagecount + "\",\"action\":\"vehicleDetailHourlyReportGotAmt\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleHourlyGotAmtDetailActivity.this, params, Config.APi_vehicleDetailHourlyReportGotAmt);
            myAsyncTask.execute();
        } else {
            Toast.makeText(VehicleHourlyGotAmtDetailActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
        if (flag == Config.APi_vehicleDetailHourlyReportGotAmt) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");

                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleHourlyGotAmtDetailModel model = new VehicleHourlyGotAmtDetailModel();
                        model.setTbl_vehicle_hourly_pay_id(c.getString("tbl_vehicle_hourly_pay_id"));
                        model.setCompany_id(c.getString("company_id"));
                        model.setBank_id(c.getString("bank_id"));
                        model.setAmount(c.getString("amount"));
                        model.setPayment_type(c.getString("payment_type"));
                        model.setCompanyName(c.getString("payment_info"));
                        model.setDetail(c.getString("detail"));
                        model.setDate(Constant_method.getOnlyDate(c.getString("date")));
                        model.setIs_delete(c.getString("is_delete"));
                        model.setTotalSum(c.getString("totalSum"));
                        model.setBankName(c.getString("bankName"));
                        model.setCompanyName(c.getString("companyName"));
                        list.add(model);
                    }
                    adapter = new VehicleHourlyDetailGotAmtReportAdapter(getApplicationContext(), list);
                    listviewvehiclegotrep.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

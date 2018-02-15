package com.multipz.kc.StaffReport;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.PartyItemAdapter;
import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Adapter.StaffExpVeicleReportAdapter;
import com.multipz.kc.Model.PartyItemModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.StaffVehicleExpReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StaffExpenseVehicleReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface {

    private String company_id;
    private Context mcontext;
    private TextView txt_select_month_year_staffExp;
    private ArrayList<StaffVehicleExpReportModel> list;
    private StaffExpVeicleReportAdapter adapter;
    private ListView listviewstaffVehiclerep;
    private Button btn_send_staff_exp;
    private int mYear, mMonth, date;
    private String monthYear;
    private Shared shared;
    private Spinner sp_vehicle_number;
    ArrayList<SpinnerModel> objects_vehicle_no;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private String vehicleno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_expense_vehicle_report);
        setTitle(getResources().getString(R.string.Staff_vehicle_ex_report));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mcontext = this;
        shared = new Shared(getApplicationContext());
        list = new ArrayList<>();
        reference();
        init();
    }

    private void init() {
        getVehicleNo();

        txt_select_month_year_staffExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                mMonth = c.get(Calendar.MONTH);
                mYear = c.get(Calendar.YEAR);
                date = c.get(Calendar.DATE);
                DatePickerDialog datePickerDialog = new DatePickerDialog(StaffExpenseVehicleReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        String formattedDay = (String.valueOf(dayOfMonth));
                        String formattedMonth = (String.valueOf(month));
                        if (dayOfMonth < 10) {
                            formattedDay = "0" + dayOfMonth;
                        }
                        if (month < 10) {
                            formattedMonth = "0" + monthOfYear;
                        }
                        txt_select_month_year_staffExp.setText(year + "-" + formattedMonth + "-" + formattedDay);

                    }
                }, mYear, mMonth, date);

                datePickerDialog.show();

            }
        });

        listviewstaffVehiclerep.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    getParamforStaffVehicleExpReport();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });

        btn_send_staff_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setReport();
            }
        });
    }

    private void getVehicleNo() {
        objects_vehicle_no = new ArrayList<>();
        objects_vehicle_no.add(new SpinnerModel("", getResources().getString(R.string.SElect_Vehicle_No)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Vehicle, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_detail_id"));
                spinnerModel.setName(staff.getString("vehicle_no"));
                objects_vehicle_no.add(spinnerModel);
            }
            sp_vehicle_number.setAdapter(new SpinnerAdapter(this, objects_vehicle_no));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setReport() {
        monthYear = txt_select_month_year_staffExp.getText().toString();
        vehicleno = objects_vehicle_no.get(sp_vehicle_number.getSelectedItemPosition()).getid();
        if (vehicleno.equals("")) {
            Toast.makeText(mcontext, "Please enter vehicle no", Toast.LENGTH_SHORT).show();
        } else if (monthYear.equals("")) {
            Toast.makeText(mcontext, "Please month / year", Toast.LENGTH_SHORT).show();
        } else {
            list.clear();
            getParamforStaffVehicleExpReport();
        }
    }

    private void reference() {
        listviewstaffVehiclerep = (ListView) findViewById(R.id.listviewstaffVehiclerep);
        txt_select_month_year_staffExp = (TextView) findViewById(R.id.txt_select_month_year_staffExp);
        btn_send_staff_exp = (Button) findViewById(R.id.btn_send_staff_exp);
        sp_vehicle_number = (Spinner) findViewById(R.id.sp_vehicle_number);
    }

    private void getParamforStaffVehicleExpReport() {
        if (Constant_method.checkConn(mcontext)) {
            String params = "{\"page\":\"" + pagecount + "\",\"action\":\"StaffVehicleExpReportData\",\"user_id\":\"" + shared.getUserId() + "\",\"monthyear\":\"" + monthYear + "\",\"tbl_vehicle_type_id\":\"" + vehicleno + "\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, StaffExpenseVehicleReport.this, params, Config.APi_StaffVehicleExpReportData);
            myAsyncTask.execute();
        } else {
            Toast.makeText(StaffExpenseVehicleReport.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_StaffVehicleExpReportData) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            StaffVehicleExpReportModel model = new StaffVehicleExpReportModel();
                            model.setVehicle_gen_exp_id(c.getString("vehicle_gen_exp_id"));
                            model.setName(c.getString("name"));
                            model.setVehicle_no(c.getString("vehicle_no"));
                            model.setDetail(c.getString("detail"));
                            model.setCreated_date(c.getString("created_date"));
                            model.setAmt(c.getString("amt"));
                            list.add(model);
                        }
                        adapter = new StaffExpVeicleReportAdapter(getApplicationContext(), list);
                        listviewstaffVehiclerep.setAdapter(adapter);
                    } else {
                        Toast.makeText(mcontext, "Data Not Found", Toast.LENGTH_SHORT).show();
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

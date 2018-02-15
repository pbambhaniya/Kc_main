package com.multipz.kc.StaffReport;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Activity.AddProjectActivity;
import com.multipz.kc.Adapter.ProjectReturnReportAdapter;
import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Adapter.StaffSiteExpenseReportAdapter;
import com.multipz.kc.Adapter.VehicleHourlyDetailGotAmtReportAdapter;
import com.multipz.kc.Adapter.VehicleTransportReportsItemAdapter;
import com.multipz.kc.Model.ProjectReturnReportModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.StaffSiteExpenseReportModel;
import com.multipz.kc.Model.VehicleHourlyGotAmtDetailModel;
import com.multipz.kc.Model.VehicleTransportReportItemModel;
import com.multipz.kc.R;
import com.multipz.kc.Report.VehicleTransportReportsItemActivity;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static com.multipz.kc.R.id.sp_company;
import static com.multipz.kc.R.id.sp_company_id;

public class StaffSiteExpenseReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    private Context context;
    private TextView txt_select_month_year_vehicle_got_rep;
    private ArrayList<StaffSiteExpenseReportModel> list;
    private StaffSiteExpenseReportAdapter adapter;
    private Button btn_staff_ex_report;
    private int mYear, mMonth, mDay;
    ArrayList<SpinnerModel> objects_company;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    Shared shared;
    Spinner sp_pro_name;
    ListView list_staff_ex_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_site_expense_report);
        setTitle(getResources().getString(R.string.Staff_Site_ex_report));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(this);

        refrence();
        init();


    }

    private void init() {
        getCompanyName();

        txt_select_month_year_vehicle_got_rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(StaffSiteExpenseReport.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

//                                txt_select_month_year_vehicle_got_rep.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                txt_select_month_year_vehicle_got_rep.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btn_staff_ex_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStaffExpReport();
            }
        });
    }

    private void refrence() {
        sp_pro_name = (Spinner) findViewById(R.id.sp_pro_name);
        txt_select_month_year_vehicle_got_rep = (TextView) findViewById(R.id.txt_select_month_year_vehicle_got_rep);
        btn_staff_ex_report = (Button) findViewById(R.id.btn_staff_ex_report);
        list_staff_ex_report = (ListView) findViewById(R.id.list_staff_ex_report);
    }

    private void getCompanyName() {
        objects_company = new ArrayList<>();
        objects_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Project, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("project_id"));
                spinnerModel.setName(staff.getString("side_sort_name"));
                objects_company.add(spinnerModel);
            }
            sp_pro_name.setAdapter(new SpinnerAdapter(this, objects_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getStaffExpReport() {
        String comid, sdate;
        sdate = txt_select_month_year_vehicle_got_rep.getText().toString();
        comid = objects_company.get(sp_pro_name.getSelectedItemPosition()).getid();


        if (comid.equals("")) {
            Toast.makeText(context, "Please enter vehicle no", Toast.LENGTH_SHORT).show();
        } else if (sdate.equals("")) {
            Toast.makeText(context, "Please month / year", Toast.LENGTH_SHORT).show();
        } else if (Constant_method.checkConn(context)) {
                String params = "{\"page\":\"" + pagecount + "\",\"action\" :\"StaffSiteExpReportData\",\"user_id\":\"" + shared.getUserId().toString() + "\",\"monthyear\":\"" + sdate + "\",\"tbl_project_id\":\"" + comid + "\"}";
                MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, StaffSiteExpenseReport.this, params, Config.Api_GEt_Staff_ex_site_report);
                myAsyncTask.execute();
            } else {
                Toast.makeText(StaffSiteExpenseReport.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onResponseService (String response,int flag){
            int success;
            String msg;
            if (flag == Config.Api_GEt_Staff_ex_site_report) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt("status");
                    if (success == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        list = new ArrayList<>();
                        for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                            JSONObject c = jsonArray.getJSONObject(i1);
                            StaffSiteExpenseReportModel model = new StaffSiteExpenseReportModel();
                            model.setSide_sort_name(c.getString("side_sort_name"));
                            model.setSide_gen_exp_id(c.getString("side_gen_exp_id"));
                            model.setName(c.getString("name"));
                            model.setDetail(c.getString("detail"));
                            model.setAmt(c.getString("amt"));
                            model.setCreated_date(c.getString("created_date"));
                            list.add(model);
                        }
                        adapter = new StaffSiteExpenseReportAdapter(context, list);
                        list_staff_ex_report.setAdapter(adapter);
                    } else {

                        Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    onBackPressed();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);

            }
        }
    }

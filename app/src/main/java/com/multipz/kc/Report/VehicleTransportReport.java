package com.multipz.kc.Report;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Adapter.StaffProjectReportAdapter;
import com.multipz.kc.Adapter.VehicleTransportReportAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.StaffProjectReportModel;
import com.multipz.kc.Model.VehicleExReportModel;
import com.multipz.kc.Model.VehicleTransportReportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class VehicleTransportReport extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    Spinner sp_user_id, sp_load_com, sp_empty_com, sp_vehicle_no;
    Context context;
    ImageView imageView;
    TextView startdate, enddate, total_amount;
    private int mYear, mMonth, mDay;
    String Sdate = "", Edate = "";
    ListView report_list;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    int pagecount = 1;
    private ArrayList<SpinnerModel> object_load_com, object_user, object_empty_com, object_vehicle;
    ArrayList<VehicleTransportReportModel> userList;
    Shared shared;
    private VehicleTransportReportAdapter adapter;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_transport_report);


        setTitle(getResources().getString(R.string.Vehicle_Transport));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        shared = new Shared(this);
        context = this;

        sp_user_id = (Spinner) findViewById(R.id.sp_user_id);
        sp_load_com = (Spinner) findViewById(R.id.sp_load_com);
        sp_empty_com = (Spinner) findViewById(R.id.sp_empty_com);
        sp_vehicle_no = (Spinner) findViewById(R.id.sp_vehicle_no);
        imageView = (ImageView) findViewById(R.id.img_clear);
        startdate = (TextView) findViewById(R.id.txt_start_date);
        enddate = (TextView) findViewById(R.id.txt_end_date);
        report_list = (ListView) findViewById(R.id.report_list);
        add = (Button) findViewById(R.id.btn_add);
        total_amount = (TextView) findViewById(R.id.total_amount);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp_user_id.setSelection(0);
                sp_load_com.setSelection(0);
                sp_empty_com.setSelection(0);
                sp_vehicle_no.setSelection(0);
                startdate.setText("");
                enddate.setText("");
            }
        });

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(VehicleTransportReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        startdate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Sdate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(VehicleTransportReport.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        enddate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Edate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        report_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (lastItem == totalItemCount && i == SCROLL_STATE_IDLE) {
                    pagecount++;
                    callservice();
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItemm, int visibleItemCountt, int totalItemCountt) {
                firstVisibleItem = firstVisibleItemm;
                visibleItemCount = visibleItemCountt;
                totalItemCount = totalItemCountt;
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callservice();
            }
        });

        object_vehicle = new ArrayList<>();
        object_vehicle.add(new SpinnerModel("", getResources().getString(R.string.vehicle_no)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Vehicle, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);

                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_detail_id"));
                String name = staff.getString("vehicle_no");
                String type = staff.getString("vehicle_type");
                String val = name + "(" + type + ")";
                spinnerModel.setName(val);
                object_vehicle.add(spinnerModel);
            }
            sp_vehicle_no.setAdapter(new SpinnerAdapter(this, object_vehicle));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        object_user = new ArrayList<>();
        object_user.add(new SpinnerModel("", getResources().getString(R.string.Select_Name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.Staff, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("user_id"));
                String name = staff.getString("name");
                String type = staff.getString("work_type");
                String val = name + "(" + type + ")";
                spinnerModel.setName(val);
                object_user.add(spinnerModel);
            }
            sp_user_id.setAdapter(new SpinnerAdapter(this, object_user));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        object_load_com = new ArrayList<>();
        object_load_com.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                object_load_com.add(spinnerModel);
            }
            sp_load_com.setAdapter(new SpinnerAdapter(this, object_load_com));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object_empty_com = new ArrayList<>();
        object_empty_com.add(new SpinnerModel("",getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                object_empty_com.add(spinnerModel);
            }
            sp_empty_com.setAdapter(new SpinnerAdapter(this, object_empty_com));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callservice() {
        String load_com, empty_com, sp_vehicle, sp_user;
        sp_vehicle = object_vehicle.get(sp_vehicle_no.getSelectedItemPosition()).getid();
        sp_user = object_user.get(sp_user_id.getSelectedItemPosition()).getid();
        empty_com = object_empty_com.get(sp_empty_com.getSelectedItemPosition()).getid();
        load_com = object_load_com.get(sp_load_com.getSelectedItemPosition()).getid();

        if (Constant_method.checkConn(context)) {
            String params;
            if (Sdate.contentEquals("") && Edate.contentEquals("")) {
                params = "{\"page\": \"" + pagecount + "\",\"is_report\": \"\",\"vehicle_detail_id\": \"" + sp_vehicle + "\",\"user_id\": \"" + sp_user + "\",\"load_compid\": \"" + load_com + "\",\"empty_compid\": \"" + empty_com + "\",\"date_range\": \"\",\"action\": \"reportVehicleTransport\"}";
            } else if (Sdate.contentEquals("") || Edate.contentEquals("")) {
                if (Sdate.contentEquals("")) {
                    Edate = Sdate;
                } else {
                    Sdate = Edate;
                }
                params = "{\"page\": \"" + pagecount + "\",\"is_report\": \"\",\"vehicle_detail_id\": \"" + sp_vehicle + "\",\"user_id\": \"" + sp_user + "\",\"load_compid\": \"" + load_com + "\",\"empty_compid\": \"" + empty_com + "\",\"date_range\": \"" + Sdate + "-" + Edate + "\",\"action\": \"reportVehicleTransport\"}";
            } else {
                params = "{\"page\": \"" + pagecount + "\",\"is_report\": \"\",\"vehicle_detail_id\": \"" + sp_vehicle + "\",\"user_id\": \"" + sp_user + "\",\"load_compid\": \"" + load_com + "\",\"empty_compid\": \"" + empty_com + "\",\"date_range\": \"" + Sdate + "-" + Edate + "\",\"action\": \"reportVehicleTransport\"}";
            }
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, VehicleTransportReport.this, params, Config.APi_Get_vehicle_transport_report);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_Get_vehicle_transport_report) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                if (success == 1) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    userList = new ArrayList<>();
                    for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                        JSONObject c = jsonArray.getJSONObject(i1);
                        VehicleTransportReportModel vehicleTransportReport = new VehicleTransportReportModel();
                        vehicleTransportReport.setVehicle_transport_id(c.getString("vehicle_transport_id"));
                        vehicleTransportReport.setVehicle_detail_id(c.getString("vehicle_detail_id"));
                        vehicleTransportReport.setUser_id(c.getString("user_id"));
                        vehicleTransportReport.setMaterial_id(c.getString("material_id"));
                        vehicleTransportReport.setLoad_to(c.getString("load_to"));
                        vehicleTransportReport.setLoad_compid(c.getString("load_compid"));
                        vehicleTransportReport.setLoad_amount(c.getString("load_amount"));
                        vehicleTransportReport.setEmpty_to(c.getString("empty_to"));
                        vehicleTransportReport.setEmpty_compid(c.getString("empty_compid"));
                        vehicleTransportReport.setEmpty_amount(c.getString("empty_amount"));
                        vehicleTransportReport.setCreated_date(Constant_method.getOnlyDate(c.getString("created_date")));
                        vehicleTransportReport.setUserName(c.getString("userName"));
                        vehicleTransportReport.setWork_type(c.getString("work_type"));
                        vehicleTransportReport.setVehicle_no(c.getString("vehicle_no"));
                        vehicleTransportReport.setVehicle_type(c.getString("vehicle_type"));
                        vehicleTransportReport.setMaterial_type(c.getString("material_type"));
                        vehicleTransportReport.setLoadCompany(c.getString("loadCompany"));
                        vehicleTransportReport.setEmptyCompany(c.getString("emptyCompany"));
                        userList.add(vehicleTransportReport);
                    }

                    adapter = new VehicleTransportReportAdapter(context, userList);
                    report_list.setAdapter(adapter);
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
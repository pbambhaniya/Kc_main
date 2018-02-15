package com.multipz.kc.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.CompanyPayModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddCompanyPayActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {
    EditText edit_detail, edit_amount;
    Button add_company_mst, update_company_mst;
    Shared shared;
    Context context;
    String id;
    LinearLayout lnr_bank;
    View v2;
    ArrayList<SpinnerModel> objects_company, objects_bank, object;
    Spinner sp_company_id, sp_payment_type, sp_bank_id;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;

    ArrayList<CompanyPayModel> companyPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_pay2);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_pay));
        context = this;
        shared = new Shared(context);


        sp_company_id = (Spinner) findViewById(R.id.sp_company_id);
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        sp_payment_type = (Spinner) findViewById(R.id.payment_type);
        sp_payment_type.setOnItemSelectedListener(this);

        v2 = (View) findViewById(R.id.v2);
        edit_detail = (EditText) findViewById(R.id.edit_detail);
        edit_amount = (EditText) findViewById(R.id.edit_amount);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        lnr_bank = (LinearLayout) findViewById(R.id.lnr_bank);

        add_company_mst = (Button) findViewById(R.id.add_company_master);
        update_company_mst = (Button) findViewById(R.id.update_company_master);

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddCompanyPayActivity.this, txt_start_date);
            }
        });

        objects_company = new ArrayList<>();
        objects_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                objects_company.add(spinnerModel);
            }
            sp_company_id.setAdapter(new SpinnerAdapter(this, objects_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //bank
        objects_bank = new ArrayList<>();
        objects_bank.add(new SpinnerModel("", getResources().getString(R.string.Select_Bank)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Bank, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("bank_id"));
                spinnerModel.setName(staff.getString("bank_name"));
                objects_bank.add(spinnerModel);
            }
            sp_bank_id.setAdapter(new SpinnerAdapter(this, objects_bank));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object = new ArrayList<SpinnerModel>();
        object.add(new SpinnerModel("", getResources().getString(R.string.Select_payment_type)));
        object.add(new SpinnerModel("CASH", getResources().getString(R.string.Cash)));
        object.add(new SpinnerModel("CHEQUE", getResources().getString(R.string.Cheque)));
        object.add(new SpinnerModel("RTGS", getResources().getString(R.string.Rtgs)));
        sp_payment_type.setAdapter(new SpinnerAdapter(this, object));


        if (getIntent().getBooleanExtra("Update", false)) {


            id = getIntent().getStringExtra("comp_pay_id");
            setSpinnerCapital(sp_payment_type, object, getIntent().getStringExtra("payment_type").trim());
            setSpinner(sp_company_id, objects_company, getIntent().getStringExtra("companyName"));
            setSpinner(sp_bank_id, objects_bank, getIntent().getStringExtra("bank_name"));
            edit_amount.setText(getIntent().getExtras().getString("amount"));
            edit_detail.setText(getIntent().getExtras().getString("payment_info"));
            txt_start_date.setText(getIntent().getStringExtra("date"));
            add_company_mst.setVisibility(View.GONE);
            update_company_mst.setVisibility(View.VISIBLE);
        }
        add_company_mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String epaymenttype, ecomid, ebankid, edetail, eamount, date;
                edetail = edit_detail.getText().toString();
                eamount = edit_amount.getText().toString();
                date = txt_start_date.getText().toString();
                epaymenttype = object.get(sp_payment_type.getSelectedItemPosition()).getid();
                ecomid = objects_company.get(sp_company_id.getSelectedItemPosition()).getid();
                ebankid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();

                if (ecomid.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (epaymenttype.contentEquals("CHEQUE")) {
                    if (sp_bank_id.getSelectedItem().equals("") || edit_detail.getText().toString().equals("")) {
                        Toast.makeText(context, "Enter Bank detail", Toast.LENGTH_SHORT).show();
                    } else if (Constant_method.checkConn(context)) {
                        String param = "{\"id\": \"\",\"company_id\": \"" + ecomid + "\",\"bank_id\": \"" + ebankid + "\",\"amount\": \"" + eamount + "\",\"payment_type\": \"" + epaymenttype + " \",\"payment_info\": \"" + edetail + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateCompanyPay\"}";
//                    String param = "{\"id\":\"\",\"name\":\"" + ename + "\",\"contact_no\":\"" + emobile + "\",\"action\":\"addUpdateCompany\"}";
                        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyPayActivity.this, param, Config.APi_Get_COmpany_pay_Add);
                        myAsyncTask.execute();
                    }

                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"company_id\": \"" + ecomid + "\",\"bank_id\": \"" + ebankid + "\",\"amount\": \"" + eamount + "\",\"payment_type\": \"" + epaymenttype + " \",\"payment_info\": \"" + edetail + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateCompanyPay\"}";
//                    String param = "{\"id\":\"\",\"name\":\"" + ename + "\",\"contact_no\":\"" + emobile + "\",\"action\":\"addUpdateCompany\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyPayActivity.this, param, Config.APi_Get_COmpany_pay_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        update_company_mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String epaymenttype, ecomid, ebankid, edetail, eamount, date;
                edetail = edit_detail.getText().toString();
                date = txt_start_date.getText().toString();
                eamount = edit_amount.getText().toString();
                epaymenttype = object.get(sp_payment_type.getSelectedItemPosition()).getid();
                ecomid = objects_company.get(sp_company_id.getSelectedItemPosition()).getid();
                ebankid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();

                if (ecomid.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (epaymenttype.contentEquals("CHEQUE")) {
                    if (sp_bank_id.getSelectedItem().equals("") || edit_detail.getText().toString().equals("")) {
                        Toast.makeText(context, "Enter Bank detail", Toast.LENGTH_SHORT).show();
                    } else if (Constant_method.checkConn(context)) {
                        String param = "{\"id\": \"" + id + "\",\"company_id\": \"" + ecomid + "\",\"bank_id\": \"" + ebankid + "\",\"amount\": \"" + eamount + "\",\"payment_type\": \"" + epaymenttype + " \",\"payment_info\": \"" + edetail + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateCompanyPay\"}";
//                    String param = "{\"id\":\"\",\"name\":\"" + ename + "\",\"contact_no\":\"" + emobile + "\",\"action\":\"addUpdateCompany\"}";
                        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyPayActivity.this, param, Config.APi_Get_COmpany_pay_Add);
                        myAsyncTask.execute();
                    }

                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"" + id + "\",\"company_id\": \"" + ecomid + "\",\"bank_id\": \"" + ebankid + "\",\"amount\": \"" + eamount + "\",\"payment_type\": \"" + epaymenttype + " \",\"payment_info\": \"" + edetail + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateCompanyPay\"}";
//                    String param = "{\"id\":\"\",\"name\":\"" + ename + "\",\"contact_no\":\"" + emobile + "\",\"action\":\"addUpdateCompany\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyPayActivity.this, param, Config.APi_Get_COmpany_pay_Add);
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
        if (flag == Config.APi_Get_COmpany_pay_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    sp_bank_id.setSelection(0);
                    sp_payment_type.setSelection(0);
                    sp_company_id.setSelection(0);
                    edit_amount.setText("");
                    edit_detail.setText("");
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_Get_COmpany_pay_Update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    ArrayList<CompanyPayModel> companyPay = new ArrayList<>();
                    CompanyPayModel compay = new CompanyPayModel();
                    compay.setBank_name(jsonObject1.getString("bank_name"));
                    compay.setCompanyName(jsonObject1.getString("companyName"));
                    compay.setComp_pay_id(jsonObject1.getString("comp_pay_id"));
                    compay.setCompany_id(jsonObject1.getString("company_id"));
                    compay.setBank_id(jsonObject1.getString("bank_id"));
                    compay.setAmount(jsonObject1.getString("amount"));
                    compay.setPayment_type(jsonObject1.getString("payment_type"));
                    compay.setPayment_info(jsonObject1.getString("payment_info"));
                    companyPay.add(compay);
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


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = object.get(sp_payment_type.getSelectedItemPosition()).getid();
        if (item.contentEquals("")) {
            edit_detail.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            lnr_bank.setVisibility(View.GONE);
            edit_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("CASH")) {
            sp_bank_id.setVisibility(View.GONE);
            edit_detail.setVisibility(View.GONE);
            lnr_bank.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
        } else if (item.contentEquals("CHEQUE")) {
            sp_bank_id.setVisibility(View.VISIBLE);
            edit_detail.setVisibility(View.VISIBLE);
            edit_detail.setHint(getResources().getString(R.string.chequeno));
            lnr_bank.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
        } else if (item.contentEquals("RTGS")) {
            sp_bank_id.setVisibility(View.VISIBLE);
            edit_detail.setVisibility(View.VISIBLE);
            edit_detail.setHint(getResources().getString(R.string.Rtgs_id));
            lnr_bank.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setSpinnerCapital(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getid().toUpperCase())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }

    private void setSpinner(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }
}

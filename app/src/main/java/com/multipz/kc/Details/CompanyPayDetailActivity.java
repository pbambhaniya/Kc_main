package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddCompanyActivity;
import com.multipz.kc.Activity.AddCompanyPayActivity;
import com.multipz.kc.R;

public class CompanyPayDetailActivity extends AppCompatActivity {
    TextView txt_bank_name, txt_com_name, txt_amount, txt_pay_type, txt_pay_info, txt_date;
    String bname, cname, amount, ptype, pinfo, id, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_pay_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_pay_detail));

//        address=(TextView)findViewById(R.id.txt_address);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_bank_name = (TextView) findViewById(R.id.txt_bank_name);
        txt_com_name = (TextView) findViewById(R.id.txt_com_name);
        txt_pay_info = (TextView) findViewById(R.id.txt_pay_info);
        txt_pay_type = (TextView) findViewById(R.id.txt_pay_type);
        txt_amount = (TextView) findViewById(R.id.txt_amount);

        id = getIntent().getStringExtra("comp_pay_id");
        bname = getIntent().getStringExtra("bank_name");
        cname = getIntent().getStringExtra("companyName");
        pinfo = getIntent().getStringExtra("payment_info");
        ptype = getIntent().getStringExtra("payment_type");
        amount = getIntent().getStringExtra("amount");
        date = getIntent().getStringExtra("date");


        txt_pay_info.setText(pinfo);
        txt_bank_name.setText(bname);
        txt_com_name.setText(cname);
        txt_amount.setText(amount);
        txt_pay_type.setText(ptype);
        txt_date.setText(date);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fragment_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent company_pay = new Intent(CompanyPayDetailActivity.this, AddCompanyPayActivity.class);
                company_pay.putExtra("comp_pay_id", id);
                company_pay.putExtra("bank_name", bname);
                company_pay.putExtra("companyName", cname);
                company_pay.putExtra("payment_info", pinfo);
                company_pay.putExtra("payment_type", ptype);
                company_pay.putExtra("date", date);
                company_pay.putExtra("amount", amount);
                company_pay.putExtra("Update", true);
                startActivity(company_pay);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddCompanyActivity;
import com.multipz.kc.R;

public class CompanyDetailActivity extends AppCompatActivity {

    TextView name, contact, address, txt_date;
    String txt_name, txt_contact, txt_address, txt_company_id, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_detail));
        name = (TextView) findViewById(R.id.txt_name);
        contact = (TextView) findViewById(R.id.txt_mobile);
        txt_date = (TextView) findViewById(R.id.txt_date);
//        address=(TextView)findViewById(R.id.txt_address);

        txt_company_id = getIntent().getStringExtra("company_id");
        date = getIntent().getStringExtra("date");
        txt_name = getIntent().getStringExtra("name");
        txt_contact = getIntent().getStringExtra("contact_no");
//        txt_address = getIntent().getStringExtra("address");


        name.setText(txt_name);
        contact.setText(txt_contact);
        txt_date.setText(date);
//        address.setText(txt_address);
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
                Intent company = new Intent(CompanyDetailActivity.this, AddCompanyActivity.class);
                company.putExtra("company_id", txt_company_id);
                company.putExtra("name", txt_name);
                company.putExtra("date", date);
                company.putExtra("contact_no", txt_contact);
                company.putExtra("Update", true);
                startActivity(company);
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

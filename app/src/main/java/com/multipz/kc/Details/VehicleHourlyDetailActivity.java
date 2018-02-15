package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddVehicleDetailActivity;
import com.multipz.kc.Activity.AddVehicleHourlyActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.Constant_method;

public class VehicleHourlyDetailActivity extends AppCompatActivity {
    TextView txt_com_name, txt_vehicle_no, txt_user_name, txt_amount, txt_start_date, txt_end_date;
    String txt_vehicle;
    String cnmae, vno, uname, amount, sdate, edate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_list_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_hourly_detail));

        txt_com_name = (TextView) findViewById(R.id.txt_com_name);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_user_name = (TextView) findViewById(R.id.txt_username);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        txt_end_date = (TextView) findViewById(R.id.txt_end_Date);
        txt_vehicle_no = (TextView) findViewById(R.id.txt_vehicle_no);

        id = getIntent().getStringExtra("vehicle_hourly_id");
        cnmae = getIntent().getStringExtra("companyName");
        vno = getIntent().getStringExtra("vehicle_no");
        uname = getIntent().getStringExtra("userName");
        amount = getIntent().getStringExtra("amount");
        sdate = getIntent().getStringExtra("start_time");
        edate = getIntent().getStringExtra("end_time");

        txt_com_name.setText(cnmae);
        txt_user_name.setText(uname);
        txt_start_date.setText(sdate);
        txt_end_date.setText(edate);
        txt_amount.setText(amount);
        txt_vehicle_no.setText(vno);

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
                Intent intent = new Intent(VehicleHourlyDetailActivity.this, AddVehicleHourlyActivity.class);
                intent.putExtra("vehicle_hourly_id", id);
                intent.putExtra("companyName", cnmae);
                intent.putExtra("userName", uname);
                intent.putExtra("amount",amount);
                intent.putExtra("start_time",sdate);
                intent.putExtra("end_time",edate);
                intent.putExtra("vehicle_no",vno);
                intent.putExtra("Update", true);
                startActivity(intent);
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

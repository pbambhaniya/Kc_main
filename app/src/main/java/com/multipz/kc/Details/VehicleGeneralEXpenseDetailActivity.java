package com.multipz.kc.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddVehicleGeneralExpenseActivity;
import com.multipz.kc.R;

public class VehicleGeneralEXpenseDetailActivity extends AppCompatActivity {
    String detail, amount, id, vehicleno, vehicletype, date,etype;
    TextView txt_detail, txt_amount, txt_vehicle_no, txt_vehicle_type, txt_date,txt_exp_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_general_expense_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.vehicle_general_ex_detail));

        refrence();
        init();

    }

    private void init() {
        detail = getIntent().getStringExtra("detail");
        id = getIntent().getStringExtra("vehicle_gen_exp_id");
        amount = getIntent().getStringExtra("amount");
        vehicleno = getIntent().getStringExtra("vehicle_no");
        vehicletype = getIntent().getStringExtra("vehicle_type");
        date = getIntent().getStringExtra("date");
        etype = getIntent().getStringExtra("exp_type");

        txt_detail.setText(detail);
        txt_amount.setText(amount);
        txt_vehicle_no.setText(vehicleno);
        txt_vehicle_type.setText(vehicletype);
        txt_date.setText(date);
        txt_exp_type.setText(etype);
    }

    private void refrence() {
        txt_detail = (TextView) findViewById(R.id.txt_detail);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_vehicle_no = (TextView) findViewById(R.id.txt_vehicle_no);
        txt_vehicle_type = (TextView) findViewById(R.id.txt_vehicle_type);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_exp_type = (TextView) findViewById(R.id.txt_exp_type);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
                Intent intent = new Intent(VehicleGeneralEXpenseDetailActivity.this, AddVehicleGeneralExpenseActivity.class);
                intent.putExtra("vehicle_gen_exp_id", id);
                intent.putExtra("detail", detail);
                intent.putExtra("amount", amount);
                intent.putExtra("vehicle_no", vehicleno);
                intent.putExtra("vehicle_type", vehicletype);
                intent.putExtra("date", date);
                intent.putExtra("exp_type",etype);
                intent.putExtra("Update", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
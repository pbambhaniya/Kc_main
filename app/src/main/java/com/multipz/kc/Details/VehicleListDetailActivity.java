package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddVehicleDetailActivity;
import com.multipz.kc.R;

public class VehicleListDetailActivity extends AppCompatActivity {
    TextView vehicle_type, vehicle_no, txt_date;
    String txt_vehicle, txt_vehicle_no, id, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_hourly_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_detail));

        vehicle_type = (TextView) findViewById(R.id.txt_vehicle);
        txt_date = (TextView) findViewById(R.id.txt_date);
        vehicle_no = (TextView) findViewById(R.id.txt_vehicle_no);

        txt_vehicle = getIntent().getStringExtra("vehicle_type");
        id = getIntent().getStringExtra("vehicle_detail_id");
        txt_vehicle_no = getIntent().getStringExtra("vehicle_no");
        date = getIntent().getStringExtra("date");

        txt_date.setText(date);
        vehicle_type.setText(txt_vehicle);
        vehicle_no.setText(txt_vehicle_no);
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
                Intent intent = new Intent(VehicleListDetailActivity.this, AddVehicleDetailActivity.class);
                intent.putExtra("vehicle_detail_id", id);
                intent.putExtra("vehicle_type", txt_vehicle);
                intent.putExtra("vehicle_no", txt_vehicle_no);
                intent.putExtra("date", date);
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

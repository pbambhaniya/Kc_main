package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddVehicleTypeActivity;
import com.multipz.kc.R;

public class VehicleTypeDetailActivity extends AppCompatActivity {
    TextView txt_vehicle, txt_date;
    String vehicle, date;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_type_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_type_detail));
        txt_vehicle = (TextView) findViewById(R.id.txt_vehicle);
        txt_date = (TextView) findViewById(R.id.txt_date);

        vehicle = getIntent().getStringExtra("vehicle_type");
        id = getIntent().getStringExtra("vehicle_type_id");
        date = getIntent().getStringExtra("date");

        txt_vehicle.setText(vehicle);
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
                Intent intent = new Intent(VehicleTypeDetailActivity.this, AddVehicleTypeActivity.class);
                intent.putExtra("vehicle_type_id", id);
                intent.putExtra("vehicle_type", vehicle);
                intent.putExtra("date",date);
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

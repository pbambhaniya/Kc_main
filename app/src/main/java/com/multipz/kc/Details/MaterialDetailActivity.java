package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddMaterialActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.Constant_method;

public class MaterialDetailActivity extends AppCompatActivity {


    String asset_name, asset_id, date;
    TextView name, txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Material_detail));
        name = (TextView) findViewById(R.id.asset_name);
        txt_date = (TextView) findViewById(R.id.txt_date);
        asset_name = getIntent().getStringExtra("material_type");
        asset_id = getIntent().getStringExtra("material_id");
        date = getIntent().getStringExtra("date");
        name.setText(asset_name);
        txt_date.setText(date);
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
                Intent material = new Intent(MaterialDetailActivity.this, AddMaterialActivity.class);
                material.putExtra("material_type", asset_name);
                material.putExtra("material_id", asset_id);
                material.putExtra("date", date);
                material.putExtra("Update", true);
                startActivity(material);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

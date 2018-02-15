package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddMaterialCompanyActivity;
import com.multipz.kc.R;

public class MaterialCompanyDetailActivity extends AppCompatActivity {
    TextView asset_name,company_name;
    String a_name,c_name,company_asset_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_company_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Material_com_detail));


        asset_name=(TextView)findViewById(R.id.asset_name);
        company_name=(TextView)findViewById(R.id.company_name);


        company_asset_id = getIntent().getStringExtra("material_comp_id");
        a_name = getIntent().getStringExtra("matetailName");
        c_name = getIntent().getStringExtra("name");

        asset_name.setText(a_name);
        company_name.setText(c_name);
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
                Intent company_master=new Intent(MaterialCompanyDetailActivity.this,AddMaterialCompanyActivity.class);
                company_master.putExtra("material_comp_id", company_asset_id);
                company_master.putExtra("matetailName", a_name);
                company_master.putExtra("name",c_name);
                company_master.putExtra("Update",true);
                startActivity(company_master);
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

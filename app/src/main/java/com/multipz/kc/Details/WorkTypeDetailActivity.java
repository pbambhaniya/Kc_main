package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddWorkTypeActivity;
import com.multipz.kc.R;

public class WorkTypeDetailActivity extends AppCompatActivity {

    TextView worktype, txt_date;
    String w_name, c_date, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_type_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Worktype_detail));
        worktype = (TextView) findViewById(R.id.txt_worktpe);
        txt_date = (TextView) findViewById(R.id.txt_date);

        w_name = getIntent().getStringExtra("work_type");
        id = getIntent().getStringExtra("work_type_id");
        c_date = getIntent().getStringExtra("cdate");
        worktype.setText(w_name);
        txt_date.setText(c_date);

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
                Intent company_master = new Intent(WorkTypeDetailActivity.this, AddWorkTypeActivity.class);
                company_master.putExtra("work_type", w_name);
                company_master.putExtra("work_type_id", id);
                company_master.putExtra("date",c_date);
                company_master.putExtra("Update", true);
                startActivity(company_master);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
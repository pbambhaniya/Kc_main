package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddKCExpenseActivity;
import com.multipz.kc.Activity.AddSiteGeneralExpenseActivity;
import com.multipz.kc.R;

public class SiteGeneralExpenseDetailActivity extends AppCompatActivity {
    TextView worktype, txt_date, txt_kc_detail, txt_kc_amount, txt_project;
    String kc_detail, kc_amount, id, pname, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_general_expense_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Site_general_ex_detail));
        worktype = (TextView) findViewById(R.id.txt_worktpe);
        txt_date = (TextView) findViewById(R.id.txt_date);

        txt_project = (TextView) findViewById(R.id.txt_project);
        txt_kc_detail = (TextView) findViewById(R.id.txt_kc_detail);
        txt_kc_amount = (TextView) findViewById(R.id.txt_kc_amount);

        kc_amount = getIntent().getStringExtra("amount");
        kc_detail = getIntent().getStringExtra("detail");
        id = getIntent().getStringExtra("side_gen_exp_id");
        pname = getIntent().getStringExtra("side_sort_name");
        date = getIntent().getStringExtra("date");


        txt_kc_detail.setText(kc_detail);
        txt_kc_amount.setText(kc_amount);
        txt_project.setText(pname);
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
                Intent intent = new Intent(SiteGeneralExpenseDetailActivity.this, AddSiteGeneralExpenseActivity.class);
                intent.putExtra("detail", kc_detail);
                intent.putExtra("amount", kc_amount);
                intent.putExtra("side_gen_exp_id", id);
                intent.putExtra("date", date);
                intent.putExtra("side_sort_name", pname);
                intent.putExtra("Update", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddKCExpenseActivity;
import com.multipz.kc.R;

public class KCExpenseDetailActivity extends AppCompatActivity {
    TextView worktype, txt_kc_detail, txt_kc_amount, txt_date;
    String kc_detail, kc_amount, id, date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_expense_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("KC Detail");
        worktype = (TextView) findViewById(R.id.txt_worktpe);
        txt_date = (TextView) findViewById(R.id.txt_date);

        txt_kc_detail = (TextView) findViewById(R.id.txt_kc_detail);
        txt_kc_amount = (TextView) findViewById(R.id.txt_kc_amount);
        kc_detail = getIntent().getStringExtra("detail");
        id = getIntent().getStringExtra("kc_exp_id");
        kc_amount = getIntent().getStringExtra("amount");
        date=getIntent().getStringExtra("date");

        txt_kc_detail.setText(kc_detail);
        txt_kc_amount.setText(kc_amount);
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
                Intent Kc_exp = new Intent(KCExpenseDetailActivity.this, AddKCExpenseActivity.class);
                Kc_exp.putExtra("detail", kc_detail);
                Kc_exp.putExtra("amount", kc_amount);
                Kc_exp.putExtra("kc_exp_id", id);
                Kc_exp.putExtra("date", date);
                Kc_exp.putExtra("Update", true);
                startActivity(Kc_exp);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
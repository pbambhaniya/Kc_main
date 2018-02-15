package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddBankActivity;
import com.multipz.kc.R;

public class BankDetailActivity extends AppCompatActivity {
    String bank_name, bank_branch, bank_ifsc, bank_id, date;
    TextView b_name, b_ifsc, b_branch, txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_detail);
        setTitle(getResources().getString(R.string.Bank_Detail));

        b_name = (TextView) findViewById(R.id.bank_name);
        b_ifsc = (TextView) findViewById(R.id.ifsc_code);
        b_branch = (TextView) findViewById(R.id.branch);
        txt_date = (TextView) findViewById(R.id.txt_date);

        bank_id = getIntent().getStringExtra("bank_id");
        bank_name = getIntent().getStringExtra("bankname");
        bank_branch = getIntent().getStringExtra("branch");
        bank_ifsc = getIntent().getStringExtra("ifsc");
        date = getIntent().getStringExtra("date");
        txt_date.setText(date);
        b_name.setText(bank_name);
        b_ifsc.setText(bank_ifsc);
        b_branch.setText(bank_branch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Intent bank = new Intent(BankDetailActivity.this, AddBankActivity.class);
                bank.putExtra("bank_id", bank_id);
                bank.putExtra("bankname", bank_name);
                bank.putExtra("branch", bank_branch);
                bank.putExtra("date", date);
                bank.putExtra("ifsc", bank_ifsc);
                bank.putExtra("Update", true);
                startActivity(bank);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddAmountStaffActivity;
import com.multipz.kc.Activity.AddStaffsActivity;
import com.multipz.kc.R;

public class AmountStaffDetailActivity extends AppCompatActivity {

    TextView txt_date, txt_pro_name, txt_user_name, txt_bank_name, txt_pay_type, txt_amount, txt_chequeno;
    String sdate, pname, uname, bname, ptype, amt, cno, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_staff_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Amount_toStaff_detail));
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_date = (TextView) findViewById(R.id.txt_date);

        txt_pro_name = (TextView) findViewById(R.id.txt_pro_name);

        txt_user_name = (TextView) findViewById(R.id.txt_user_name);

        txt_bank_name = (TextView) findViewById(R.id.txt_bank_name);

        txt_pay_type = (TextView) findViewById(R.id.txt_pay_type);

        txt_chequeno = (TextView) findViewById(R.id.txt_chequeno);


        id = getIntent().getStringExtra("staff_amount_id");
        pname = getIntent().getStringExtra("side_sort_name");
        uname = getIntent().getStringExtra("userName");
        bname = getIntent().getStringExtra("bankName");
        ptype = getIntent().getStringExtra("payment_type");
        amt = getIntent().getStringExtra("amount");
        cno = getIntent().getStringExtra("payment_info");
        sdate = getIntent().getStringExtra("amount_date");

        txt_amount.setText(amt);
        txt_date.setText(sdate);
        txt_pro_name.setText(pname);
        txt_user_name.setText(uname);
        txt_bank_name.setText(bname);
        txt_pay_type.setText(ptype);
        txt_chequeno.setText(cno);


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
                Intent intent = new Intent(AmountStaffDetailActivity.this, AddAmountStaffActivity.class);

                intent.putExtra("staff_amount_id", id);
                intent.putExtra("side_sort_name", pname);
                intent.putExtra("userName", uname);
                intent.putExtra("bankName", bname);
                intent.putExtra("payment_type", ptype);
                intent.putExtra("amount", amt);
                intent.putExtra("payment_info", cno);
                intent.putExtra("amount_date",sdate);
                intent.putExtra("Update", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
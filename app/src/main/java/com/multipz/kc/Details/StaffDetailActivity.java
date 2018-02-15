package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Activity.AddStaffsActivity;
import com.multipz.kc.Activity.AddWorkTypeActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.squareup.picasso.Picasso;

public class StaffDetailActivity extends AppCompatActivity {


    TextView txt_work_type, txt_name, txt_mobileno, txt_password, txt_salary, txt_salary_type, txt_date;
    String e_type, name, mobile, password, salary, salarytype, id, img, proof_img, date;
    ImageView txt_pro_img, txt_proof_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_detail);
        setTitle(getResources().getString(R.string.Staff_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_work_type = (TextView) findViewById(R.id.txt_work_type);
        txt_mobileno = (TextView) findViewById(R.id.txt_mobileno);
        txt_password = (TextView) findViewById(R.id.txt_password);
        txt_salary = (TextView) findViewById(R.id.txt_salary);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_salary_type = (TextView) findViewById(R.id.txt_salary_type);
        txt_pro_img = (ImageView) findViewById(R.id.txt_pro_img);
        txt_proof_img = (ImageView) findViewById(R.id.txt_proof_img);

        id = getIntent().getStringExtra("user_id");
        e_type = getIntent().getStringExtra("work_type");
        name = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        password = getIntent().getStringExtra("password");
        salary = getIntent().getStringExtra("salary");
        salarytype = getIntent().getStringExtra("salary_based_type");
        img = getIntent().getStringExtra("profile_img");
        // img = img.replace(" ", "%20");
        proof_img = getIntent().getStringExtra("proof_img");
        // proof_img = proof_img.replace(" ","%20");
        date = getIntent().getStringExtra("date");

        txt_name.setText(name);
        txt_work_type.setText(e_type);
        txt_mobileno.setText(mobile);
        txt_password.setText(password);
        txt_salary.setText(salary);
        txt_salary_type.setText(salarytype);
        txt_date.setText(date);
        txt_salary_type.setText(salarytype.contentEquals("D") ? "Daily Base" : "Fix");

        Picasso.with(getApplicationContext()).load(Config.img_url + img).into(txt_pro_img);
        Picasso.with(getApplicationContext()).load(Config.img_url + proof_img).into(txt_proof_img);

        txt_pro_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffDetailActivity.this, DisplayImgActivity.class);
                i.putExtra("imgurl", Config.img_url + img);
                startActivity(i);
            }
        });
        txt_proof_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StaffDetailActivity.this, DisplayImgActivity.class);
                i.putExtra("imgurl", Config.img_url + proof_img);
                startActivity(i);
            }
        });

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
                Intent intent = new Intent(StaffDetailActivity.this, AddStaffsActivity.class);


                intent.putExtra("work_type", e_type);
                intent.putExtra("name", name);
                intent.putExtra("mobile", mobile);
                intent.putExtra("password", password);
                intent.putExtra("salary", salary);
                intent.putExtra("salary_based_type", salarytype);
                intent.putExtra("profile_img", img);
                intent.putExtra("proof_img", proof_img);
                intent.putExtra("user_id", id);
                intent.putExtra("date", date);
                intent.putExtra("Update", true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
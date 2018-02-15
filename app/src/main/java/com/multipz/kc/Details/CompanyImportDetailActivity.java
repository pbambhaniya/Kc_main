package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Activity.AddCompanyImportActivity;
import com.multipz.kc.Activity.AddCompanyPayActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.squareup.picasso.Picasso;

public class CompanyImportDetailActivity extends AppCompatActivity {


    TextView txt_com_name, txt_pro_name, txt_material_name, txt_amount, txt_detail, txt_challon_no, txt_weigh_bag, txt_weigh_type, txt_price_ton, txt_total_price_ton, txt_date;
    ImageView txt_chhallanimg;

    String cname, pname, mname, detail, amount, challon_no, img, id, weight_bags, weight_unit, price_ton, total_price_ton, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_import_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_Import_detail));


        txt_com_name = (TextView) findViewById(R.id.txt_com_name);
        txt_pro_name = (TextView) findViewById(R.id.txt_pro_name);

        txt_material_name = (TextView) findViewById(R.id.txt_material_name);
        txt_amount = (TextView) findViewById(R.id.txt_amount);
        txt_detail = (TextView) findViewById(R.id.txt_detail);
        txt_challon_no = (TextView) findViewById(R.id.txt_challon_no);
        txt_chhallanimg = (ImageView) findViewById(R.id.txt_chhallanimg);

        txt_weigh_bag = (TextView) findViewById(R.id.txt_weigh_bag);
        txt_weigh_type = (TextView) findViewById(R.id.txt_weigh_type);
        txt_price_ton = (TextView) findViewById(R.id.txt_price_ton);
        txt_total_price_ton = (TextView) findViewById(R.id.txt_total_price_ton);

        txt_date = (TextView) findViewById(R.id.txt_date);


        id = getIntent().getStringExtra("comp_import_id");
        cname = getIntent().getStringExtra("companyName");
        pname = getIntent().getStringExtra("side_sort_name");
        mname = getIntent().getStringExtra("material_type");
        detail = getIntent().getStringExtra("detail");
        amount = getIntent().getStringExtra("amount");
        challon_no = getIntent().getStringExtra("challan_no");
        img = getIntent().getStringExtra("challan_image");
        img = img.replace(" ", "%20");
        weight_bags = getIntent().getStringExtra("weight_bags");
        weight_unit = getIntent().getStringExtra("weight_unit");
        price_ton = getIntent().getStringExtra("price_ton");
        total_price_ton = getIntent().getStringExtra("total_price_ton");
        date = getIntent().getStringExtra("date");


        txt_material_name.setText(mname);
        txt_pro_name.setText(pname);
        txt_com_name.setText(cname);
        txt_detail.setText(detail);
        txt_amount.setText(amount);
        txt_challon_no.setText(challon_no);
        txt_weigh_bag.setText(weight_bags);
        txt_weigh_type.setText(weight_unit);
        txt_price_ton.setText(price_ton);
        txt_total_price_ton.setText(total_price_ton);
        txt_date.setText(date);

        Picasso.with(getApplicationContext()).load(Config.img_url + img).placeholder(R.drawable.img).into(txt_chhallanimg);
        //Picasso.with(getApplicationContext()).load(Config.img_url + img).into(txt_chhallanimg);

        txt_chhallanimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompanyImportDetailActivity.this, DisplayImgActivity.class);
                i.putExtra("imgurl", Config.img_url + img);
                startActivity(i);
            }
        });
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
                Intent company_import = new Intent(CompanyImportDetailActivity.this, AddCompanyImportActivity.class);
                company_import.putExtra("comp_import_id", id);
                company_import.putExtra("companyName", cname);
                company_import.putExtra("side_sort_name", pname);
                company_import.putExtra("material_type", mname);
                company_import.putExtra("challan_image", Config.img_url + img);
                company_import.putExtra("challan_no", challon_no);
                company_import.putExtra("amount", amount);
                company_import.putExtra("detail", detail);
                company_import.putExtra("date", date);
                company_import.putExtra("weight_bags", weight_bags);
                company_import.putExtra("weight_unit", weight_unit);
                company_import.putExtra("price_ton", price_ton);
                company_import.putExtra("total_price_ton", total_price_ton);
                company_import.putExtra("Update", true);
                startActivity(company_import);
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

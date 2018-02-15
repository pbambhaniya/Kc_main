package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.multipz.kc.Activity.AddVehicleHourlyActivity;
import com.multipz.kc.Activity.AddVehicleTransportActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.squareup.picasso.Picasso;

public class VehicleTransportDetailActivity extends AppCompatActivity {
    TextView txt_weigh_bag, txt_price_ton, txt_weigh_type, txt_total_price_ton, txt_username, txt_vehicle_name, txt_material_name, txt_load_to, txt_load_com_name, txt_load_amount, txt_empty_to, txt_empty_com_name, txt_empty_amount;
    String suser, challan_no, challan_image, svehiclename, smaterial, sloadcompany, semptycomid, eloadto, eloadamount, emptyto, emptyamount, id, weight_bags, weight_unit, price_per_ton, total_price_per_ton;
    private ImageView txt_chhallanimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_transport_detail);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Vehicle Transport Detail");
        txt_username = (TextView) findViewById(R.id.txt_username);
        txt_vehicle_name = (TextView) findViewById(R.id.txt_vehicle_name);
        txt_material_name = (TextView) findViewById(R.id.txt_material_name);
        txt_load_to = (TextView) findViewById(R.id.txt_load_to);
        txt_load_com_name = (TextView) findViewById(R.id.txt_load_com_name);
        txt_load_amount = (TextView) findViewById(R.id.txt_load_amount);
        txt_empty_to = (TextView) findViewById(R.id.txt_empty_to);
        txt_empty_com_name = (TextView) findViewById(R.id.txt_empty_com_name);
        txt_empty_amount = (TextView) findViewById(R.id.txt_empty_amount);
        txt_price_ton = (TextView) findViewById(R.id.txt_price_ton);
        txt_total_price_ton = (TextView) findViewById(R.id.txt_total_price_ton);
        txt_chhallanimg = (ImageView) findViewById(R.id.txt_chhallanimg);

        txt_weigh_bag = (TextView) findViewById(R.id.txt_weigh_bag);
        txt_weigh_type = (TextView) findViewById(R.id.txt_weigh_type);
        txt_price_ton = (TextView) findViewById(R.id.txt_price_ton);
        txt_total_price_ton = (TextView) findViewById(R.id.txt_total_price_ton);

        id = getIntent().getStringExtra("vehicle_transport_id");
        eloadto = getIntent().getStringExtra("load_to");
        eloadamount = getIntent().getStringExtra("load_amount");

        emptyto = getIntent().getStringExtra("empty_to");
        emptyamount = getIntent().getStringExtra("empty_amount");
        sloadcompany = getIntent().getStringExtra("loadCompanyName");
        semptycomid = getIntent().getStringExtra("EmptyCompanyName");
        suser = getIntent().getStringExtra("userName");
        svehiclename = getIntent().getStringExtra("vehicle_no");
        smaterial = getIntent().getStringExtra("material_type");


        weight_bags = getIntent().getStringExtra("weight_bags");
        weight_unit = getIntent().getStringExtra("weight_unit");
        challan_image = getIntent().getStringExtra("challan_image");
        challan_no = getIntent().getStringExtra("challan_no");
        price_per_ton = getIntent().getStringExtra("price_per_ton");
        total_price_per_ton = getIntent().getStringExtra("total_price_per_ton");

        txt_username.setText(suser);
        txt_vehicle_name.setText(svehiclename);
        txt_material_name.setText(smaterial);
        txt_load_to.setText(eloadto);
        txt_load_com_name.setText(sloadcompany);
        txt_load_amount.setText(eloadamount);
        txt_empty_to.setText(emptyto);
        txt_empty_com_name.setText(semptycomid);
        txt_empty_amount.setText(emptyamount);

        txt_weigh_bag.setText(weight_bags);
        txt_weigh_type.setText(weight_unit);
        txt_price_ton.setText(price_per_ton);
        txt_total_price_ton.setText(total_price_per_ton);

        Picasso.with(getApplicationContext()).load(Config.img_url + challan_image).placeholder(R.drawable.img).into(txt_chhallanimg);
        txt_chhallanimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VehicleTransportDetailActivity.this, DisplayImgActivity.class);
                i.putExtra("imgurl", Config.img_url + challan_image);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}

package com.multipz.kc.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.squareup.picasso.Picasso;

public class DisplayImgActivity extends AppCompatActivity {

    private ImageView displayImg;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_img);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.view));

        displayImg = (ImageView) findViewById(R.id.displayImg);
        Intent i = getIntent();
        imgUrl = i.getStringExtra("imgurl");
        Picasso.with(getApplicationContext()).load(imgUrl).into(displayImg);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}

package com.multipz.kc.Details;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.multipz.kc.Activity.AddProjectActivity;
import com.multipz.kc.Activity.AddProjectReturnActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.MyAsyncTask;

public class ProjectReturnDetailActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    String sortname, sd, fmg, tds, id, date;
    SwitchCompat switchComp, switchPay;
    Context context;

    TextView txt_sort_name, txt_sd, txt_fmg, txt_tds, txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_return_detail);


        setTitle(getResources().getString(R.string.Project_Return_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        txt_sort_name = (TextView) findViewById(R.id.txt_sort_name);
        txt_sd = (TextView) findViewById(R.id.txt_sd);
        txt_fmg = (TextView) findViewById(R.id.txt_fmg);
        txt_tds = (TextView) findViewById(R.id.txt_tds);
        txt_date = (TextView) findViewById(R.id.txt_date);


//
//        txt_s_now = (TextView) findViewById(R.id.txt_s_now);
//        txt_budget = (TextView) findViewById(R.id.txt_budget);
//        txt_deposite = (TextView) findViewById(R.id.txt_deposite);
//        txt_sdate = (TextView) findViewById(R.id.txt_sdate);
//        txt_edate = (TextView) findViewById(R.id.txt_edate);
//        txt_location = (TextView) findViewById(R.id.txt_location);
//        switchComp = (SwitchCompat) findViewById(R.id.swtch_comp);
//        switchPay = (SwitchCompat) findViewById(R.id.swtch_pay);


        id = getIntent().getStringExtra("project_id");
        sortname = getIntent().getStringExtra("side_sort_name");
        sd = getIntent().getStringExtra("sd");
        fmg = getIntent().getStringExtra("fmg");
        tds = getIntent().getStringExtra("tds");
        date = getIntent().getStringExtra("date");

//        complete = getIntent().getStringExtra("is_complete");

        txt_sort_name.setText(sortname);
        txt_sd.setText(sd);
        txt_fmg.setText(fmg);
        txt_tds.setText(tds);
        txt_date.setText(date);
//        txt_sdate.setText(sdate);
//        txt_edate.setText(edate);
//        txt_location.setText(location);
//        p_complete.setText(complete);

//        if (complete.toLowerCase().contentEquals("y")) {
//            switchComp.setChecked(true);
//            switchPay.setEnabled(true);
//            switchComp.setEnabled(false);
//        } else {
//            switchComp.setChecked(false);
//            switchPay.setEnabled(false);
//            switchComp.setEnabled(true);
//        }

//        if (payment.toLowerCase().contentEquals("y")) {
//            switchPay.setChecked(true);
//            switchPay.setEnabled(false);
//        } else {
//            switchPay.setChecked(false);
//        }


//        switchComp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    AlertDialog.Builder builder;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
//                    } else {
//                        builder = new AlertDialog.Builder(context);
//                    }
//                    builder.setTitle("Confirmation")
//                            .setMessage("Are you sure you want to complete this site?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String param = "{\"project_id\":\"1\",\"flag\":\"C\",\"action\":\"compaleteProjectAndFees\"}";
//                                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, ProjectDetailActivity.this, param, Config.API_TOKEN_ADD_PROJ);
//                                    myAsyncTask.execute();
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    switchComp.setChecked(false);
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }
//            }
//        });

//        switchPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    AlertDialog.Builder builder;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
//                    } else {
//                        builder = new AlertDialog.Builder(context);
//                    }
//                    builder.setTitle("Confirmation")
//                            .setMessage("Are you sure you got payment?")
//                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    String param = "{\"project_id\":\"1\",\"flag\":\"P\",\"action\":\"compaleteProjectAndFees\"}";
//                                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, ProjectDetailActivity.this, param, Config.API_TOKEN_UPDATE_PROJ);
//                                    myAsyncTask.execute();
//                                }
//                            })
//                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    switchPay.setChecked(false);
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }
//            }
//        });
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
                Intent project_return = new Intent(ProjectReturnDetailActivity.this, AddProjectReturnActivity.class);
                project_return.putExtra("project_id", id);
                project_return.putExtra("side_sort_name", sortname);
                project_return.putExtra("sd", sd);
                project_return.putExtra("fmg", fmg);
                project_return.putExtra("date", date);
                project_return.putExtra("tds", tds);
                project_return.putExtra("Update", true);
                startActivity(project_return);
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

    @Override
    public void onResponseService(String response, int flag) {
//        if (flag == Config.API_TOKEN_ADD_PROJ) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                if (jsonObject.getInt("status") == 1) {
//                    switchComp.setChecked(true);
//                    switchComp.setEnabled(false);
//                    switchPay.setEnabled(true);
//                } else {
//                    switchComp.setChecked(false);
//                }
//                Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (flag == Config.API_TOKEN_UPDATE_PROJ) {
//            try {
//                JSONObject jsonObject = new JSONObject(response);
//                if (jsonObject.getInt("status") == 1) {
//                    switchPay.setChecked(true);
//                    switchPay.setEnabled(false);
//                } else {
//                    switchPay.setChecked(false);
//                }
//                Toast.makeText(context, jsonObject.getString("msg"), Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
    }
}


package com.multipz.kc.Details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Activity.AddProjectActivity;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.MyAsyncTask;

import org.json.JSONObject;

public class ProjectDetailActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    TextView txt_sort_name, txt_s_now, txt_budget, txt_deposite, txt_sdate, txt_edate, txt_location, txt_date, p_location, p_complete, p_payment;
    String sortname, snow, budget, deposite, sdate, edate, location, complete, id, date;
    SwitchCompat switchComp, switchPay;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail);

        setTitle(getResources().getString(R.string.Project_detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        txt_sort_name = (TextView) findViewById(R.id.txt_sort_name);
        txt_s_now = (TextView) findViewById(R.id.txt_s_now);
        txt_date = (TextView) findViewById(R.id.txt_date);
        txt_budget = (TextView) findViewById(R.id.txt_budget);
        txt_deposite = (TextView) findViewById(R.id.txt_deposite);
        txt_sdate = (TextView) findViewById(R.id.txt_sdate);
        txt_edate = (TextView) findViewById(R.id.txt_edate);
        txt_location = (TextView) findViewById(R.id.txt_location);
        switchComp = (SwitchCompat) findViewById(R.id.swtch_comp);
//        switchPay = (SwitchCompat) findViewById(R.id.swtch_pay);


        id = getIntent().getStringExtra("project_id");
        sortname = getIntent().getStringExtra("side_sort_name");
        snow = getIntent().getStringExtra("name_of_work");
        budget = getIntent().getStringExtra("budget");
        deposite = getIntent().getStringExtra("deposite");
        sdate = getIntent().getStringExtra("start_date");
        edate = getIntent().getStringExtra("end_date");
        location = getIntent().getStringExtra("location");
        date = getIntent().getStringExtra("date");
//        complete = getIntent().getStringExtra("is_complete");

        txt_sort_name.setText(sortname);
        txt_s_now.setText(snow);
        txt_budget.setText(budget);
        txt_deposite.setText(deposite);
        txt_sdate.setText(sdate);
        txt_edate.setText(edate);
        txt_location.setText(location);
        txt_date.setText(date);
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
                Intent project = new Intent(ProjectDetailActivity.this, AddProjectActivity.class);
                project.putExtra("project_id", id);
                project.putExtra("side_sort_name", sortname);
                project.putExtra("name_of_work", snow);
                project.putExtra("budget", budget);
                project.putExtra("deposite", deposite);
                project.putExtra("start_date", sdate);
                project.putExtra("end_date", edate);
                project.putExtra("deposite", deposite);
                project.putExtra("location", location);
                project.putExtra("date", date);
                project.putExtra("Update", true);
                startActivity(project);
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
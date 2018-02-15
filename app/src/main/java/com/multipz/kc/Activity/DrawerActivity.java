package com.multipz.kc.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.multipz.kc.Fragment.AdminDeshboardFragment;
import com.multipz.kc.Fragment.AssetFragment;
import com.multipz.kc.Fragment.CompanyFragment;
import com.multipz.kc.Fragment.GeneralExpenseFragment;
import com.multipz.kc.Fragment.IncomeFragment;
import com.multipz.kc.Fragment.ProjectFragment;
import com.multipz.kc.Fragment.ReportFragment;
import com.multipz.kc.Fragment.SiteFragment;
import com.multipz.kc.Fragment.StaffFragment;
import com.multipz.kc.Fragment.VehicleFragment;
import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.CustomTypefaceSpan;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONObject;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyAsyncTask.AsyncInterface {
    NavigationView navigationView;
    Context context;
    Shared shared;
    public static DrawerActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

        setTitle("Home");
        context = this;
        shared = new Shared(this);
        activity = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setNavigationView(getResources().getColor(R.color.colorPrimaryDark));

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        if (savedInstanceState == null) {
            navigationView.getMenu().getItem(0).setChecked(true);
            onNavigationItemSelected(navigationView.getMenu().getItem(0));
        }

        callComponenet();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Fragment fragment = new AdminDeshboardFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.add_staff) {
            Fragment fragment = new StaffFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        } else if (id == R.id.menu_vehicle) {
            Fragment fragment = new VehicleFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.add_project) {
            Fragment fragment = new ProjectFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.add_assets) {
            Fragment fragment = new AssetFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        }
//        else if (id == R.id.site) {
//            Fragment fragment = new SiteFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//        }
//        else if (id == R.id.create_manage) {
//            Fragment fragment = new IncomeFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//        }
        else if (id == R.id.add_company) {
            Fragment fragment = new CompanyFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.add_general_expense) {
            Fragment fragment = new GeneralExpenseFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        } else if (id == R.id.reports) {
            Fragment fragment = new ReportFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }

//        else if (id == R.id.profile) {
//            Fragment fragment = new Profile_Fragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//
//        } else if (id == R.id.settings) {
//
//
//        }

        else if (id == R.id.logout) {
            shared.putBoolean(Config.isLogin, false);
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "font/Oxygen-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void setNavigationView(int color) {
        int navDefaultTextColor = Color.parseColor("#f8f8f8");
        int navDefaultIconColor = Color.parseColor("#f8f8f8");

        ColorStateList navMenuTextList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor,
                        navDefaultTextColor
                }
        );

        ColorStateList navMenuIconList = new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{android.R.attr.state_enabled},
                        new int[]{android.R.attr.state_pressed},
                        new int[]{android.R.attr.state_focused},
                        new int[]{android.R.attr.state_pressed}
                },
                new int[]{
                        color,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor,
                        navDefaultIconColor
                }
        );

        navigationView.setItemTextColor(navMenuTextList);
        navigationView.setItemIconTintList(navMenuIconList);
    }

    public void callComponenet() {

        new CallConfigDataApi(shared).execute();

//        String param = "{\"action\":\"getComponents\"}";
//        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.API_TOKEN_CONTAINER);
//        myAsyncTask.execute();
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        if (flag == Config.API_TOKEN_CONTAINER) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    shared.putString(Config.Material, jsonObject1.getString("material"));
                    shared.putString(Config.VehicleType, jsonObject1.getString("vehicle_type"));
                    shared.putString(Config.Project, jsonObject1.getString("project"));
                    shared.putString(Config.Vehicle, jsonObject1.getString("vehicle"));
                    shared.putString(Config.Bank, jsonObject1.getString("bank"));
                    shared.putString(Config.company, jsonObject1.getString("company"));
                    shared.putString(Config.Work_type, jsonObject1.getString("work_type"));
                    shared.putString(Config.Staff, jsonObject1.getString("staff"));
                    shared.putString(Config.AllStaff, jsonObject1.getString("allStaff"));
//                    shared.putString(Config.Wholeseller, jsonObject1.getString("wholeseller"));
//
//                    shared.putString(Config.OnlyProject, jsonObject1.getString("only_project"));
//
//
//                    shared.putString(Config.Company_mst, jsonObject1.getString("company_mst"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

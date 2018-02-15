package com.multipz.kc.Vehicle;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.multipz.kc.Activity.AddVehicleTransportActivity;
import com.multipz.kc.Adapter.VehicleTransportAdapter;
import com.multipz.kc.Details.VehicleTransportDetailActivity;
import com.multipz.kc.Model.VehicleTransportModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VehicleTransport extends AppCompatActivity
        implements MyAsyncTask.AsyncInterface,
        ItemClickListener {
    RecyclerView vehicle_list;
    Shared shared;
    Context context;
    MyAsyncTask.AsyncInterface asyncInterface;
    FloatingActionButton fabAdd;
    private VehicleTransportAdapter adapter;
    private boolean userScrolled = true;
    ArrayList<VehicleTransportModel> userList;
    private int pagecount = 1;
    public int firstVisibleItem, visibleItemCount, totalItemCount;
    private int lastVisibleItem, firstVisible;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_transport);
        setTitle(getResources().getString(R.string.Vehicle_Transport));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(context);

        reference();

    }

    private void paramforvahicletrspdata() {
        if (Constant_method.checkConn(context)) {
            String param = "{\"page\":\"" + pagecount + "\",\"action\":\"getVehicleTranspData\"}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_Get_vehicle_Transport);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void reference() {
        vehicle_list = (RecyclerView) findViewById(R.id.vehicle_details_list);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddVehicleTransportActivity.class);
                startActivity(intent);
            }
        });

        vehicle_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                totalItemCount = layoutManager.getItemCount();
                firstVisible = layoutManager.findFirstVisibleItemPosition();
                if (userScrolled) {
                    if ((lastVisibleItem + firstVisible) >= totalItemCount) {
                        userScrolled = false;
                        pagecount++;
                        paramforvahicletrspdata();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        pagecount = 1;
        userList = new ArrayList<>();
        paramforvahicletrspdata();
    }

    private void delete(String vid) {
        if (Constant_method.checkConn(context)) {
            String param = "{\"action\":\"editDeleteVehicleTransp\",\"id\":\"" + vid + "\",\"optFlag\":\"d\"}";
            //String param = "{\"id\": \"" + vid + "\",\"optFlag\":\"d\"," + "\"action\":\"editDeleteVehicleTransp\"" + "}";
            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, this, param, Config.APi_editDeleteVehicleTransp);
            myAsyncTask.execute();
        } else {
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        String msg = "";
        int success;
        if (flag == Config.APi_Get_vehicle_Transport) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                JSONArray jsonArray = jsonObject.getJSONArray("data");


                for (int i1 = 0; i1 < jsonArray.length(); i1++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                    VehicleTransportModel transportModel = new VehicleTransportModel();
                    transportModel.setVehicle_transport_id(jsonObject1.getString("vehicle_transport_id"));
                    transportModel.setVehicle_detail_id(jsonObject1.getString("vehicle_detail_id"));
                    transportModel.setUser_id(jsonObject1.getString("user_id"));
                    transportModel.setMaterial_id(jsonObject1.getString("material_id"));
                    transportModel.setLoad_compid(jsonObject1.getString("load_compid"));

                    transportModel.setEmpty_compid(jsonObject1.getString("empty_compid"));

                    transportModel.setLoadCompanyName(jsonObject1.getString("loadCompanyName"));
                    transportModel.setEmptyCompanyName(jsonObject1.getString("EmptyCompanyName"));
                    transportModel.setUserName(jsonObject1.getString("userName"));
                    transportModel.setVehicle_no(jsonObject1.getString("vehicle_no"));
                    transportModel.setMaterial_type(jsonObject1.getString("material_type"));

                    transportModel.setWeight_unit(jsonObject1.getString("weight_unit"));


                    userList.add(transportModel);
                }
                adapter = new VehicleTransportAdapter(context, userList);
                layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                vehicle_list.setLayoutManager(layoutManager);
                vehicle_list.setAdapter(adapter);
                adapter.setClickListener(this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_editDeleteVehicleTransp) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void itemClicked(View View, int position) {
        VehicleTransportModel item = userList.get(position);
        if (View.getId() == R.id.deletetransport) {
            delete(item.getVehicle_transport_id());
            removeAt(position);
//        } else {
//            Intent intent = new Intent(context, VehicleTransportDetailActivity.class);
//            intent.putExtra("vehicle_transport_id", item.getVehicle_transport_id());
//            intent.putExtra("load_to", item.getLoad_to());
//            intent.putExtra("load_amount", item.getLoad_amount());
//            intent.putExtra("empty_to", item.getEmpty_to());
//            intent.putExtra("empty_amount", item.getEmpty_amount());
//            intent.putExtra("loadCompanyName", item.getLoadCompanyName());
//            intent.putExtra("EmptyCompanyName", item.getEmptyCompanyName());
//            intent.putExtra("userName", item.getUserName());
//            intent.putExtra("vehicle_no", item.getVehicle_no());
//            intent.putExtra("material_type", item.getMaterial_type());
//            intent.putExtra("weight_bags", item.getWeight_bags());
//            intent.putExtra("weight_unit", item.getWeight_unit());
//            intent.putExtra("challan_image", item.getChallan_image());
//            intent.putExtra("challan_no", item.getChallan_no());
//            intent.putExtra("price_per_ton", item.getPrice_per_ton());
//            intent.putExtra("total_price_per_ton", item.getTotal_price_per_ton());
//            startActivity(intent);
//        }
        }
    }


    public void removeAt(int position) {
        userList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, userList.size());
    }

}




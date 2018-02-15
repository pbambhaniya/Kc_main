package com.multipz.kc.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class AddVehicleTransportActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    Button add;
    ImageView empty_img, load_img;
    TextView txt_load_date, txt_load_com_name, txt_load_challon_no, txt_load_weight, txt_load_weight_type, txt_load_amount, txt_load_detail, txt_start_date;
    TextView txt_empty_date, txt_empty_com_name, txt_empty_challon_no, txt_empty_weight, txt_empty_weight_type, txt_empty_amount, txt_empty_detail;

    String load_date, load_com_name, load_challon_no, load_weight, load_weight_type, load_amount, load_detail;
    String empty_date, empty_com_name, empty_challon_no, empty_weight, empty_weight_type, empty_amount, empty_detail;
    ArrayList<SpinnerModel> objects_vehicle_no, object_user, objects_vehicle_type, object_material, object_payment_type;
    Context context;
    Shared shared;
    private ArrayAdapter<String> adapter = null;
    Spinner sp_user, sp_vehicle_no, sp_vehicle_type, sp_material, sp_weight_unit_vehicle;

    private String encodedImage = "";
    String l_date, l_challon_no, l_com_name, l_weight_type, l_weight, l_amount, l_detail, l_img, Lid, l_total_amount, Load_img;
    String e_date, e_challon_no, e_com_name, e_weight_type, e_weight, e_amount, e_detail, e_img, E_id, e_total_amount, Empty_img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_transport);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Vehicle_Transport));

        context = this;
        shared = new Shared(context);


        new CallConfigDataApi(shared).execute();
        add = (Button) findViewById(R.id.add);
        empty_img = (ImageView) findViewById(R.id.empty_img);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        load_img = (ImageView) findViewById(R.id.load_img);
        sp_user = (Spinner) findViewById(R.id.sp_user);
        sp_vehicle_no = (Spinner) findViewById(R.id.sp_vehicle_no);
        sp_vehicle_type = (Spinner) findViewById(R.id.sp_vehicle_type);
        sp_material = (Spinner) findViewById(R.id.sp_material);
        sp_weight_unit_vehicle = (Spinner) findViewById(R.id.sp_weight_unit_vehicle);


        getUserName();
        getPaymentType();
        getVehicleNo();
        getVehicleType();
        getMaterialName();

        load_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddVehicleTransportActivity.this, LoadToActivity.class);
                intent.putExtra("name", "Load");
                intent.putExtra("load_date", load_date);
                intent.putExtra("load_com_name", load_com_name);
                intent.putExtra("load_challon_no", load_challon_no);
                intent.putExtra("load_weight", load_weight);
                intent.putExtra("load_weight_type", load_weight_type);
                intent.putExtra("load_amount", load_amount);
                intent.putExtra("load_detail", load_detail);
                startActivityForResult(intent, 101);

            }
        });
        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddVehicleTransportActivity.this, txt_start_date);
            }
        });
        empty_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddVehicleTransportActivity.this, LoadToActivity.class);
                intent.putExtra("name", "Empty");
                intent.putExtra("empty_date", empty_date);
                intent.putExtra("empty_com_name", empty_com_name);
                intent.putExtra("empty_challon_no", empty_challon_no);
                intent.putExtra("empty_weight", empty_weight);
                intent.putExtra("empty_weight_type", empty_weight_type);
                intent.putExtra("empty_amount", empty_amount);
                intent.putExtra("empty_detail", empty_detail);

                startActivityForResult(intent, 102);
            }
        });


        load_date = getResources().getString(R.string.Load_date);
        load_com_name = getResources().getString(R.string.load_com_name);
        load_challon_no = getResources().getString(R.string.Load_Challon_no);
        load_weight = getResources().getString(R.string.Load_weight);
        load_weight_type = getResources().getString(R.string.Load_weight_type);
        load_amount = getResources().getString(R.string.load_amount);
        load_detail = getResources().getString(R.string.load_detail);


        txt_load_date = (TextView) findViewById(R.id.txt_load_date);
        txt_load_date.setText(load_date);
        txt_load_com_name = (TextView) findViewById(R.id.txt_load_com_name);
        txt_load_com_name.setText(load_com_name);
        txt_load_challon_no = (TextView) findViewById(R.id.txt_load_challon_no);
        txt_load_challon_no.setText(load_challon_no);
        txt_load_weight = (TextView) findViewById(R.id.txt_load_weight);
        txt_load_weight.setText(load_weight);
        txt_load_weight_type = (TextView) findViewById(R.id.txt_load_weight_type);
        txt_load_weight_type.setText(load_weight_type);
        txt_load_amount = (TextView) findViewById(R.id.txt_load_amount);
        txt_load_amount.setText(load_amount);
        txt_load_detail = (TextView) findViewById(R.id.txt_load_detail);
        txt_load_detail.setText(load_detail);


        empty_date = getResources().getString(R.string.Empty_date);
        empty_com_name = getResources().getString(R.string.empty_com_name);
        empty_challon_no = getResources().getString(R.string.Empty_challon_no);
        empty_weight = getResources().getString(R.string.Empty_weight);
        empty_weight_type = getResources().getString(R.string.Empty_weight_type);
        empty_amount = getResources().getString(R.string.empty_amount);
        empty_detail = getResources().getString(R.string.empty_detail);

        txt_empty_date = (TextView) findViewById(R.id.txt_empty_date);
        txt_empty_date.setText(empty_date);
        txt_empty_com_name = (TextView) findViewById(R.id.txt_empty_com_name);
        txt_empty_com_name.setText(empty_com_name);
        txt_empty_challon_no = (TextView) findViewById(R.id.txt_empty_challon_no);
        txt_empty_challon_no.setText(empty_challon_no);
        txt_empty_weight = (TextView) findViewById(R.id.txt_empty_weight);
        txt_empty_weight.setText(empty_weight);
        txt_empty_weight_type = (TextView) findViewById(R.id.txt_empty_weight_type);
        txt_empty_weight_type.setText(empty_weight_type);
        txt_empty_amount = (TextView) findViewById(R.id.txt_empty_amount);
        txt_empty_amount.setText(empty_amount);
        txt_empty_detail = (TextView) findViewById(R.id.txt_empty_detail);
        txt_empty_detail.setText(empty_detail);

        sp_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                objects_vehicle_no.clear();
                objects_vehicle_no.add(new SpinnerModel("", getResources().getString(R.string.SElect_Vehicle_No)));
                try {
                    JSONArray jsonArray = new JSONArray(shared.getString(Config.Vehicle, "[{}]"));
                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject staff = jsonArray.getJSONObject(j);
                        if (staff.getString("vehicle_type_id").contentEquals(objects_vehicle_type.get(i).getid())) {
                            SpinnerModel spinnerModel = new SpinnerModel();
                            spinnerModel.setid(staff.getString("vehicle_detail_id"));
                            spinnerModel.setName(staff.getString("vehicle_no"));
                            objects_vehicle_no.add(spinnerModel);
                        }
                    }
                    sp_vehicle_no.setAdapter(new SpinnerAdapter(AddVehicleTransportActivity.this, objects_vehicle_no));
                    try {
                        setSpinnerVType(sp_vehicle_no, objects_vehicle_no, getIntent().getStringExtra("vehicle_no").trim());
                    } catch (Exception e) {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cdate, user, svehicletype, smaterial, ptype;

                cdate = txt_start_date.getText().toString();
                user = object_user.get(sp_user.getSelectedItemPosition()).getid();
                svehicletype = objects_vehicle_no.get(sp_vehicle_type.getSelectedItemPosition()).getid();
                smaterial = object_material.get(sp_material.getSelectedItemPosition()).getid();
                ptype = object_payment_type.get(sp_weight_unit_vehicle.getSelectedItemPosition()).getid();

                if (user.contentEquals("Enter Staff Name")) {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                } else if (cdate.contentEquals("Enter Date")) {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                } else if (svehicletype.contentEquals("ENter Vehicle Type")) {
                    Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                } else if (ptype.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight Type", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {

                    JSONObject param = null;
                    if (Constant_method.checkConn(context)) {

                        try {
                            param = new JSONObject();
                            param.put("action", "addUpdateVehicleTransp");
                            param.put("id", "");
                            param.put("load_challan_image", Load_img);
                            param.put("vehicle_detail_id", svehicletype);
                            param.put("material_id", smaterial);
                            param.put("user_id", user);
                            param.put("weight_unit", ptype);
                            param.put("to_load_date", l_date);
                            param.put("to_empty_date", e_date);
                            param.put("load_compid", Lid);
                            param.put("empty_compid", E_id);
                            param.put("load_challan_no", l_challon_no);
                            param.put("empty_challan_no", e_challon_no);
                            param.put("empty_challan_image", Empty_img);
                            param.put("load_total_weight", l_weight);
                            param.put("empty_total_weight", e_weight);
                            param.put("load_price_per_ton", l_weight_type);
                            param.put("empty_price_per_ton", e_weight_type);
                            param.put("load_total_price_per_ton", l_amount);
                            param.put("empty_total_price_per_ton", e_amount);
                            param.put("cdate", cdate);
                            param.toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String params = param.toString();
                        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddVehicleTransportActivity.this, params, Config.APi_Get_vehicle_Transport_Add);
                        myAsyncTask.execute();
                    }
                }

            }
        });

    }

    private void getUserName() {
        object_user = new ArrayList<>();
        object_user.add(new SpinnerModel("", getResources().getString(R.string.Select_Name)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Staff, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("user_id"));
//                String name = staff.getString("name");
//                String type = staff.getString("work_type");
//                String val = name + "(" + " " + ")" + type;
                spinnerModel.setName(staff.getString("name"));
                object_user.add(spinnerModel);
            }
            sp_user.setAdapter(new SpinnerAdapter(this, object_user));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVehicleNo() {
        objects_vehicle_no = new ArrayList<>();
        objects_vehicle_no.add(new SpinnerModel("", getResources().getString(R.string.SElect_Vehicle_No)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Vehicle, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_detail_id"));
                spinnerModel.setName(staff.getString("vehicle_no"));
                objects_vehicle_no.add(spinnerModel);
            }
            sp_vehicle_no.setAdapter(new SpinnerAdapter(this, objects_vehicle_no));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVehicleType() {
        objects_vehicle_type = new ArrayList<>();
        objects_vehicle_type.add(new SpinnerModel("", getResources().getString(R.string.Select_VEhicle_Type)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.VehicleType, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("vehicle_type_id"));
                spinnerModel.setName(staff.getString("vehicle_type"));
                objects_vehicle_type.add(spinnerModel);
            }
            sp_vehicle_type.setAdapter(new SpinnerAdapter(this, objects_vehicle_type));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMaterialName() {

        object_material = new ArrayList<>();
        object_material.add(new SpinnerModel("", getResources().getString(R.string.Select_Material_name)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Material, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();

                spinnerModel.setid(staff.getString("material_id"));
                spinnerModel.setName(staff.getString("material_type"));
                object_material.add(spinnerModel);
            }
            sp_material.setAdapter(new SpinnerAdapter(this, object_material));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void getPaymentType() {
        object_payment_type = new ArrayList<SpinnerModel>();
        object_payment_type.add(new SpinnerModel("", "Select Weight Type"));
        object_payment_type.add(new SpinnerModel("Open", "Open"));
        object_payment_type.add(new SpinnerModel("Bags", "Bags"));
        sp_weight_unit_vehicle.setAdapter(new SpinnerAdapter(this, object_payment_type));

    }


    private void setSpinnerVType(Spinner spin_vehicle_type, ArrayList<SpinnerModel> objects_vehicle, String vehicle_type) {
        for (int i = 0; i < objects_vehicle.size(); i++) {
            if (vehicle_type.contentEquals(objects_vehicle.get(i).getName().trim())) {
                spin_vehicle_type.setSelection(i);
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 101) {
            l_date = data.getStringExtra("date");
            txt_load_date.setText(l_date);
            l_challon_no = data.getStringExtra("challon_no");
            txt_load_challon_no.setText(l_challon_no);

            l_com_name = data.getStringExtra("comname");
            txt_load_com_name.setText(l_com_name);
            l_weight_type = data.getStringExtra("weight_type");
            txt_load_weight_type.setText(l_weight_type);
            l_weight = data.getStringExtra("weight");
            txt_load_weight.setText(l_weight);
            l_amount = data.getStringExtra("amount");
            txt_load_amount.setText(l_amount);
            l_detail = data.getStringExtra("detail");
            txt_load_detail.setText(l_detail);

            l_img = data.getStringExtra("img");

            Lid = data.getStringExtra("com_id");

            Load_img = encodedImage = encodeImage(BitmapFactory.decodeFile(l_img));


        } else if (resultCode == 102) {

            e_date = data.getStringExtra("date");
            txt_empty_date.setText(e_date);
            e_challon_no = data.getStringExtra("challon_no");
            txt_empty_challon_no.setText(e_challon_no);
            e_com_name = data.getStringExtra("comname");
            txt_empty_com_name.setText(e_com_name);
            e_weight_type = data.getStringExtra("weight_type");
            txt_empty_weight_type.setText(e_weight_type);
            e_weight = data.getStringExtra("weight");
            txt_empty_weight.setText(e_weight);
            e_amount = data.getStringExtra("amount");
            txt_empty_amount.setText(e_amount);
            e_detail = data.getStringExtra("detail");
            txt_empty_detail.setText(e_detail);

            e_img = data.getStringExtra("img");
            Empty_img = encodedImage = encodeImage(BitmapFactory.decodeFile(e_img));
            E_id = data.getStringExtra("com_id");

        }
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_Get_vehicle_Transport_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    sp_user.setSelection(0);
                    sp_vehicle_no.setSelection(0);
                    sp_material.setSelection(0);
                    txt_start_date.setText("");
                    sp_weight_unit_vehicle.setSelection(0);


//                    txt_load_amount.setText("");
//                    txt_load_challon_no.setText("");
//                    txt_load_com_name.setText("");
//                    txt_load_date.setText("");
//                    txt_load_detail.setText("");
//                    txt_load_weight.setText("");
//                    txt_load_weight_type.setText("");
//
//                    txt_empty_amount.setText("");
//                    txt_empty_challon_no.setText("");
//                    txt_empty_com_name.setText("");
//                    txt_empty_date.setText("");
//                    txt_empty_detail.setText("");
//                    txt_empty_weight.setText("");
//                    txt_empty_weight_type.setText("");
                    Intent intent = new Intent(this, AddVehicleTransportActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        encImage = encImage.replaceAll("\n", "");

        return encImage;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

package com.multipz.kc.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.CompanyImportModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.CallConfigDataApi;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.Constant_method;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.MyAsyncTask;
import com.multipz.kc.util.Shared;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddCompanyImportActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface {
    Button add_company_mst, update_company_mst;
    Shared shared;
    Context context;
    LinearLayout lnr_bank;
    View v2;
    String encodedImage = "", id, c_img, weight_bags, weight_unit;
    int upload_image = 0;
    ImageView challan_img;
    ArrayList<SpinnerModel> objects_company, objects_material, object_project;
    Spinner sp_payment_type, sp_bank_id;
    Spinner sp_company_id, sp_project_id, sp_material_id, sp_weight_unit;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;
    EditText edit_challon_no, edit_amount, edit_detail, edit_weight_type, edit_price_per_ton, edit_total_price_per_ton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company_import);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Company_Import));
        context = this;
        shared = new Shared(context);

        new CallConfigDataApi(shared);
        reference();// reference Initialize
        init();


    }

    private void init() {
        getCompanyName();
        getMaterailName();
        getProjectName();
        addAttendaceBaseType();

        if (getIntent().getBooleanExtra("Update", false)) {
            // setSpinner(sp_company_id, objects_company, getIntent().getStringExtra("companyName"));
            setSpinner(sp_company_id, objects_company, getIntent().getStringExtra("companyName"));
            setSpinner(sp_project_id, object_project, getIntent().getStringExtra("side_sort_name"));
            setSpinner(sp_material_id, objects_material, getIntent().getStringExtra("material_type"));
            edit_challon_no.setText(getIntent().getStringExtra("challan_no"));
            c_img = getIntent().getExtras().getString("challan_image");
            Picasso.with(getApplicationContext()).load(c_img).into(challan_img);
            try {
                Bitmap img = getBitmapFromURL(c_img);
                encodedImage = encodeImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
            edit_amount.setText(getIntent().getExtras().getString("amount"));
            edit_detail.setText(getIntent().getExtras().getString("detail"));
            edit_weight_type.setText(getIntent().getStringExtra("weight_bags"));
            edit_price_per_ton.setText(getIntent().getStringExtra("price_ton"));
            edit_total_price_per_ton.setText(getIntent().getStringExtra("total_price_ton"));
            txt_start_date.setText(getIntent().getStringExtra("date"));

            List<String> list = new ArrayList<String>();
            list.add("Select Weight unit");
            list.add("OPEN");
            list.add("BAGS");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCompanyImportActivity.this, R.layout.spinner_item, R.id.company, list);
            sp_weight_unit.setAdapter(adapter);
            int pos = adapter.getPosition(getIntent().getStringExtra("weight_unit"));
            sp_weight_unit.setSelection(pos);

            id = getIntent().getStringExtra("comp_import_id");
            add_company_mst.setVisibility(View.GONE);
            update_company_mst.setVisibility(View.VISIBLE);
        }

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddCompanyImportActivity.this, txt_start_date);
            }
        });

        add_company_mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String epaymenttype, ecomid, date, sproject, smaterial, scompany, echallono, eamount, edetail, weight_unit, editweight_type, price_per_ton, total_price_per_ton;

//                edetail = edit_detail.getText().toString();
//                eamount = edit_amount.getText().toString();
//                epaymenttype = object.get(sp_payment_type.getSelectedItemPosition()).getid();
//                ecomid = objects_company.get(sp_company_id.getSelectedItemPosition()).getid();
//                ebankid = objects_bank.get(sp_bank_id.getSelectedItemPosition()).getid();

                date = txt_start_date.getText().toString();
                sproject = object_project.get(sp_project_id.getSelectedItemPosition()).getid();
                smaterial = objects_material.get(sp_material_id.getSelectedItemPosition()).getid();
                scompany = objects_company.get(sp_company_id.getSelectedItemPosition()).getid();
                echallono = edit_challon_no.getText().toString();
                eamount = edit_amount.getText().toString();
                edetail = edit_detail.getText().toString();
                weight_unit = sp_weight_unit.getSelectedItem().toString();
                editweight_type = edit_weight_type.getText().toString();
                price_per_ton = edit_price_per_ton.getText().toString();
                total_price_per_ton = edit_total_price_per_ton.getText().toString();


                if (scompany.contentEquals("")) {
                    Toast.makeText(context, "Enter  Company Name", Toast.LENGTH_SHORT).show();
                } else if (sproject.contentEquals("")) {
                    Toast.makeText(context, "Enter Project Name", Toast.LENGTH_SHORT).show();
                } else if (smaterial.contentEquals("")) {
                    Toast.makeText(context, "Enter Material Name", Toast.LENGTH_SHORT).show();
                } else if (echallono.contentEquals("")) {
                    Toast.makeText(context, "Enter Challon no", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter Detail", Toast.LENGTH_SHORT).show();
                } else if (!weight_unit.contentEquals("Select Weight unit") && weight_unit.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight unit", Toast.LENGTH_SHORT).show();
                } else if (editweight_type.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight type", Toast.LENGTH_SHORT).show();
                } else if (price_per_ton.contentEquals("")) {
                    Toast.makeText(context, "Enter price per ton", Toast.LENGTH_SHORT).show();
                } else if (total_price_per_ton.contentEquals("")) {
                    Toast.makeText(context, "Enter total price per ton", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"\",\"company_id\": \"" + scompany + "\",\"project_id\": \"" + sproject + "\",\"material_id\": \"" + smaterial + "\",\"challan_no\": \"" + echallono + "\",\"challan_image\": \"" + encodedImage + "\",\"amount\":\"" + eamount + "\",\"detail\": \"" + edetail + "\",\"weight_bags\": \"" + editweight_type + "\",\"weight_unit\": \"" + weight_unit + "\",\"price_per_ton\": \"" + price_per_ton + "\",\"total_price_per_ton\": \"" + total_price_per_ton + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateCompImport\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyImportActivity.this, param, Config.API_Company_import_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        update_company_mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String epaymenttype, ecomid, date, sproject, smaterial, scompany, echallono, eamount, edetail, weight_unit, editweight_type, price_per_ton, total_price_per_ton;

                sproject = object_project.get(sp_project_id.getSelectedItemPosition()).getid();
                smaterial = objects_material.get(sp_material_id.getSelectedItemPosition()).getid();
                scompany = objects_company.get(sp_company_id.getSelectedItemPosition()).getid();
                echallono = edit_challon_no.getText().toString();
                eamount = edit_amount.getText().toString();
                edetail = edit_detail.getText().toString();
                weight_unit = sp_weight_unit.getSelectedItem().toString();
                editweight_type = edit_weight_type.getText().toString();
                price_per_ton = edit_price_per_ton.getText().toString();
                total_price_per_ton = edit_total_price_per_ton.getText().toString();
                date = txt_start_date.getText().toString();

                if (scompany.contentEquals("")) {
                    Toast.makeText(context, "Enter Company Name", Toast.LENGTH_SHORT).show();
                } else if (sproject.contentEquals("")) {
                    Toast.makeText(context, "Enter Project Name", Toast.LENGTH_SHORT).show();
                } else if (smaterial.contentEquals("")) {
                    Toast.makeText(context, "Enter Material Name", Toast.LENGTH_SHORT).show();
                } else if (echallono.contentEquals("")) {
                    Toast.makeText(context, "Enter Challon no", Toast.LENGTH_SHORT).show();
                } else if (eamount.contentEquals("")) {
                    Toast.makeText(context, "Enter Amount", Toast.LENGTH_SHORT).show();
                } else if (edetail.contentEquals("")) {
                    Toast.makeText(context, "Enter Detail", Toast.LENGTH_SHORT).show();
                } else if (!weight_unit.contentEquals("Select Weight unit") && weight_unit.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight unit", Toast.LENGTH_SHORT).show();
                } else if (editweight_type.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight type", Toast.LENGTH_SHORT).show();
                } else if (price_per_ton.contentEquals("")) {
                    Toast.makeText(context, "Enter price per ton", Toast.LENGTH_SHORT).show();
                } else if (total_price_per_ton.contentEquals("")) {
                    Toast.makeText(context, "Enter total price per ton", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (Constant_method.checkConn(context)) {
                    String param = "{\"id\": \"" + id + "\",\"company_id\": \"" + scompany + "\",\"project_id\": \"" + sproject + "\",\"material_id\": \"" + smaterial + "\",\"challan_no\": \"" + echallono + "\",\"challan_image\": \"" + encodedImage + "\",\"amount\":\"" + eamount + "\",\"detail\": \"" + edetail + "\",\"weight_bags\": \"" + editweight_type + "\",\"weight_unit\": \"" + weight_unit + "\",\"price_per_ton\": \"" + price_per_ton + "\",\"total_price_per_ton\": \"" + total_price_per_ton + "\",\"cdate\": \"" + date + "\",\"action\": \"addUpdateCompImport\"}";
                    MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddCompanyImportActivity.this, param, Config.API_Company_import_Add);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        challan_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (checkPermission()) {
//                    upload_image = 1;
//                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(i, RESULT_LOAD_IMAGE);
//                } else {
//                    ActivityCompat.requestPermissions(Add_Vehicle_Transport_Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
//                }


                selectImage();
                upload_image = 1;
            }


        });
    }

    private void addAttendaceBaseType() {
        try {
            List<String> list = new ArrayList<String>();
            list.add(getResources().getString(R.string.Select_Weight_unit));
            list.add("OPEN");
            list.add("BAGS");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddCompanyImportActivity.this, R.layout.spinner_item, R.id.company, list);
            sp_weight_unit.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void reference() {
        sp_company_id = (Spinner) findViewById(R.id.sp_company_id);
        sp_material_id = (Spinner) findViewById(R.id.sp_material_id);
        sp_project_id = (Spinner) findViewById(R.id.sp_project_id);
        challan_img = (ImageView) findViewById(R.id.challan_img);
        sp_weight_unit = (Spinner) findViewById(R.id.sp_weight_unit);

        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        edit_detail = (EditText) findViewById(R.id.edit_detail);
        edit_amount = (EditText) findViewById(R.id.edit_amount);
        edit_challon_no = (EditText) findViewById(R.id.edit_challon_no);
        sp_bank_id = (Spinner) findViewById(R.id.sp_bank_id);
        sp_payment_type = (Spinner) findViewById(R.id.payment_type);
        v2 = (View) findViewById(R.id.v2);
        lnr_bank = (LinearLayout) findViewById(R.id.lnr_bank);
        add_company_mst = (Button) findViewById(R.id.add_company_master);
        update_company_mst = (Button) findViewById(R.id.update_company_master);
        edit_weight_type = (EditText) findViewById(R.id.edit_weight_type);
        edit_price_per_ton = (EditText) findViewById(R.id.edit_price_per_ton);
        edit_total_price_per_ton = (EditText) findViewById(R.id.edit_total_price_per_ton);


    }

    private void getCompanyName() {
        objects_company = new ArrayList<>();
        objects_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {
            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                objects_company.add(spinnerModel);
            }
            sp_company_id.setAdapter(new SpinnerAdapter(this, objects_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getProjectName() {
        object_project = new ArrayList<>();
        object_project.add(new SpinnerModel("", getResources().getString(R.string.Select_project)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Project, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("project_id"));
                spinnerModel.setName(staff.getString("side_sort_name"));
                object_project.add(spinnerModel);
            }
            sp_project_id.setAdapter(new SpinnerAdapter(this, object_project));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getMaterailName() {
        objects_material = new ArrayList<>();
        objects_material.add(new SpinnerModel("", getResources().getString(R.string.Select_Material_name)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Material, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("material_id"));
                spinnerModel.setName(staff.getString("material_type"));
                objects_material.add(spinnerModel);
            }
            sp_material_id.setAdapter(new SpinnerAdapter(this, objects_material));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.API_Company_import_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");
                if (success == 1) {
                    sp_company_id.setSelection(0);
                    sp_project_id.setSelection(0);
                    sp_material_id.setSelection(0);
                    sp_weight_unit.setSelection(0);
                    edit_price_per_ton.setText("");
                    edit_total_price_per_ton.setText("");
                    edit_amount.setText("");
                    edit_detail.setText("");
                    edit_challon_no.setText("");
                    edit_weight_type.setText("");
                    challan_img.setImageResource(R.drawable.img);
                    txt_start_date.setText("");

                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared);
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.API_Company_import_update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    ArrayList<CompanyImportModel> userlist = new ArrayList<>();
                    CompanyImportModel companyimport = new CompanyImportModel();
                    companyimport.setComp_import_id(jsonObject1.getString("comp_import_id"));
                    companyimport.setCompany_id(jsonObject1.getString("company_id"));
                    companyimport.setProject_id(jsonObject1.getString("project_id"));
                    companyimport.setCompany_id(jsonObject1.getString("company_id"));
                    companyimport.setMaterial_id(jsonObject1.getString("material_id"));
                    companyimport.setChallan_no(jsonObject1.getString("challan_no"));
                    companyimport.setChallan_image(jsonObject1.getString("challan_image"));
                    companyimport.setAmount(jsonObject1.getString("amount"));
                    companyimport.setUser_id(jsonObject1.getString("user_id"));
                    companyimport.setDetail(jsonObject1.getString("detail"));
                    companyimport.setCompanyName(jsonObject1.getString("companyName"));
                    companyimport.setSide_sort_name(jsonObject1.getString("side_sort_name"));
                    companyimport.setMaterial_type(jsonObject1.getString("material_type"));
                    userlist.add(companyimport);
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared);
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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


    private void setSpinner(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddCompanyImportActivity.this);
        builder.setTitle("Add Profile Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    if (checkPermission()) {
                        if (checkPermission_camera()) {

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                            startActivityForResult(intent, 1);
                        } else {
                            ActivityCompat.requestPermissions(AddCompanyImportActivity.this, new String[]{Manifest.permission.CAMERA}, 145);
                        }
                    } else {
                        ActivityCompat.requestPermissions(AddCompanyImportActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    if (checkPermission()) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else {
                        ActivityCompat.requestPermissions(AddCompanyImportActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AddCompanyImportActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermission_camera() {
        int result = ContextCompat.checkSelfPermission(AddCompanyImportActivity.this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 144:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //new NewWordAdd().execute();
                    //permission granted successfully

                } else {

                    //permission denied

                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    challan_img.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix";

                    if (!new File(path).exists()) {
                        new File(path).mkdir();
                    }

                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    encodedImage = encodeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    challan_img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);

                encodedImage = encodeImage(BitmapFactory.decodeFile(picturePath));
                challan_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");
                challan_img.setImageBitmap(thumbnail);
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

}

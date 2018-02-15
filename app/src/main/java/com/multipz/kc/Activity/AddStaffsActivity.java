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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.Model.StaffModel;
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
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class AddStaffsActivity extends AppCompatActivity implements MyAsyncTask.AsyncInterface, AdapterView.OnItemSelectedListener {

    Button btn_add, btn_update;
    Shared shared;
    Context context;
    private ArrayList<SpinnerModel> object_salary_type, objects_work_type;
    private ArrayList<StaffModel> userList;
    LinearLayout lnr_detail;
    String encodedImage = "", proofimg = "", id, c_img, p_img;
    int upload_image = 0;
    ImageView profile_img, proof_img;
    Spinner sp_worktype, sp_salary_type;
    EditText et_name, et_mobile_no, et_password, et_sallary;
    RelativeLayout rl_start_dates;
    TextView txt_start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staffs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getResources().getString(R.string.Staff));
        context = this;
        shared = new Shared(context);

        new CallConfigDataApi(shared).execute();

        rl_start_dates = (RelativeLayout) findViewById(R.id.rl_start_dates);
        txt_start_date = (TextView) findViewById(R.id.txt_start_date);
        lnr_detail = (LinearLayout) findViewById(R.id.lnr_sallry);
        sp_worktype = (Spinner) findViewById(R.id.sp_worktype);
        sp_salary_type = (Spinner) findViewById(R.id.sp_salary_type);

        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_sallary = (EditText) findViewById(R.id.et_sallary);
        et_mobile_no = (EditText) findViewById(R.id.et_mobile_no);

        profile_img = (ImageView) findViewById(R.id.profile_img);
        proof_img = (ImageView) findViewById(R.id.proof_img);

        sp_salary_type.setOnItemSelectedListener(this);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_update = (Button) findViewById(R.id.btn_update);


        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_image = 1;
                selectImage();

            }
        });

        proof_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload_image = 2;
                selectImage();

            }
        });

        txt_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(AddStaffsActivity.this, txt_start_date);
            }
        });

        objects_work_type = new ArrayList<>();
        objects_work_type.add(new SpinnerModel("", getResources().getString(R.string.SElect_Work_type)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.Work_type, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();
                spinnerModel.setid(staff.getString("work_type_id"));
                spinnerModel.setName(staff.getString("work_type"));
                objects_work_type.add(spinnerModel);
            }
            sp_worktype.setAdapter(new SpinnerAdapter(this, objects_work_type));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object_salary_type = new ArrayList<>();
        object_salary_type.add(new SpinnerModel("", "Salary-Type"));
        object_salary_type.add(new SpinnerModel("FIX", "Fix"));
        object_salary_type.add(new SpinnerModel("Daily Base", "Daily Base"));
        sp_salary_type.setAdapter(new SpinnerAdapter(this, object_salary_type));


        if (getIntent().getBooleanExtra("Update", false)) {

            id = getIntent().getStringExtra("user_id");
            et_mobile_no.setText(getIntent().getExtras().getString("mobile"));
            et_sallary.setText(getIntent().getExtras().getString("salary"));
            et_password.setText(getIntent().getExtras().getString("password"));
            et_name.setText(getIntent().getExtras().getString("name"));
            setSpinner(sp_worktype, objects_work_type, getIntent().getStringExtra("work_type"));
            setSpinnerCapital(sp_salary_type, object_salary_type, getIntent().getStringExtra("salary_based_type").trim());
            c_img = getIntent().getExtras().getString("profile_img");
            Picasso.with(getApplicationContext()).load(Config.img_url + "" + c_img).into(profile_img);
            p_img = getIntent().getExtras().getString("proof_img");
            Picasso.with(getApplicationContext()).load(Config.img_url + "" + p_img).into(proof_img);

            txt_start_date.setText(getIntent().getStringExtra("date"));
            btn_add.setVisibility(View.GONE);
            btn_update.setVisibility(View.VISIBLE);
        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ename, epassword, esallry, emobileno, sworktype, salarytype, date;
                ename = et_name.getText().toString();
                epassword = et_password.getText().toString();
                esallry = et_sallary.getText().toString();
                emobileno = et_mobile_no.getText().toString();
                date = txt_start_date.getText().toString();
                salarytype = object_salary_type.get(sp_salary_type.getSelectedItemPosition()).getid();
                sworktype = objects_work_type.get(sp_worktype.getSelectedItemPosition()).getid();
                if (ename.contentEquals("")) {
                    Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (sworktype.contentEquals("")) {
                    Toast.makeText(context, "Enter Work Type", Toast.LENGTH_SHORT).show();
                } else if (emobileno.contentEquals("")) {
                    Toast.makeText(context, "Enter Mobile No", Toast.LENGTH_SHORT).show();
                } else if (epassword.contentEquals("")) {
                    Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (date.contentEquals("")) {
                    Toast.makeText(context, "Enter Date", Toast.LENGTH_SHORT).show();
                } else if (salarytype.contentEquals("Fix")) {
                    if (esallry.contentEquals("")) {
                        Toast.makeText(context, "Please enter salary", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Constant_method.checkConn(context)) {
                            String param = " {\"id\": \"\",\"name\": \"" + ename + "\",\"work_type_id\": \"" + sworktype + "\",\"profile_img\": \"" + encodedImage + "\",\"proof_img\": \"" + proofimg + "\",\"cdate\": \"" + date + "\",\"mobile\": \"" + emobileno + "\",\"password\": \"" + epassword + "\",\"salary_based_type\":\"" + salarytype + "\",\"salary\":\"" + esallry + "\",\"action\": \"addUpdateStaff\"}\n";
                            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddStaffsActivity.this, param, Config.APi_Staff_Add);
                            myAsyncTask.execute();
                        } else {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (Constant_method.checkConn(context)) {
                        String param = " {\"id\": \"\",\"name\": \"" + ename + "\",\"work_type_id\": \"" + sworktype + "\",\"profile_img\": \"" + encodedImage + "\",\"proof_img\": \"" + proofimg + "\",\"cdate\": \"" + date + "\",\"mobile\": \"" + emobileno + "\",\"password\": \"" + epassword + "\",\"salary_based_type\":\"" + salarytype + "\",\"salary\":\"" + esallry + "\",\"action\": \"addUpdateStaff\"}";
                        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddStaffsActivity.this, param, Config.APi_Staff_Add);
                        myAsyncTask.execute();
                    } else {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                String ename, epassword, esallry, emobileno, sworktype, salarytype, date;
                ename = et_name.getText().toString();
                epassword = et_password.getText().toString();
                esallry = et_sallary.getText().toString();
                emobileno = et_mobile_no.getText().toString();
                date = txt_start_date.getText().toString();
                salarytype = object_salary_type.get(sp_salary_type.getSelectedItemPosition()).getid();
                sworktype = objects_work_type.get(sp_worktype.getSelectedItemPosition()).getid();
                if (ename.contentEquals("")) {
                    Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (sworktype.contentEquals("")) {
                    Toast.makeText(context, "Enter Work Type", Toast.LENGTH_SHORT).show();
                } else if (emobileno.contentEquals("")) {
                    Toast.makeText(context, "Enter Mobile No", Toast.LENGTH_SHORT).show();
                } else if (epassword.contentEquals("")) {
                    Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (salarytype.contentEquals("Fix")) {
                    if (esallry.contentEquals("")) {
                        Toast.makeText(context, "Please enter salary", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Constant_method.checkConn(context)) {
                            String param = " {\"id\": \""+id+"\",\"name\": \"" + ename + "\",\"work_type_id\": \"" + sworktype + "\",\"profile_img\": \"" + encodedImage + "\",\"proof_img\": \"" + proofimg + "\",\"mobile\": \"" + emobileno + "\",\"password\": \"" + epassword + "\",\"salary_based_type\":\"" + salarytype + "\",\"salary\":\"" + esallry + "\",\"cdate\":\"" + date + "\",\"action\": \"addUpdateStaff\"}\n";
                            MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddStaffsActivity.this, param, Config.APi_Staff_Add);
                            myAsyncTask.execute();
                        } else {
                            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (Constant_method.checkConn(context)) {
                        String param = " {\"id\": \""+id+"\",\"name\": \"" + ename + "\",\"work_type_id\": \"" + sworktype + "\",\"profile_img\": \"" + encodedImage + "\",\"proof_img\": \"" + proofimg + "\",\"mobile\": \"" + emobileno + "\",\"password\": \"" + epassword + "\",\"salary_based_type\":\"" + salarytype + "\",\"salary\":\"" + esallry + "\",\"cdate\":\"" + date + "\",\"action\": \"addUpdateStaff\"}\n";
                        MyAsyncTask myAsyncTask = new MyAsyncTask(Config.MAIN_API, AddStaffsActivity.this, param, Config.APi_Staff_Add);
                        myAsyncTask.execute();
                    } else {
                        Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponseService(String response, int flag) {
        int success;
        String msg;
        if (flag == Config.APi_Staff_Add) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    et_mobile_no.setText("");
                    et_name.setText("");
                    et_password.setText("");
                    et_sallary.setText("");
                    sp_salary_type.setSelection(0);
                    sp_worktype.setSelection(0);
                    profile_img.setImageResource(R.drawable.img);
                    proof_img.setImageResource(R.drawable.img);
                    txt_start_date.setText("");
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    DrawerActivity.activity.callComponenet();
                    new CallConfigDataApi(shared).execute();
                } else {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag == Config.APi_Staff_Update) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Log.e("Responce", jsonObject.toString());
                success = jsonObject.getInt("status");
                msg = jsonObject.getString("msg");

                if (success == 1) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    userList = new ArrayList<>();
                    StaffModel staff = new StaffModel();
                    staff.setUser_id(jsonObject1.getString("user_id"));
                    staff.setName(jsonObject1.getString("name"));
                    staff.setWork_type_id(jsonObject1.getString("work_type_id"));
                    staff.setMobile(jsonObject1.getString("mobile"));
                    staff.setProfile_img(jsonObject1.getString("profile_img"));
                    staff.setProof_img(jsonObject1.getString("proof_img"));
                    staff.setPassword(jsonObject1.getString("password"));
                    staff.setUser_type(jsonObject1.getString("user_type"));
                    staff.setSalary(jsonObject1.getString("salary"));
                    staff.setSalary_based_type(jsonObject1.getString("salary_based_type"));
                    staff.setWork_type(jsonObject1.getString("work_type"));
                    userList.add(staff);
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

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStaffsActivity.this);
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
                            ActivityCompat.requestPermissions(AddStaffsActivity.this, new String[]{Manifest.permission.CAMERA}, 145);
                        }
                    } else {
                        ActivityCompat.requestPermissions(AddStaffsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    if (checkPermission()) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else {
                        ActivityCompat.requestPermissions(AddStaffsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(AddStaffsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermission_camera() {
        int result = ContextCompat.checkSelfPermission(AddStaffsActivity.this, Manifest.permission.CAMERA);
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
                    if (upload_image == 1) {
                        profile_img.setImageBitmap(bitmap);
                    } else if (upload_image == 2) {
                        proof_img.setImageBitmap(bitmap);
                    }

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (upload_image == 1) {
                        encodedImage = encodeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        profile_img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    } else if (upload_image == 2) {
                        proofimg = encodeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
                        proof_img.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                    }

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
                if (upload_image == 1) {
                    encodedImage = encodeImage(BitmapFactory.decodeFile(picturePath));
                    profile_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                } else if (upload_image == 2) {
                    proofimg = encodeImage(BitmapFactory.decodeFile(picturePath));
                    proof_img.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }

                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gallery......******************.........", picturePath + "");

                if (upload_image == 1) {
                    profile_img.setImageBitmap(thumbnail);
                } else if (upload_image == 2) {
                    proof_img.setImageBitmap(thumbnail);
                }

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

    private void setSpinner(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }


    private void setSpinnerCapital(Spinner asset_id, ArrayList<SpinnerModel> objects_company, String assetsName) {
        for (int i = 0; i < objects_company.size(); i++) {
            if (assetsName.contentEquals(objects_company.get(i).getName())) {
                asset_id.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = object_salary_type.get(sp_salary_type.getSelectedItemPosition()).getName();
        if (item.contentEquals("Salary-Type")) {
            lnr_detail.setVisibility(View.GONE);
        } else if (item.contentEquals("Fix")) {
            lnr_detail.setVisibility(View.VISIBLE);
        } else if (item.contentEquals("Daily Base")) {
            lnr_detail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}


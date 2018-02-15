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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.Adapter.SpinnerAdapter;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.Shared;

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

public class LoadToActivity extends AppCompatActivity {
    TextView txt_load_date, et_load_amount;
    EditText et_load_challon_no, et_weight, et_load_weight_type, et_load_detail;
    String loadtype;
    ImageView challan_img;
    int upload_image = 0;
    private String encodedImage;
    Context context;
    Shared shared;
    Spinner sp_load_compid;
    Button add_vehicle;
    ArrayList<SpinnerModel> object_load_company;
    int result;
    String date, challon_no, weight_type, weight, amount, detail;
    private String loadcpname = "", id = "";
    private String picturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_to);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        shared = new Shared(context);


        txt_load_date = (TextView) findViewById(R.id.txt_load_date);
        add_vehicle = (Button) findViewById(R.id.add_vehicle);
        et_weight = (EditText) findViewById(R.id.et_weight);
        et_load_challon_no = (EditText) findViewById(R.id.et_load_challon_no);
        et_load_weight_type = (EditText) findViewById(R.id.et_load_weight_type);
        et_load_amount = (TextView) findViewById(R.id.et_load_amount);
        et_load_detail = (EditText) findViewById(R.id.et_load_detail);
        challan_img = (ImageView) findViewById(R.id.challan_img);
        sp_load_compid = (Spinner) findViewById(R.id.sp_load_compid);

        getLoadCompanyName();
        loadtype = getIntent().getStringExtra("name");

        if (loadtype.contentEquals("Load")) {
            txt_load_date.setText(getIntent().getStringExtra("load_date"));
            et_load_challon_no.setHint(getIntent().getStringExtra("load_challon_no"));
            et_weight.setHint(getIntent().getStringExtra("load_weight"));
            et_load_weight_type.setHint(getIntent().getStringExtra("load_weight_type"));
            et_load_amount.setText(getIntent().getStringExtra("load_amount"));
            et_load_detail.setHint(getIntent().getStringExtra("load_detail"));
        } else if (loadtype.contentEquals("Empty"))

        {
            txt_load_date.setText(getIntent().getStringExtra("empty_date"));
            et_load_challon_no.setHint(getIntent().getStringExtra("empty_challon_no"));
            et_weight.setHint(getIntent().getStringExtra("empty_weight"));
            et_load_weight_type.setHint(getIntent().getStringExtra("empty_weight_type"));
            et_load_amount.setText(getIntent().getStringExtra("empty_amount"));
            et_load_detail.setHint(getIntent().getStringExtra("empty_detail"));
        }

        challan_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                upload_image = 1;
            }
        });


        et_load_weight_type.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int a = Integer.parseInt(et_weight.getText().toString());
                int b = Integer.parseInt(et_load_weight_type.getText().toString());
                result = a * b;
                et_load_amount.setText(result + "");

            }
        });


        sp_load_compid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadcpname = object_load_company.get(i).getName();
                id = object_load_company.get(i).getid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        txt_load_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerForTextSet(LoadToActivity.this, txt_load_date);
            }
        });
        add_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date = txt_load_date.getText().toString();
                challon_no = et_load_challon_no.getText().toString();
                weight_type = et_load_weight_type.getText().toString();
                weight = et_weight.getText().toString();

                amount = et_load_amount.getText().toString();
                detail = et_load_detail.getText().toString();

                if (challon_no.contentEquals("")) {
                    Toast.makeText(context, "Enter Challon no", Toast.LENGTH_SHORT).show();

                } else if (picturePath.contentEquals("")) {
                    Toast.makeText(context, "Enter Select Image", Toast.LENGTH_SHORT).show();
                }  else if (weight_type.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight Name", Toast.LENGTH_SHORT).show();

                } else if (weight.contentEquals("")) {
                    Toast.makeText(context, "Enter Weight Name", Toast.LENGTH_SHORT).show();

                }


                else if (loadtype.contentEquals("Load")) {


                    {
                        Intent intent = new Intent();
                        intent.putExtra("date", date);
                        intent.putExtra("weight_type", weight_type);
                        intent.putExtra("weight", weight);
                        intent.putExtra("challon_no", challon_no);
                        intent.putExtra("load_img", encodedImage);
                        intent.putExtra("comname", loadcpname);
                        intent.putExtra("com_id", id);
                        intent.putExtra("totoal_amount", result);
                        intent.putExtra("amount", amount);
                        intent.putExtra("detail", detail);
                        intent.putExtra("img", picturePath);
                        setResult(101, intent);
                        finish();
                    }


                } else if (loadtype.contentEquals("Empty")) {
                    Intent intent = new Intent();
                    intent.putExtra("date", date);
                    intent.putExtra("challon_no", challon_no);
                    intent.putExtra("weight_type", weight_type);
                    intent.putExtra("weight", weight);
                    intent.putExtra("comname", loadcpname);
                    intent.putExtra("com_id", id);
                    intent.putExtra("totoal_amount", result);
                    intent.putExtra("empty_img", encodedImage);
                    intent.putExtra("amount", amount);
                    intent.putExtra("detail", detail);
                    intent.putExtra("img", picturePath);
                    setResult(102, intent);
                    finish();
                }


            }
        });


    }

    private void getLoadCompanyName() {
        object_load_company = new ArrayList<>();
        object_load_company.add(new SpinnerModel("", getResources().getString(R.string.Select_com_name)));
        try {

            JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject staff = jsonArray.getJSONObject(i);
                SpinnerModel spinnerModel = new SpinnerModel();

                spinnerModel.setid(staff.getString("company_id"));
                spinnerModel.setName(staff.getString("name"));
                object_load_company.add(spinnerModel);
            }
            sp_load_compid.setAdapter(new SpinnerAdapter(this, object_load_company));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(LoadToActivity.this);
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
                            ActivityCompat.requestPermissions(LoadToActivity.this, new String[]{Manifest.permission.CAMERA}, 145);
                        }
                    } else {
                        ActivityCompat.requestPermissions(LoadToActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    if (checkPermission()) {

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    } else {
                        ActivityCompat.requestPermissions(LoadToActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 144);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(LoadToActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPermission_camera() {
        int result = ContextCompat.checkSelfPermission(LoadToActivity.this, Manifest.permission.CAMERA);
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
                    picturePath = file.getAbsolutePath();

                    //   encodedImage = encodeImage(BitmapFactory.decodeFile(file.getAbsolutePath()));
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
                picturePath = c.getString(columnIndex);


                //    encodedImage = encodeImage(BitmapFactory.decodeFile(picturePath));
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

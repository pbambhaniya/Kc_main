package com.multipz.kc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.multipz.kc.ManageStaffAttendance.StaffAttendanceActivity;
import com.multipz.kc.Model.GetStaffAsPerTypeModel;
import com.multipz.kc.Model.SpinnerModel;
import com.multipz.kc.R;
import com.multipz.kc.util.Application;
import com.multipz.kc.util.Config;
import com.multipz.kc.util.DatePickerForTextSet;
import com.multipz.kc.util.ItemClickListener;
import com.multipz.kc.util.Shared;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StaffAttendanceBaseTypeAdapter extends RecyclerView.Adapter<StaffAttendanceBaseTypeAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<GetStaffAsPerTypeModel> listData;
    private ItemClickListener clickListener;
    Shared shared;
    int pos;
    LayoutInflater inflater;
    ArrayList<SpinnerModel> objects_company;
    String loadcpname="",id="";

    public StaffAttendanceBaseTypeAdapter(Context c, ArrayList<GetStaffAsPerTypeModel> timeSlots) {
        mContext = c;
        this.listData = timeSlots;
        shared = new Shared(mContext);

        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attendance_base_type, parent, false);
        return new MyViewHolder(itemView);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GetStaffAsPerTypeModel model = listData.get(position);
        holder.staffEmployeeName.setText(model.getName());
        holder.toggle_attendance.setTag(position);

        holder.staffEmployeeName.setTypeface(Application.fontOxygenRegular);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SwitchCompat.OnCheckedChangeListener {
        TextView staffEmployeeName;
        SwitchCompat toggle_attendance;
        EditText edt_fix_amount;
        LinearLayout layoutforamount;

        public MyViewHolder(View view) {
            super(view);
            staffEmployeeName = (TextView) view.findViewById(R.id.staffEmployeeName);
            toggle_attendance = (SwitchCompat) view.findViewById(R.id.toggle_attendance);
            edt_fix_amount = (EditText) view.findViewById(R.id.edt_fix_amount);
            layoutforamount = (LinearLayout) view.findViewById(R.id.layoutforamount);
            itemView.setOnClickListener(this);
            toggle_attendance.setOnCheckedChangeListener(this);


        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            pos = (Integer) toggle_attendance.getTag();
            if (isChecked) {
                if (shared.getSalaryBaseType().matches("F")) {

                    showFixPopup(toggle_attendance);


                } else if (shared.getSalaryBaseType().matches("D")) {
                    showPopup(toggle_attendance);
                }
            } else {
                layoutforamount.setVisibility(View.GONE);
            }

        }

        public void showPopup(View view) {

            pos = (Integer) toggle_attendance.getTag();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.popup_dailybase_type, null);


            final EditText edt_fix_amount_popup = (EditText) layout.findViewById(R.id.edt_fix_amount_popup);
            final TextView txt_start_date = (TextView) layout.findViewById(R.id.txt_start_date);
            final Spinner sp_company_id = (Spinner) layout.findViewById(R.id.sp_company_id);
            Button btn_submit_attendance_popup = (Button) layout.findViewById(R.id.btn_submit_attendance_popup);
            Button btn_cancel_attendance_popup = (Button) layout.findViewById(R.id.btn_cancel_attendance_popup);

            getCompanyName(sp_company_id);

            btn_submit_attendance_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String date,com_id;
                    if (edt_fix_amount_popup.getText().toString().contentEquals("")) {
                        Toast.makeText(mContext, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                    } else {
                        String amt = edt_fix_amount_popup.getText().toString().trim();
                        date=txt_start_date.getText().toString();
                        com_id = objects_company.get(sp_company_id.getSelectedItemPosition()).getid();
                        getStaffAttendance(pos, amt, date,com_id);
                    }
                }
            });
            txt_start_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerForTextSet(mContext, txt_start_date);
                }
            });

            AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
            alertbox.setView(layout);
            final AlertDialog dialog = alertbox.create();

            btn_cancel_attendance_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    toggle_attendance.setChecked(false);
                }
            });
            sp_company_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    loadcpname = objects_company.get(i).getName();
                    id = objects_company.get(i).getid();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            dialog.show();
        }

        private void getCompanyName(Spinner sp_company_id) {
            objects_company = new ArrayList<>();
            objects_company.add(new SpinnerModel("", mContext.getResources().getString(R.string.Select_com_name)));
            try {
                JSONArray jsonArray = new JSONArray(shared.getString(Config.company, "[{}]"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject staff = jsonArray.getJSONObject(i);
                    SpinnerModel spinnerModel = new SpinnerModel();
                    spinnerModel.setid(staff.getString("company_id"));
                    spinnerModel.setName(staff.getString("name"));
                    objects_company.add(spinnerModel);
                }
                sp_company_id.setAdapter(new SpinnerStaffSalaryAdapter(mContext, objects_company));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        private void showFixPopup(View view) {

            pos = (Integer) toggle_attendance.getTag();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_fix_type, null);

            final TextView txt_start_date = (TextView) layout.findViewById(R.id.txt_start_date);
            Button btn_submit_attendance_popup = (Button) layout.findViewById(R.id.btn_submit_attendance_popup);
            Button btn_cancel_attendance_popup = (Button) layout.findViewById(R.id.btn_cancel_attendance_popup);

            txt_start_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new DatePickerForTextSet(mContext, txt_start_date);
                }
            });

            btn_submit_attendance_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txt_start_date.getText().toString().contentEquals("")) {
                        Toast.makeText(mContext, "Please Enter Date", Toast.LENGTH_SHORT).show();
                    } else {
                        String date = txt_start_date.getText().toString().trim();
                        getStaffAttendance(pos, "0", date,"");
                    }
                }
            });
            AlertDialog.Builder alertbox = new AlertDialog.Builder(view.getRootView().getContext());
            alertbox.setView(layout);

            final AlertDialog dialog = alertbox.create();

            btn_cancel_attendance_popup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    toggle_attendance.setChecked(false);
                }
            });
            dialog.show();


        }
    }


    private void getStaffAttendance(int position, String amount, String date,String id) {
        GetStaffAsPerTypeModel model = listData.get(position);
        if (shared.getSalaryBaseType().matches("F")) {
            String userId = model.getUser_id();
            Intent i = new Intent(mContext, StaffAttendanceActivity.class);
            i.putExtra("staffData", "staffData");
            i.putExtra("userId", userId);
            i.putExtra("amount", "0");
            i.putExtra("date", date);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);

        } else if (shared.getSalaryBaseType().matches("D")) {
            String userId = model.getUser_id();
            Intent i = new Intent(mContext, StaffAttendanceActivity.class);
            i.putExtra("staffData", "staffData");
            i.putExtra("userId", userId);
            i.putExtra("amount", amount);
            i.putExtra("date",date);
            i.putExtra("cid",id);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
        }
    }


}

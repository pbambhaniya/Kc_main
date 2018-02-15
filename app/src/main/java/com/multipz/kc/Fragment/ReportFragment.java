package com.multipz.kc.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.multipz.kc.Model.GridMenu;
import com.multipz.kc.R;
import com.multipz.kc.Report.KcGenralExpActivity;
import com.multipz.kc.Report.PartywisepaymentActivity;
import com.multipz.kc.Report.ProjectReturnReport;
import com.multipz.kc.Report.SalaryStatementActivity;
import com.multipz.kc.Report.ShowAttendanceReportActivity;
import com.multipz.kc.Report.SiteExpenseReport;
import com.multipz.kc.Report.SiteGenralExpReportActivity;
import com.multipz.kc.Report.SiteWisePaymentActivity;
import com.multipz.kc.Report.TransportCompanyModule;
import com.multipz.kc.Report.VehicleHourlyReport;
import com.multipz.kc.Report.VehicleTransportReports;
import com.multipz.kc.Report.VehicleExReport;
import com.multipz.kc.Report.VehicleTransportReport;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {

    Context context;
    ArrayList<GridMenu> data;
    GridView gridMenu;
    com.multipz.kc.Adapter.GridMenu adapter;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        getActivity().setTitle(getResources().getString(R.string.Report));
        context = getActivity();
        data = new ArrayList<>();

        gridMenu = view.findViewById(R.id.gridmenu);
        setData();

        adapter = new com.multipz.kc.Adapter.GridMenu(context, data);
        gridMenu.setAdapter(adapter);

        gridMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
//                    case 0:
//                        startActivity(new Intent(context, SiteExpenseReport.class));
//                        break;
                    case 0:
                        startActivity(new Intent(context, ShowAttendanceReportActivity.class));
                        break;

                    case 1:
                        startActivity(new Intent(context, SalaryStatementActivity.class));
                        break;

                    case 2:
                        startActivity(new Intent(context, KcGenralExpActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(context, SiteGenralExpReportActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(context, VehicleExReport.class));
                        break;
                    case 5:
                        startActivity(new Intent(context, VehicleTransportReports.class));
                        break;
//                    case 3:
//                        startActivity(new Intent(context, VehicleTransportReport.class));
//                        break;

                    case 6:
                        startActivity(new Intent(context, PartywisepaymentActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(context, SiteWisePaymentActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(context, VehicleHourlyReport.class));
                        break;
                    case 9:
                        startActivity(new Intent(context, ProjectReturnReport.class));
                        break;
                    case 10:
                        startActivity(new Intent(context, TransportCompanyModule.class));
                        break;
                    default:
                        break;
                }
            }
        });
        return view;

    }

    private void setData() {
//        data.add(new GridMenu("Site Exp.Report", R.drawable.bank));
        data.add(new GridMenu(getResources().getString(R.string.Attendance_Report), R.drawable.attendence_report_guj));
        data.add(new GridMenu(getResources().getString(R.string.Salary_Statement), R.drawable.salary_report_guj));
        data.add(new GridMenu(getResources().getString(R.string.KcGenralExpense), R.drawable.kc_gen_exp_report_guj));
        data.add(new GridMenu(getResources().getString(R.string.SiteGenaralExpense), R.drawable.side_report));
        data.add(new GridMenu(getResources().getString(R.string.Vehicle_Expense), R.drawable.vehicle_expence_report_guj));
        data.add(new GridMenu(getResources().getString(R.string.Vehicle_Transport), R.drawable.transport_report_guj));
        //data.add(new GridMenu("Site", R.drawable.site));
        data.add(new GridMenu(getResources().getString(R.string.PartyWisePayment), R.drawable.party_payment_report_guj));
        data.add(new GridMenu(getResources().getString(R.string.SiteWisePAyment), R.drawable.side_expense_report));
        data.add(new GridMenu(getResources().getString(R.string.Vehicle_Hourly), R.drawable.hourly_vehicle_report_guj));
        data.add(new GridMenu(getResources().getString(R.string.Project_Return), R.drawable.project_return_report));
        data.add(new GridMenu(getResources().getString(R.string.TransportCompany), R.drawable.transport_company_report));
    }

}

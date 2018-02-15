package com.multipz.kc.util;

/**
 * Created by Admin on 29-07-2017.
 */

public class Config {
//    public static final String MAIN_API = "http://kc.multipz.com/api";
//    public static final String img_url = "http://kc.multipz.com/assets/images/";

//    local
    public static final String MAIN_API = "http://192.168.0.71/kc/api";
    public static final String img_url = "http://192.168.0.71/kc/assets/images/";

    public static final int API_TOKEN_ADD_PROJ = 1;
    public static final int API_TOKEN_UPDATE_PROJ = 2;
    public static final int API_TOKEN_DELETE_PROJ = 3;
    public static final int API_TOKEN_GET_PROJ = 4;

    public static final int API_TOKEN_ADD_LOGIN = 5;

    public static final int API_TOKEN_ADD_COMPANY = 6;
    public static final int API_TOKEN_UPDATE_COMPANY = 7;
    public static final int API_TOKEN_DELETE_COMPANY = 8;
    public static final int API_TOKEN_GET_COMPANY = 9;

    public static final int API_TOKEN_CONTAINER = 10;

    public static final int API_TOKEN_ADD_Material = 11;
    public static final int API_TOKEN_UPDATE_Material = 12;
    public static final int API_TOKEN_DELETE_Material = 13;
    public static final int API_TOKEN_GET_Material = 14;

    public static final int API_TOKEN_DASHBOARD = 15;

    public static final int API_TOKEN_ADD_COMPANY_MASTER = 16;
    public static final int API_TOKEN_UPDATE_COMPANY_MASTER = 17;
    public static final int API_TOKEN_GET_COMPANY_MASTER = 18;

    public static final int API_TOKEN_ADD_VEHICLE = 19;
    public static final int API_TOKEN_UPDATE_VEHICLE = 20;
    public static final int API_TOKEN_GET_VEHICLE = 21;

    public static final int API_TOKEN_ADD_BANK = 22;
    public static final int API_TOKEN_UPDATE_BANK = 23;
    public static final int API_TOKEN_DELETE_BANK = 24;
    public static final int API_TOKEN_GET_BANK = 25;

    public static final int API_TOKEN_ADD_VEHICLE_TYPE = 26;
    public static final int API_TOKEN_UPDATE_VEHICLE_TYPE = 27;
    public static final int API_TOKEN_DELETE_VEHICLE_TYPE = 28;
    public static final int API_TOKEN_GET_VEHICLE_TYPE = 29;


    public static final int API_Company_import_Add = 30;
    public static final int API_Company_import_update = 31;
    public static final int API_Get_Company_import = 32;

    public static final int API_TOKEN_ADMIN_DASHBOARD = 33;
    public static final int API_Add_Project_return = 34;
    public static final int API_Update_Project_return = 35;
    public static final int API_Get_Project_return = 36;

    public static final int APi_WorkType_Add = 37;
    public static final int APi_WorkType_Update = 38;
    public static final int APi_Get_Worktype = 39;

    public static final int APi_Get_KC_Expense = 40;
    public static final int APi_KC_Expense_Add = 41;
    public static final int APi_KC_Expense_update = 42;

    public static final int APi_Get_Site_general_Expense = 43;
    public static final int APi_Site_general_Expense_Add = 44;
    public static final int APi_Site_general_Expense_update = 45;

    public static final int APi_Get_vehicle_general_Expense = 46;
    public static final int APi_vehicle_general_Expense_Add = 47;
    public static final int APi_vehicle_general_Expense_Update = 48;

    public static final int APi_Get_Company_Pay = 49;
    public static final int APi_Get_COmpany_pay_Add = 50;
    public static final int APi_Get_COmpany_pay_Update = 51;

    public static final int APi_Get_vehicle_hourly = 52;
    public static final int APi_Get_vehicle_hourly_Add = 53;
    public static final int APi_Get_vehicle_hourly_Update = 54;

    public static final int APi_Get_vehicle_Transport = 55;
    public static final int APi_Get_vehicle_Transport_Add = 56;
    public static final int APi_Get_vehicle_Transport_Update = 57;
    public static final int APi_delete_vehicle_Transport = 1566;

    public static final int APi_Staff_Add = 58;
    public static final int APi_Staff_Update = 59;
    public static final int APi_Get_staff = 60;

    public static final int APi_Amount_Staff_Add = 61;
    public static final int APi_Amount_Staff_Update = 62;
    public static final int APi_Get_AMount_staff = 63;

    /******************** paresh changes *******************/
    public static final int Api_Staff_Deshboard = 69;
    public static final int APi_Get_staff_debit_salary = 70;
    public static final int APi_staff_debit_salary_delete = 99;
    public static final int APi_staff_debit_salary_add = 71;
    public static final int APi_staff_debit_salary_update = 72;
    public static final int APi_Get_vehicle_hourly_pay = 73;
    /********************************************************/
    public static final int APi_Get_site_expense_report = 74;
    public static final int APi_Get_vehicle_expense_report = 75;
    public static final int APi_Get_staff_project_report = 76;
    public static final int APi_Get_vehicle_transport_report = 77;
    public static final int APi_getStaffAsPerType = 78;
    public static final int APi_staffAttendance = 79;
    public static final int APi_getStaffAtten = 80;
    public static final int APi_salaryData = 81;
    public static final int APi_attendanceData = 82;
    public static final int APi_partywishpayment = 83;
    public static final int APi_partyItem = 84;
    public static final int APi_sitewishpayment = 85;
    public static final int APi_kcGeneralExp = 86;
    public static final int APi_siteGeneralExp = 87;
    public static final int APi_siteExpDetail = 88;
    public static final int APi_Get_vehicle_hourly_delete = 89;
    public static final int APi_editDeleteVehicleTransp = 90;
    public static final int APi_vehicle_in_out_add = 91;
    public static final int APi_Get_vehicle_In_Out = 92;
    public static final int APi_vehicle_in_out_delete = 93;
    public static final int APi_vehicle_hourly_pay_Add = 94;
    public static final int APi_vehicle_hourly_pay_Delete = 95;
    public static final int API_Vehicle_item = 96;

    public static final int API_Project_return_report = 97;
    public static final int API_Vehicle_Transports_reports = 98;
    public static final int API_Vehicle_Transports_Report_Item = 99;
    public static final int APi_vehicleHourlyReport = 100;
    public static final int APi_vehicleDetailHourlyReportRemainAmt = 101;
    public static final int APi_vehicleDetailHourlyReportGotAmt = 102;
    public static final int APi_transportCompanyReportGet = 103;
    public static final int APitransportDetails = 104;
    public static final int APi_paymentDetails = 105;
    public static final int Api_GEt_Staff_ex_site_report = 106;
    public static final int APi_StaffVehicleExpReportData = 107;
    public static final int APi_viewSalaryData = 108;
    public static final int APi_getSalaryDatapay = 109;
    public static final int APi_getDataForPaySalary = 110;
    public static final int APi_salaryPaid = 111;
    //salaryPaid


    //Shared Key
    public static final String isLogin = "isLogin";
    public static final String CurrentUser = "currentuser";
    public static final String Staff = "staff";
    public static final String Project = "project";
    public static final String Material = "material";
    public static final String company = "company";
    public static final String Bank = "bank";
    public static final String Wholeseller = "wholeseller";
    public static final String Vehicle = "vehicle";
    public static final String Work_type = "work_type";
    public static final String Company_mst = "Company_mst";
    public static final String OnlyProject = "OnlyProject";
    public static final String getStaffAsPerType = "getStaffAsPerType";
    public static final String VehicleType = "vehicle_type";
    public static final String staffAttendance = "staffAttendance";
    public static final String getStaffAtten = "getStaffAtten";
    public static final String salaryData = "salaryData";
    public static final String attendanceData = "attendanceData";
    public static final String partywishpayment = "partywishpayment";
    public static final String partyItem = "partyItem";
    public static final String sitewishpayment = "sitewishpayment";
    public static final String kcGeneralExp = "kcGeneralExp";
    public static final String siteGeneralExp = "siteGeneralExp";
    public static final String siteExpDetail = "siteExpDetail";
    public static final String editDeleteVehicleTransp = "editDeleteVehicleTransp";
    public static final String vehicleHourlyReport = "vehicleHourlyReport";
    public static final String AllStaff = "allStaff";

}

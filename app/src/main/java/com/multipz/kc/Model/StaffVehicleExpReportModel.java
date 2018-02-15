package com.multipz.kc.Model;

/**
 * Created by Admin on 05-10-2017.
 */

public class StaffVehicleExpReportModel {
    private String vehicle_gen_exp_id, name, vehicle_no, detail, created_date, amt;

    public String getVehicle_gen_exp_id() {
        return vehicle_gen_exp_id;
    }

    public void setVehicle_gen_exp_id(String vehicle_gen_exp_id) {
        this.vehicle_gen_exp_id = vehicle_gen_exp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}

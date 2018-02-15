package com.multipz.kc.Model;

/**
 * Created by Admin on 12-09-2017.
 */

public class VehicleGeneralExpenseModel {
    String vehicle_gen_exp_id;
    String vehicle_detail_id;
    String detail;
    String amount;
    String vehicle_no;
    String user_id;
    String vehicle_type;
    String cdate;

    public String getEtype() {
        return etype;
    }

    public void setEtype(String etype) {
        this.etype = etype;
    }

    String etype;

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }


    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_gen_exp_id() {
        return vehicle_gen_exp_id;
    }

    public void setVehicle_gen_exp_id(String vehicle_gen_exp_id) {
        this.vehicle_gen_exp_id = vehicle_gen_exp_id;
    }

    public String getVehicle_detail_id() {
        return vehicle_detail_id;
    }

    public void setVehicle_detail_id(String vehicle_detail_id) {
        this.vehicle_detail_id = vehicle_detail_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

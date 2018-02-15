package com.multipz.kc.Model;

/**
 * Created by Admin on 04-10-2017.
 */

public class VehicleTransportReportItemModel {

    String vehicle_transport_id;
    String vehicle_detail_id;
    String user_id;
    String material_id;
    String load_to;
    String load_compid;
    String load_amount;
    String EMPTY_TO;
    String empty_compid;
    String empty_amount;
    String Profit;

    public String getProfit() {
        return Profit;
    }

    public void setProfit(String profit) {
        Profit = profit;
    }

    String created_date;
    String modified_date;
    String is_status;
    String is_delete;
    String totalLoadAmount;
    String totalEmptyAmount;
    String name;
    String loadCompanyName;
    String emptyCompanyName;
    String vehicle_no;
    String vehicle_type;
    String work_type;

    public String getVehicle_transport_id() {
        return vehicle_transport_id;
    }

    public void setVehicle_transport_id(String vehicle_transport_id) {
        this.vehicle_transport_id = vehicle_transport_id;
    }

    public String getVehicle_detail_id() {
        return vehicle_detail_id;
    }

    public void setVehicle_detail_id(String vehicle_detail_id) {
        this.vehicle_detail_id = vehicle_detail_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getLoad_to() {
        return load_to;
    }

    public void setLoad_to(String load_to) {
        this.load_to = load_to;
    }

    public String getLoad_compid() {
        return load_compid;
    }

    public void setLoad_compid(String load_compid) {
        this.load_compid = load_compid;
    }

    public String getLoad_amount() {
        return load_amount;
    }

    public void setLoad_amount(String load_amount) {
        this.load_amount = load_amount;
    }

    public String getEMPTY_TO() {
        return EMPTY_TO;
    }

    public void setEMPTY_TO(String EMPTY_TO) {
        this.EMPTY_TO = EMPTY_TO;
    }

    public String getEmpty_compid() {
        return empty_compid;
    }

    public void setEmpty_compid(String empty_compid) {
        this.empty_compid = empty_compid;
    }

    public String getEmpty_amount() {
        return empty_amount;
    }

    public void setEmpty_amount(String empty_amount) {
        this.empty_amount = empty_amount;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getIs_status() {
        return is_status;
    }

    public void setIs_status(String is_status) {
        this.is_status = is_status;
    }

    public String getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    public String getTotalLoadAmount() {
        return totalLoadAmount;
    }

    public void setTotalLoadAmount(String totalLoadAmount) {
        this.totalLoadAmount = totalLoadAmount;
    }

    public String getTotalEmptyAmount() {
        return totalEmptyAmount;
    }

    public void setTotalEmptyAmount(String totalEmptyAmount) {
        this.totalEmptyAmount = totalEmptyAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoadCompanyName() {
        return loadCompanyName;
    }

    public void setLoadCompanyName(String loadCompanyName) {
        this.loadCompanyName = loadCompanyName;
    }

    public String getEmptyCompanyName() {
        return emptyCompanyName;
    }

    public void setEmptyCompanyName(String emptyCompanyName) {
        this.emptyCompanyName = emptyCompanyName;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }
}

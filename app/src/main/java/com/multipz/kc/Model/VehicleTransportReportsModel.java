package com.multipz.kc.Model;

/**
 * Created by Admin on 04-10-2017.
 */

public class VehicleTransportReportsModel {
    public String getVehicle_detail_id() {
        return vehicle_detail_id;
    }

    public void setVehicle_detail_id(String vehicle_detail_id) {
        this.vehicle_detail_id = vehicle_detail_id;
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

    String vehicle_detail_id, vehicle_no, vehicle_type;
}

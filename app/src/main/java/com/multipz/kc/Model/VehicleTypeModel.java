package com.multipz.kc.Model;

/**
 * Created by Admin on 04-08-2017.
 */

public class VehicleTypeModel {
    String vehicle_type_id;
    String vehicle_type;

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    String cdate;

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    String created_date;

    public String getCreated_date() {
        return created_date;
    }

    public String getVehicle_type_id() {
        return vehicle_type_id;
    }

    public void setVehicle_type_id(String vehicle_type_id) {
        this.vehicle_type_id = vehicle_type_id;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }
}

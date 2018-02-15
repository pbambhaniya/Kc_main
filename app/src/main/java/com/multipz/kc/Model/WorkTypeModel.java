package com.multipz.kc.Model;

/**
 * Created by Admin on 08-08-2017.
 */

public class WorkTypeModel {

    String work_type_id, work_type,cdate,created_date,modified_date,is_status,is_delete;

    public String getWork_type_id() {
        return work_type_id;
    }

    public void setWork_type_id(String work_type_id) {
        this.work_type_id = work_type_id;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
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
}

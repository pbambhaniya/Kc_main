package com.multipz.kc.Model;

/**
 * Created by Admin on 19-09-2017.
 */

public class StaffModel {
    String user_id;
    String name;
    String work_type_id;
    String mobile;
    String profile_img;
    String proof_img;
    String password;
    String user_type;
    String salary;
    String cdate;

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    String salary_based_type;
    String created_date;
    String modified_date;
    String is_status;
    String is_delete;
    String work_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork_type_id() {
        return work_type_id;
    }

    public void setWork_type_id(String work_type_id) {
        this.work_type_id = work_type_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getProof_img() {
        return proof_img;
    }

    public void setProof_img(String proof_img) {
        this.proof_img = proof_img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getSalary_based_type() {
        return salary_based_type;
    }

    public void setSalary_based_type(String salary_based_type) {
        this.salary_based_type = salary_based_type;
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

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }
}

package com.multipz.kc.Model;

/**
 * Created by Admin on 05-10-2017.
 */

public class StaffSiteExpenseReportModel {
    String side_gen_exp_id,name,side_sort_name,detail,amt,created_date;

    public String getSide_gen_exp_id() {
        return side_gen_exp_id;
    }

    public void setSide_gen_exp_id(String side_gen_exp_id) {
        this.side_gen_exp_id = side_gen_exp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSide_sort_name() {
        return side_sort_name;
    }

    public void setSide_sort_name(String side_sort_name) {
        this.side_sort_name = side_sort_name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}

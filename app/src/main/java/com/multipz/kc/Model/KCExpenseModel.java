package com.multipz.kc.Model;

/**
 * Created by Admin on 12-09-2017.
 */

public class KCExpenseModel {
    String side_sort_name, detail, amount, side_gen_exp_id, is_status, kc_exp_id, user_id, project_id, cdate;


    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }


    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public String getSide_sort_name() {
        return side_sort_name;
    }

    public void setSide_sort_name(String side_sort_name) {
        this.side_sort_name = side_sort_name;
    }


    public String getSide_gen_exp_id() {
        return side_gen_exp_id;
    }

    public void setSide_gen_exp_id(String side_gen_exp_id) {
        this.side_gen_exp_id = side_gen_exp_id;
    }


    public String getKc_exp_id() {
        return kc_exp_id;
    }

    public void setKc_exp_id(String kc_exp_id) {
        this.kc_exp_id = kc_exp_id;
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

    public String getIs_status() {
        return is_status;
    }

    public void setIs_status(String is_status) {
        this.is_status = is_status;
    }
}

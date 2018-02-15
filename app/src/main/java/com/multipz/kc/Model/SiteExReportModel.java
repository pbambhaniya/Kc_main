package com.multipz.kc.Model;

/**
 * Created by Admin on 21-09-2017.
 */

public class SiteExReportModel {

    String side_gen_exp_id, user_id, project_id, detail, amount, created_date, userName, side_sort_name,name_of_work,totalSum;

    public String getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(String totalSum) {
        this.totalSum = totalSum;
    }

    public String getName_of_work() {
        return name_of_work;
    }

    public void setName_of_work(String name_of_work) {
        this.name_of_work = name_of_work;
    }

    public String getSide_gen_exp_id() {
        return side_gen_exp_id;
    }

    public void setSide_gen_exp_id(String side_gen_exp_id) {
        this.side_gen_exp_id = side_gen_exp_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSide_sort_name() {
        return side_sort_name;
    }

    public void setSide_sort_name(String side_sort_name) {
        this.side_sort_name = side_sort_name;
    }
}

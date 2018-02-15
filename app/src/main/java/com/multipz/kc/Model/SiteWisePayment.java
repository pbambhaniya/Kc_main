package com.multipz.kc.Model;

/**
 * Created by Admin on 28-09-2017.
 */

public class SiteWisePayment {
    private String project_id, side_sort_name, start_date, amount;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getSide_sort_name() {
        return side_sort_name;
    }

    public void setSide_sort_name(String side_sort_name) {
        this.side_sort_name = side_sort_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

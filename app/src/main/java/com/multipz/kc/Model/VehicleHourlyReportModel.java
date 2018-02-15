package com.multipz.kc.Model;

/**
 * Created by Admin on 04-10-2017.
 */

public class VehicleHourlyReportModel {
    private String name, company_id, amount, get_amount, actualRemaining;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getGet_amount() {
        return get_amount;
    }

    public void setGet_amount(String get_amount) {
        this.get_amount = get_amount;
    }

    public String getActualRemaining() {
        return actualRemaining;
    }

    public void setActualRemaining(String actualRemaining) {
        this.actualRemaining = actualRemaining;
    }
}

package com.multipz.kc.Model;

/**
 * Created by Admin on 27-09-2017.
 */

public class SalaryDataModel {
    private String user_id, tbl_month_salary_id, name, day, actual_salary, pay_salary, actual_pay_salary, pay_status, amount;

    public String getTbl_month_salary_id() {
        return tbl_month_salary_id;
    }

    public void setTbl_month_salary_id(String tbl_month_salary_id) {
        this.tbl_month_salary_id = tbl_month_salary_id;
    }

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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getActual_salary() {
        return actual_salary;
    }

    public void setActual_salary(String actual_salary) {
        this.actual_salary = actual_salary;
    }

    public String getPay_salary() {
        return pay_salary;
    }

    public void setPay_salary(String pay_salary) {
        this.pay_salary = pay_salary;
    }

    public String getActual_pay_salary() {
        return actual_pay_salary;
    }

    public void setActual_pay_salary(String actual_pay_salary) {
        this.actual_pay_salary = actual_pay_salary;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

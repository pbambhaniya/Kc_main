package com.multipz.kc.Model;

/**
 * Created by Admin on 09-10-2017.
 */

public class SalaryPayModel {


    private String user_id, name, salary_based_type, salary, creditDebit;

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

    public String getSalary_based_type() {
        return salary_based_type;
    }

    public void setSalary_based_type(String salary_based_type) {
        this.salary_based_type = salary_based_type;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCreditDebit() {
        return creditDebit;
    }

    public void setCreditDebit(String creditDebit) {
        this.creditDebit = creditDebit;
    }
}

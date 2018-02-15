package com.multipz.kc.Model;

/**
 * Created by Admin on 13-09-2017.
 */

public class CompanyPayModel {
    String bank_name, companyName, comp_pay_id, company_id, bank_id, amount, payment_type, payment_info;

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    String cdate;

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getComp_pay_id() {
        return comp_pay_id;
    }

    public void setComp_pay_id(String comp_pay_id) {
        this.comp_pay_id = comp_pay_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getBank_id() {
        return bank_id;
    }

    public void setBank_id(String bank_id) {
        this.bank_id = bank_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_info() {
        return payment_info;
    }

    public void setPayment_info(String payment_info) {
        this.payment_info = payment_info;
    }
}
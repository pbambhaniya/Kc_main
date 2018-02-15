package com.multipz.kc.Model;

/**
 * Created by Admin on 02-10-2017.
 */

public class TransPortInOutModel {
    String cheque_no, payment_type, tbl_master_payment_in_out_id, type, amount, name;

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTbl_master_payment_in_out_id() {
        return tbl_master_payment_in_out_id;
    }

    public void setTbl_master_payment_in_out_id(String tbl_master_payment_in_out_id) {
        this.tbl_master_payment_in_out_id = tbl_master_payment_in_out_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

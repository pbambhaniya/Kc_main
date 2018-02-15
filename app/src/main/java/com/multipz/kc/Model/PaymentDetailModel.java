package com.multipz.kc.Model;

/**
 * Created by Admin on 04-10-2017.
 */

public class PaymentDetailModel {
    private String tbl_master_payment_in_out_id, type, payment_type, amount, cheque_no, create_date;

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

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCheque_no() {
        return cheque_no;
    }

    public void setCheque_no(String cheque_no) {
        this.cheque_no = cheque_no;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }
}

package com.multipz.kc.Model;

/**
 * Created by Admin on 04-10-2017.
 */

public class TransportCompanyModel {
    private String partyName, amt1,tbl_transport_payment_id;

    public String getTbl_transport_payment_id() {
        return tbl_transport_payment_id;
    }

    public void setTbl_transport_payment_id(String tbl_transport_payment_id) {
        this.tbl_transport_payment_id = tbl_transport_payment_id;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getAmt1() {
        return amt1;
    }

    public void setAmt1(String amt1) {
        this.amt1 = amt1;
    }
}

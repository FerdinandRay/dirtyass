package com.fang.model;

/**
 * Created by bull on 16-6-3.
 */
public class Trans {
    private int trans_id;
    private int trans_payass_id;
    private int trans_otherass_id;
    private double trans_money;
    private String trans_reason;
    private String trans_date;

    public Trans() {
    }

    public Trans(int trans_payass_id, int trans_otherass_id, double trans_money, String trans_reason, String trans_date) {
        this.trans_payass_id = trans_payass_id;
        this.trans_otherass_id = trans_otherass_id;
        this.trans_money = trans_money;
        this.trans_reason = trans_reason;
        this.trans_date = trans_date;
    }

    public int getTrans_payass_id() {
        return trans_payass_id;
    }

    public void setTrans_payass_id(int trans_payass_id) {
        this.trans_payass_id = trans_payass_id;
    }

    public int getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(int trans_id) {
        this.trans_id = trans_id;
    }

    public int getTrans_otherass_id() {
        return trans_otherass_id;
    }

    public void setTrans_otherass_id(int trans_otherass_id) {
        this.trans_otherass_id = trans_otherass_id;
    }

    public double getTrans_money() {
        return trans_money;
    }

    public void setTrans_money(double trans_money) {
        this.trans_money = trans_money;
    }

    public String getTrans_reason() {
        return trans_reason;
    }

    public void setTrans_reason(String trans_reason) {
        this.trans_reason = trans_reason;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }
}

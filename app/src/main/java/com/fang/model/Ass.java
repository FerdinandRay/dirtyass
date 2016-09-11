package com.fang.model;

/**
 * Created by bull on 16-6-3.
 */
public class Ass {
    private int ass_id;
    private String ass_name;
    private int ass_trade_count;
    private int ass_trans_count;
    private double ass_total_money;

    public Ass() {
    }

    public Ass(String ass_name) {
        this.ass_name = ass_name;
    }

    public Ass(String ass_name, int ass_trade_count, int ass_trans_count, double ass_total_money) {
        this.ass_name = ass_name;
        this.ass_trade_count = ass_trade_count;
        this.ass_trans_count = ass_trans_count;
        this.ass_total_money = ass_total_money;
    }

    public int getAss_id() {
        return ass_id;
    }

    public void setAss_id(int ass_id) {
        this.ass_id = ass_id;
    }

    public String getAss_name() {
        return ass_name;
    }

    public void setAss_name(String ass_name) {
        this.ass_name = ass_name;
    }

    public int getAss_trade_count() {
        return ass_trade_count;
    }

    public void setAss_trade_count(int ass_trade_count) {
        this.ass_trade_count = ass_trade_count;
    }

    public int getAss_trans_count() {
        return ass_trans_count;
    }

    public void setAss_trans_count(int ass_trans_count) {
        this.ass_trans_count = ass_trans_count;
    }

    public double getAss_total_money() {
        return ass_total_money;
    }

    public void setAss_total_money(double ass_total_money) {
        this.ass_total_money = ass_total_money;
    }
}

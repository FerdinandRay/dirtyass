package com.fang.model;

/**
 * Created by bull on 16-6-3.
 */
public class Trade {
    private int trade_id;
    private int trade_ass_id;
    private double trade_money;
    private String trade_reason;
    private String trade_date;

    public Trade() {
    }

    public Trade(int trade_ass_id, double trade_money, String trade_reason, String trade_date) {
        this.trade_ass_id = trade_ass_id;
        this.trade_money = trade_money;
        this.trade_reason = trade_reason;
        this.trade_date = trade_date;
    }

    public int getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(int trade_id) {
        this.trade_id = trade_id;
    }

    public int getTrade_ass_id() {
        return trade_ass_id;
    }

    public void setTrade_ass_id(int trade_ass_id) {
        this.trade_ass_id = trade_ass_id;
    }

    public double getTrade_money() {
        return trade_money;
    }

    public void setTrade_money(double trade_money) {
        this.trade_money = trade_money;
    }

    public String getTrade_reason() {
        return trade_reason;
    }

    public void setTrade_reason(String trade_reason) {
        this.trade_reason = trade_reason;
    }

    public String getTrade_date() {
        return trade_date;
    }

    public void setTrade_date(String trade_date) {
        this.trade_date = trade_date;
    }
}

package com.fang.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fang.database.D;
import com.fang.database.TradeDbManager;
import com.fang.dirtyass.R;
import com.fang.model.Trade;
import com.fang.utils.Static;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;

/**
 * Created by bull on 16-6-13.
 */
public class AddTradeActivity extends AppCompatActivity implements View.OnClickListener {

    // 控件
    private EditText et_trade_money, et_trade_reason;
    private TextView tv_trade_date;
    private Button btn_add, btn_modify, btn_del, btn_date;

    // 数据库
    private TradeDbManager mTradeDbManager;

    // 存储信息
    private int state;
    private int ass_id;
    private int trade_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trade);

        // 初始化参数
        mTradeDbManager = new TradeDbManager(this);

        state = getIntent().getIntExtra("state", Static.ADD);
        ass_id = getIntent().getIntExtra("ass_id", 0);
        trade_id = getIntent().getIntExtra("trade_id", 0);

        // 初始化控件
        et_trade_money = (EditText) findViewById(R.id.trade_money);
        et_trade_reason = (EditText) findViewById(R.id.trade_reason);
        tv_trade_date = (TextView) findViewById(R.id.trade_date);
        btn_add = (Button) findViewById(R.id.btn_add_trade);
        btn_modify = (Button) findViewById(R.id.btn_modify_trade);
        btn_del = (Button) findViewById(R.id.btn_del_trade);
        btn_date = (Button) findViewById(R.id.btn_add_date);

        // 设置按钮
        btn_add.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_date.setOnClickListener(this);

        if (state == Static.ADD) {
            showEmpty();
        }
        if (state == Static.EDIT) {
            showEdit();
        }
    }

    private void showEmpty() {
        btn_modify.setVisibility(View.GONE);
        btn_del.setVisibility(View.GONE);
    }

    private void showEdit() {
        btn_add.setVisibility(View.GONE);
        mTradeDbManager.open();
        Cursor cursor = mTradeDbManager.selectById(trade_id);
        mTradeDbManager.close();
        if (cursor.moveToFirst()) {
            et_trade_money.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(D.Tbl_Trade.TRADE_MONEY))));
            et_trade_reason.setText(cursor.getString(cursor.getColumnIndex(D.Tbl_Trade.TRADE_REASON)));
            tv_trade_date.setText(cursor.getString(cursor.getColumnIndex(D.Tbl_Trade.TRADE_DATE)));
        }
    }

    private void add() {
        Trade trade = new Trade();
        trade.setTrade_ass_id(ass_id);
        trade.setTrade_money(Double.parseDouble(et_trade_money.getText().toString()));
        trade.setTrade_reason(et_trade_reason.getText().toString());
        trade.setTrade_date(tv_trade_date.getText().toString());

        btn_modify.setVisibility(View.GONE);
        btn_del.setVisibility(View.GONE);
        mTradeDbManager.open();
        long result = mTradeDbManager.insertTrade(trade);
        mTradeDbManager.close();
        if (result > 0) {
            Snackbar.make(getCurrentFocus(), "记账成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "记账失败", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    private void edit() {
        Trade trade = new Trade();
        trade.setTrade_id(trade_id);
        trade.setTrade_money(Double.parseDouble(et_trade_money.getText().toString()));
        trade.setTrade_reason(et_trade_reason.getText().toString());
        trade.setTrade_date(tv_trade_date.getText().toString());

        mTradeDbManager.open();
        long result = mTradeDbManager.updateTrade(trade);
        mTradeDbManager.close();
        if (result > 0) {
            Snackbar.make(getCurrentFocus(), "修改记账成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "修改记账失败", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    private void del() {
        mTradeDbManager.open();
        long result = mTradeDbManager.deleteTrade(trade_id);
        mTradeDbManager.close();
        if (result > 0) {
            Snackbar.make(getCurrentFocus(), "删除记账成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "删除记账失败", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_trade:
                add();
                break;
            case R.id.btn_modify_trade:
                edit();
                break;
            case R.id.btn_del_trade:
                del();
                break;
            case R.id.btn_add_date:
                Dialog.Builder builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        tv_trade_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(dialog.getDate()));
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        fragment.dismiss();
                        super.onNegativeActionClicked(fragment);
                    }
                };
                builder.positiveAction("OK");
                builder.negativeAction("CANCEL");
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getSupportFragmentManager(), null);
                break;
        }
    }
}

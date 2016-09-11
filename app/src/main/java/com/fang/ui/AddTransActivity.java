package com.fang.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fang.database.AssDbManager;
import com.fang.database.D;
import com.fang.database.TransDbManager;
import com.fang.dirtyass.R;
import com.fang.model.Trans;
import com.fang.utils.Static;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Created by bull on 16-6-13.
 */
public class AddTransActivity extends AppCompatActivity implements View.OnClickListener {

    // 控件
    private Spinner mSpinner;
    private EditText et_trans_money, et_trans_reason;
    private TextView tv_trans_date;
    private Button btn_add, btn_modify, btn_del, btn_date;

    // 数据库
    private AssDbManager mAssDbManager;
    private TransDbManager mTransDbManager;
    private ArrayAdapter<String> adapter;
    private String[] spinnerArray;
    private HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

    // 存储信息
    private int state;
    private int ass_id;
    private int trans_id;
    private int trans_otherass_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trans);

        // 初始化参数
        mAssDbManager = new AssDbManager(this);
        mTransDbManager = new TransDbManager(this);

        state = getIntent().getIntExtra("state", Static.ADD);
        ass_id = getIntent().getIntExtra("ass_id", 0);
        trans_id = getIntent().getIntExtra("trans_id", 0);

        // 初始化控件
        et_trans_money = (EditText) findViewById(R.id.trans_money);
        et_trans_reason = (EditText) findViewById(R.id.trans_reason);
        tv_trans_date = (TextView) findViewById(R.id.trans_date);
        btn_add = (Button) findViewById(R.id.btn_add_trans);
        btn_modify = (Button) findViewById(R.id.btn_modify_trans);
        btn_del = (Button) findViewById(R.id.btn_del_trans);
        btn_date = (Button) findViewById(R.id.btn_add_date);

        // 设置按钮
        btn_add.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_date.setOnClickListener(this);

        // 获得下拉框数据
        mAssDbManager.open();
        Cursor cursor = mAssDbManager.selectAssWithId();
        mAssDbManager.close();
        if (cursor.moveToFirst()) {
            int i = 0;
            spinnerArray = new String[cursor.getCount()-1];
            do {
                if (ass_id != (cursor.getInt(cursor.getColumnIndex(D.Tbl_Ass.ASS_ID)))) {
                    hashMap.put(cursor.getString(cursor.getColumnIndex(D.Tbl_Ass.ASS_NAME)), cursor.getInt(cursor.getColumnIndex(D.Tbl_Ass.ASS_ID)));
                    spinnerArray[i] = cursor.getString(cursor.getColumnIndex(D.Tbl_Ass.ASS_NAME));
                    i++;
                }
            } while (cursor.moveToNext());
        }

        // 设置adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trans_otherass_id = hashMap.get(mSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (state == Static.ADD) {
            showEmpty();
        }
        if (state == Static.EDIT) {
            showEdit();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_trans:
                add();
                break;
            case R.id.btn_modify_trans:
                edit();
                break;
            case R.id.btn_del_trans:
                del();
                break;
            case R.id.btn_add_date:
                Dialog.Builder builder = new DatePickerDialog.Builder() {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        tv_trans_date.setText(new SimpleDateFormat("yyyy-MM-dd").format(dialog.getDate()));
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

    private void showEmpty() {
        btn_modify.setVisibility(View.GONE);
        btn_del.setVisibility(View.GONE);
    }

    private void showEdit() {
        btn_add.setVisibility(View.GONE);
        mTransDbManager.open();
        Cursor cursor = mTransDbManager.selectById(trans_id);
        mTransDbManager.close();
        if (cursor.moveToFirst()) {
            et_trans_money.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(D.Tbl_Trans.TRANS_MONEY))));
            et_trans_reason.setText(cursor.getString(cursor.getColumnIndex(D.Tbl_Trans.TRANS_REASON)));
            tv_trans_date.setText(cursor.getString(cursor.getColumnIndex(D.Tbl_Trans.TRANS_DATE)));
        }
    }

    private void add() {
        Trans trans = new Trans();
        trans.setTrans_payass_id(ass_id);
        trans.setTrans_otherass_id(trans_otherass_id);
        trans.setTrans_money(Double.parseDouble(et_trans_money.getText().toString()));
        trans.setTrans_reason(et_trans_reason.getText().toString());
        trans.setTrans_date(tv_trans_date.getText().toString());

        btn_modify.setVisibility(View.GONE);
        btn_del.setVisibility(View.GONE);
        mTransDbManager.open();
        long result = mTransDbManager.insertTrans(trans);
        mTransDbManager.close();
        if (result > 0) {
            Snackbar.make(getCurrentFocus(), "转账成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "转账失败", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    private void edit() {
        Trans trans = new Trans();
        trans.setTrans_id(trans_id);
        trans.setTrans_otherass_id(trans_otherass_id);
        trans.setTrans_money(Double.parseDouble(et_trans_money.getText().toString()));
        trans.setTrans_reason(et_trans_reason.getText().toString());
        trans.setTrans_date(tv_trans_date.getText().toString());

        mTransDbManager.open();
        long result = mTransDbManager.updateTrans(trans);
        mTransDbManager.close();
        if (result > 0) {
            Snackbar.make(getCurrentFocus(), "修改转账成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "修改转账失败", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }

    private void del() {
        mTransDbManager.open();
        long result = mTransDbManager.deleteTrans(trans_id);
        mTransDbManager.close();
        if (result > 0) {
            Snackbar.make(getCurrentFocus(), "删除转账成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "删除转账失败", Snackbar.LENGTH_SHORT).show();
        }
        finish();
    }
}

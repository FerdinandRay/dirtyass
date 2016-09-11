package com.fang.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.fang.adapter.AssAdapter;
import com.fang.database.AssDbManager;
import com.fang.database.D;
import com.fang.dirtyass.R;
import com.fang.model.Ass;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private AssDbManager dbManager;
    private RecyclerView mRecyclerView;
    private AssAdapter mAssAdapter;
    private ArrayList<Ass> data = new ArrayList<Ass>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 初始化参数
        mContext = MainActivity.this;
        dbManager = new AssDbManager(mContext);
        mAssAdapter = new AssAdapter(data, mContext);

        // 设置标题
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle("Jessica");

        // 设置列表
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAssAdapter);

        // 获得添加按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add_ass);
        if(fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
                    View view = layoutInflater.inflate(R.layout.dialog_add_ass, null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    final EditText et_assName = (EditText) view.findViewById(R.id.dialog_ass_name);
                    builder.setTitle("添加欠账人");
                    builder.setView(view);
                    builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            add(et_assName.getText().toString());
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            refresh();
                        }
                    });
                    builder.create();
                    builder.show();
                }
            });
        }
    }

    private void add(String ass_name){
        dbManager.open();
        Ass ass = new Ass(ass_name);
        long result = dbManager.insertAss(ass);
        if (result > 0){
            Snackbar.make(getCurrentFocus(), "添加欠账人成功", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(getCurrentFocus(), "添加欠账人失败", Snackbar.LENGTH_SHORT).show();
        }
        dbManager.close();
        refresh();
    }

    private void refresh(){
        Ass ass;
        dbManager.open();
        data.clear();
        Cursor cursor = dbManager.selectAllAss();

        if(cursor.moveToFirst()) {
            do {
                int ass_id = cursor.getInt(cursor.getColumnIndex(D.Tbl_Ass.ASS_ID));
                dbManager.updateMoney(ass_id);
                dbManager.updateTradeCount(ass_id);
                dbManager.updateTransCount(ass_id);
                ass = new Ass();
                ass.setAss_id(ass_id);
                ass.setAss_name(cursor.getString(cursor.getColumnIndex(D.Tbl_Ass.ASS_NAME)));
                ass.setAss_trade_count(cursor.getInt(cursor.getColumnIndex(D.Tbl_Ass.ASS_TRADE_COUNT)));
                ass.setAss_trans_count(cursor.getInt(cursor.getColumnIndex(D.Tbl_Ass.ASS_TRANS_COUNT)));
                ass.setAss_total_money(cursor.getDouble(cursor.getColumnIndex(D.Tbl_Ass.ASS_TOTAL_MONEY)));
                data.add(ass);
            } while (cursor.moveToNext());
        }
        dbManager.close();

        if (!(data.size() < 1)){
            mRecyclerView.setAdapter(mAssAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
    }
}

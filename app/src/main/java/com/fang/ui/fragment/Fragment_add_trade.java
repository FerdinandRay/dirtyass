package com.fang.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.adapter.TradeAdapter;
import com.fang.database.D;
import com.fang.database.TradeDbManager;
import com.fang.dirtyass.R;
import com.fang.model.Trade;
import com.fang.ui.AddTradeActivity;
import com.fang.utils.Static;

import java.util.ArrayList;

/**
 * Created by bull on 16-6-11.
 */
public class Fragment_add_trade extends Fragment {

    private View mView;
    private FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private TradeAdapter mTradeAdapter;
    private TradeDbManager dbManager;
    private Context mContext;
    private ArrayList<Trade> data = new ArrayList<Trade>();

    private int ass_id;

    public void setmContext(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_detail_viewpager, container, false);
        init(mView);
        return mView;
    }

    private void init(View mView){

        final Bundle bundle = getArguments();
        ass_id = bundle.getInt("ass_id");

        dbManager = new TradeDbManager(mContext);
        mTradeAdapter = new TradeAdapter(data, mContext);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mTradeAdapter);

        fab = (FloatingActionButton) mView.findViewById(R.id.fab);
        if(fab != null){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AddTradeActivity.class);
                    intent.putExtra("ass_id", ass_id);
                    intent.putExtra("state", Static.ADD);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void refresh(){
        Trade trade;
        dbManager.open();
        data.clear();
        Cursor cursor = dbManager.selectAllTradeByAssId(ass_id);
        dbManager.close();
        if(cursor.moveToFirst()){
            do {
                trade = new Trade();
                trade.setTrade_id(cursor.getInt(cursor.getColumnIndex(D.Tbl_Trade.TRADE_ID)));
                trade.setTrade_ass_id(cursor.getInt(cursor.getColumnIndex(D.Tbl_Trade.TRADE_ASSID)));
                trade.setTrade_money(cursor.getDouble(cursor.getColumnIndex(D.Tbl_Trade.TRADE_MONEY)));
                trade.setTrade_reason(cursor.getString(cursor.getColumnIndex(D.Tbl_Trade.TRADE_REASON)));
                trade.setTrade_date(cursor.getString(cursor.getColumnIndex(D.Tbl_Trade.TRADE_DATE)));
                data.add(trade);
            } while (cursor.moveToNext());
        }

        if(!(data.size() < 1)){
            mRecyclerView.setAdapter(mTradeAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }
}

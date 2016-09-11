package com.fang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fang.model.Trade;

/**
 * Created by bull on 16-6-3.
 */
public class TradeDbManager {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;

    public TradeDbManager(Context context){
        mDatabaseHelper = DatabaseHelper.instance(context);
    }

    public void open(){
        try {
            mDb = mDatabaseHelper.getWritableDatabase();
        } catch (Exception e){
            Log.d(D.LOG_TAG, "数据库打开失败");
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            mDb.close();
            mDatabaseHelper.close();
        } catch (Exception e) {
            Log.d(D.LOG_TAG, "数据库关闭失败");
            e.printStackTrace();
        }
    }

    public long insertTrade(Trade trade){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Trade.TRADE_ASSID, trade.getTrade_ass_id());
        values.put(D.Tbl_Trade.TRADE_MONEY, trade.getTrade_money());
        values.put(D.Tbl_Trade.TRADE_REASON, trade.getTrade_reason());
        values.put(D.Tbl_Trade.TRADE_DATE, trade.getTrade_date());

        return mDb.insert(D.Table.Tbl_Trade, null, values);
    }

    public int deleteTrade(int trade_id){
        return mDb.delete(D.Table.Tbl_Trade, D.Tbl_Trade.TRADE_ID+"=?", new String[]{String.valueOf(trade_id)});
    }

    public int deleteTradeByAssId(int ass_id){
        return mDb.delete(D.Table.Tbl_Trade, D.Tbl_Trade.TRADE_ASSID+"=?", new String[]{String.valueOf(ass_id)});
    }

    public int updateTrade(Trade trade){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Trade.TRADE_ID, trade.getTrade_id());
        values.put(D.Tbl_Trade.TRADE_MONEY, trade.getTrade_money());
        values.put(D.Tbl_Trade.TRADE_REASON, trade.getTrade_reason());
        values.put(D.Tbl_Trade.TRADE_DATE, trade.getTrade_date());

        return mDb.update(D.Table.Tbl_Trade, values, D.Tbl_Trade.TRADE_ID+"=?", new String[]{String.valueOf(trade.getTrade_id())});
    }

    public Cursor selectAllTrade(){
        Cursor mCursor = mDb.query(D.Table.Tbl_Trade, null, null, null, null, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectAllTradeByAssId(int ass_id){
        Cursor mCursor = mDb.query(D.Table.Tbl_Trade, null, D.Tbl_Trade.TRADE_ASSID+"=?", new String[]{String.valueOf(ass_id)}, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectById(int trade_id){
        Cursor mCursor = mDb.query(D.Table.Tbl_Trade, null, D.Tbl_Trade.TRADE_ID+"=?", new String[]{String.valueOf(trade_id)}, null, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}

package com.fang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fang.model.Ass;
import com.fang.utils.Static;

/**
 * Created by bull on 16-6-3.
 */
public class AssDbManager {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;

    public AssDbManager(Context context){
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

    public long insertAss(Ass ass){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Ass.ASS_NAME, ass.getAss_name());
        values.put(D.Tbl_Ass.ASS_TRADE_COUNT, Static.DEFAULT_TRADE);
        values.put(D.Tbl_Ass.ASS_TRANS_COUNT, Static.DEFAULT_TRANS);
        values.put(D.Tbl_Ass.ASS_TOTAL_MONEY, Static.DEFAULT_MONEY);
        return mDb.insert(D.Table.Tbl_Ass, null, values);
    }

    public int deleteAss(int ass_id){
        return mDb.delete(D.Table.Tbl_Ass, D.Tbl_Ass.ASS_ID+"=?", new String[]{String.valueOf(ass_id)});
    }

    public int updateAss(Ass ass){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Ass.ASS_ID, ass.getAss_id());
        values.put(D.Tbl_Ass.ASS_NAME, ass.getAss_name());
        return mDb.update(D.Table.Tbl_Ass, values, D.Tbl_Ass.ASS_ID+"=?", new String[]{String.valueOf(ass.getAss_id())});
    }

    public int updateAssName(String ass_name, int ass_id){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Ass.ASS_NAME, ass_name);
        return mDb.update(D.Table.Tbl_Ass, values, D.Tbl_Ass.ASS_ID+"=?", new String[]{String.valueOf(ass_id)});
    }

    public void updateMoney(int ass_id){
        String sql = "update tbl_ass set ass_total_money="
                + "(select total(trade_money) from tbl_trade where trade_ass_id=" +ass_id + ")-"
                + "(select total(trans_money) from tbl_trans where trans_payass_id=" +ass_id + ")+"
                + "(select total(trans_money) from tbl_trans where trans_otherass_id=" +ass_id+ ")"
                + " where _id=" + ass_id;
        mDb.execSQL(sql);
    }

    public void updateTradeCount(int ass_id){
        String sql = "update tbl_ass set ass_trade_count="
                + "(select count(*) from tbl_trade where trade_ass_id=" + ass_id
                + ") where _id=" + ass_id;
        Log.d("fang", sql);
        mDb.execSQL(sql);
    }

    public void updateTransCount(int ass_id){
        String sql = "update tbl_ass set ass_trans_count="
                + "(select count(*) from tbl_trans where trans_payass_id=" + ass_id
                + ") where _id=" + ass_id;
        mDb.execSQL(sql);
    }

    public Cursor selectAllAss(){
        Cursor mCursor = mDb.query(D.Table.Tbl_Ass, null, null, null, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor selectOneAss(int ass_id){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Ass.ASS_ID, ass_id);
        Cursor mCursor = mDb.query(D.Table.Tbl_Ass, null, D.Tbl_Ass.ASS_ID+"=?", new String[]{String.valueOf(ass_id)}, null, null, null, null);
        return mCursor;
    }

    public Cursor selectAssWithId(){
        Cursor mCursor = mDb.query(D.Table.Tbl_Ass, new String[]{D.Tbl_Ass.ASS_ID, D.Tbl_Ass.ASS_NAME},null, null, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

}

package com.fang.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fang.model.Trans;

/**
 * Created by bull on 16-6-3.
 */
public class TransDbManager {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb;

    public TransDbManager(Context context){
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

    public long insertTrans(Trans trans){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Trans.TRANS_PAYASS_ID, trans.getTrans_payass_id());
        values.put(D.Tbl_Trans.TRANS_OTHERASS_ID, trans.getTrans_otherass_id());
        values.put(D.Tbl_Trans.TRANS_MONEY, trans.getTrans_money());
        values.put(D.Tbl_Trans.TRANS_REASON, trans.getTrans_reason());
        values.put(D.Tbl_Trans.TRANS_DATE, trans.getTrans_date());

        return mDb.insert(D.Table.Tbl_Trans, null, values);
    }

    public int deleteTrans(int trans_id){
        return mDb.delete(D.Table.Tbl_Trans, D.Tbl_Trans.TRANS_ID+"=?", new String[]{String.valueOf(trans_id)});
    }

    public int deleteTransByPayAssId(int ass_id){
        return mDb.delete(D.Table.Tbl_Trans, D.Tbl_Trans.TRANS_PAYASS_ID+"=?", new String[]{String.valueOf(ass_id)});
    }

    public int deleteTransByOtherAssId(int ass_id){
        return mDb.delete(D.Table.Tbl_Trans, D.Tbl_Trans.TRANS_OTHERASS_ID+"=?", new String[]{String.valueOf(ass_id)});
    }

    public int updateTrans(Trans trans){
        ContentValues values = new ContentValues();
        values.put(D.Tbl_Trans.TRANS_ID, trans.getTrans_id());
        values.put(D.Tbl_Trans.TRANS_OTHERASS_ID, trans.getTrans_otherass_id());
        values.put(D.Tbl_Trans.TRANS_MONEY, trans.getTrans_money());
        values.put(D.Tbl_Trans.TRANS_REASON, trans.getTrans_reason());
        values.put(D.Tbl_Trans.TRANS_DATE, trans.getTrans_date());

        return mDb.update(D.Table.Tbl_Trans, values, D.Tbl_Trans.TRANS_ID+"=?", new String[]{String.valueOf(trans.getTrans_id())});
    }

    public Cursor selectAllTransByAssId(int ass_id){
        Cursor mCursor = mDb.query(D.Table.Tbl_Trans, null, D.Tbl_Trans.TRANS_PAYASS_ID+"=?", new String[]{String.valueOf(ass_id)}, null, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor selectById(int trans_id){
        Cursor mCursor = mDb.query(D.Table.Tbl_Trans, null, D.Tbl_Trans.TRANS_ID+"=?", new String[]{String.valueOf(trans_id)}, null, null, null, null);
        if (mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public String getAssNameByTransId(int ass_id){
        String ass_name;
        Cursor mCursor = mDb.query(D.Table.Tbl_Ass, null, D.Tbl_Ass.ASS_ID+"=?", new String[]{String.valueOf(ass_id)}, null, null, null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        ass_name = mCursor.getString(mCursor.getColumnIndex(D.Tbl_Ass.ASS_NAME));
        return ass_name;
    }

}

package com.fang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bull on 16-6-2.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper mDatabaseHelper;

    public DatabaseHelper(Context context){
        super(context, D.DATABASE_NAME, null, D.DATABASE_VERSION);
    }

    public static DatabaseHelper instance(Context context){
        if (mDatabaseHelper == null){
            mDatabaseHelper = new DatabaseHelper(context);
        }
        return mDatabaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(D.CREATE_TABLE.CREATE_TBL_ASS);
        db.execSQL(D.CREATE_TABLE.CREATE_TBL_TRADE);
        db.execSQL(D.CREATE_TABLE.CREATE_TBL_TRANS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

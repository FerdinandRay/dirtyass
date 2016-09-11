package com.fang.database;

/**
 * Created by bull on 16-5-31.
 */
public class D {

    public static final String DATABASE_NAME = "dirtyass.db";
    public static final int DATABASE_VERSION = 1;

    public static final String LOG_TAG = "fff";


    public static final class Table{
        // 表1 欠钱人信息表
        public static final String Tbl_Ass = "tbl_ass";
        // 表2 欠钱明细表
        public static final String Tbl_Trade = "tbl_trade";
        // 表3 销账明细表
        public static final String Tbl_Trans = "tbl_trans";
        // 表4 欠钱总金额表
        public static final String Tbl_Total = "tbl_total";
    }

    public static final class Tbl_Ass{
        public static final String ASS_ID = "_id";
        public static final String ASS_NAME = "ass_name";
        public static final String ASS_TRADE_COUNT = "ass_trade_count";
        public static final String ASS_TRANS_COUNT = "ass_trans_count";
        public static final String ASS_TOTAL_MONEY = "ass_total_money";
    }

    public static final class Tbl_Trade{
        public static final String TRADE_ID = "_id";
        public static final String TRADE_ASSID = "trade_ass_id";
        public static final String TRADE_MONEY = "trade_money";
        public static final String TRADE_REASON = "trade_reason";
        public static final String TRADE_DATE = "trade_date";
    }

    public static final class Tbl_Trans{
        public static final String TRANS_ID = "_id";
        // 下面这两个人的id都是在Tbl_Ass中寻找的
        public static final String TRANS_PAYASS_ID = "trans_payass_id";
        public static final String TRANS_OTHERASS_ID = "trans_otherass_id";
        public static final String TRANS_MONEY = "trans_money";
        public static final String TRANS_REASON = "trans_reason";
        public static final String TRANS_DATE = "trans_date";
    }

    // 暂时没用，不知道以后会不会用到
    public static final class Tbl_Total{
        // 这张表主要用于查询对应欠账人欠账总额
        public static final String TOTAL_ID = "total_id";
        public static final String TOTAL_ASS_ID = "total_ass_id";
        public static final String TOTAL_MONEY = "total_money";
        public static final String TOTAL_DATE = "total_date";
    }

    public static final class CREATE_TABLE{
        public static final String CREATE_TBL_ASS = "CREATE TABLE "
                + Table.Tbl_Ass + "("
                + Tbl_Ass.ASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Tbl_Ass.ASS_NAME + " TEXT, "
                + Tbl_Ass.ASS_TRADE_COUNT + " INTEGER, "
                + Tbl_Ass.ASS_TRANS_COUNT + " INTEGER, "
                + Tbl_Ass.ASS_TOTAL_MONEY + " REAL)";

        public static final String CREATE_TBL_TRADE = "CREATE TABLE "
                + Table.Tbl_Trade + "("
                + Tbl_Trade.TRADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Tbl_Trade.TRADE_ASSID + " INTEGER, "
                + Tbl_Trade.TRADE_MONEY + " REAL, "
                + Tbl_Trade.TRADE_REASON + " TEXT, "
                + Tbl_Trade.TRADE_DATE + " TEXT)";

        public static final String CREATE_TBL_TRANS = "CREATE TABLE "
                + Table.Tbl_Trans + "("
                + Tbl_Trans.TRANS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Tbl_Trans.TRANS_PAYASS_ID + " INTEGER, "
                + Tbl_Trans.TRANS_OTHERASS_ID + " INTEGER, "
                + Tbl_Trans.TRANS_MONEY + " REAL, "
                + Tbl_Trans.TRANS_REASON + " INTEGER, "
                + Tbl_Trans.TRANS_DATE + " TEXT)";
    }


}

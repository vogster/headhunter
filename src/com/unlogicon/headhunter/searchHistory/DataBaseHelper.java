package com.unlogicon.headhunter.searchHistory;

/**
 * Created by Valo on 14.12.14.
 */
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DataBaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME = "hhdatabase.db";
    private static final String  DATABASE_TABLE= "queries";
    private static final int DATABASE_VERSION = 1;

    //table fields
    public static final String ID = "_id";
    public static final String JOB_NAME = "job_name";
    public static final String SEARCH_QUERY = "search_query";
    public static final String CURRENCY = "currency";
    public static final String SALARY = "salary";
    public static final String PROF = "prof_area";
    public static final String SPECIALIZATION = "specialization";
    public static final String EXPERIENCE = "experience";
    public static final String EMPLOYMENT = "employment";
    public static final String SCHEDULE = "schedule";
    public static final String PERIOD = "period";
    public static final String ORDER_BY = "order_by";
    public static final String AREA = "area";
    public static final String AGENCY = "agency";
    public static final String HANDICAPPED = "handicapped";
    public static final String ONLY_SALARY = "only_salary";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + DATABASE_TABLE + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + JOB_NAME + " text, "
            + SEARCH_QUERY + " text, "
            + CURRENCY + " text, "
            + SALARY + " text, "
            + PROF + " text, "
            + SPECIALIZATION + " text, "
            + EXPERIENCE + " text, "
            + EMPLOYMENT + " text, "
            + SCHEDULE + " text, "
            + PERIOD + " text, "
            + ORDER_BY + " text, "
            + AREA + " text, "
            + AGENCY + " INTEGER NOT NULL DEFAULT 0, "   // flags for checkbox, true if 1, false if 0
            + HANDICAPPED + " INTEGER NOT NULL DEFAULT 0, "
            + ONLY_SALARY + " INTEGER NOT NULL DEFAULT 0"
            + ");";

    private static final String RAWS_DELETE_SCRIPT = "Delete from "
            + DATABASE_TABLE;

    //super without errorHandler

    public DataBaseHelper(Context context, String name, CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_SCRIPT);
    }

    public void deleteAllRows(SQLiteDatabase database) {
        database.execSQL(RAWS_DELETE_SCRIPT);
    }

    public void executeQuery(SQLiteDatabase database, String strQuery) {
        database.execSQL(strQuery);
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
}
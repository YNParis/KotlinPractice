package com.demos.yxn.lifecircle.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YXN on 2018/7/10.
 */

public class DBHelpler extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "db_test";
    public static String TABLE_NAME = "user";
    public static int VERSION = 1;

    /**
     * @param context 上下文
     * @param name    数据库名字
     * @param factory 接收数据，一般情况为null
     * @param version 数据库版本号
     */
    public DBHelpler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelpler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ( name text default \"\"," +
                "age integer default 0, gender text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

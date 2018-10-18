package com.demos.yxn.lifecircle.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by YXN on 2018/7/10.
 */

public class DBDao {

    private DBHelpler helpler;
    private SQLiteDatabase db;

    public DBDao(Context context) {
        helpler = new DBHelpler(context);
        db = helpler.getWritableDatabase();
    }

    private void update(SQLiteDatabase db, ContentValues values) {
        values = new ContentValues();
//在values中添加内容
        values.put("snumber", "101003");
//修改条件
        String whereClause = "id=?";
//修改添加参数
        String[] whereArgs = {String.valueOf(1)};
//修改
        db.update("usertable", values, whereClause, whereArgs);
    }

    private void update(SQLiteDatabase db, String sql) {
//修改SQL语句
        sql = "update stu_table set snumber = 654321 where id = 1";
//执行SQL
        db.execSQL(sql);
    }

    public String execQuery(String sql) {
        return "";
    }
}

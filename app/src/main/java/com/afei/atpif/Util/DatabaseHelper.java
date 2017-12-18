package com.afei.atpif.Util;

/**
 * Created by afei on 2017/11/27.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.afei.atpif.MainActivity;


public class DatabaseHelper extends SQLiteOpenHelper {

    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static DatabaseHelper instance = null;


    /* 私有构造方法，防止被实例化 */
    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    //数据库名字
    private static final String DB_NAME = "TrainList.db";

    //本版号
    private static final int VERSION = 1;

    //创建表
    private static final  String CREATE_TABLE_NOTE = "CREATE TABLE IF NOT EXISTS atpifList(_id integer primary key autoincrement,"+
            "atpifName text, atpifRUNState text, atpifIP text, clientNum text, lastConnectDate text, atpif_photo_path text)";

    //删除表
    private static final String DROP_TABLE_NOTE = "drop table if exists note";


    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQLiteDatabase 用于操作数据库的工具类
        db.execSQL(CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_NOTE);
        db.execSQL(CREATE_TABLE_NOTE);
    }
}
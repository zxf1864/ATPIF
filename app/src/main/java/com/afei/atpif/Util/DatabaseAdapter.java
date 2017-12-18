package com.afei.atpif.Util;

/**
 * Created by afei on 2017/11/27.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.afei.atpif.CustomListView.ATPIFListModel;

import java.util.ArrayList;


public class DatabaseAdapter {

    private DatabaseHelper dbHelper;

    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static DatabaseAdapter instance = null;

    /* 私有构造方法，防止被实例化 */
    private DatabaseAdapter(Context context) {
        dbHelper = DatabaseHelper.getInstance(context);
    }

    /* 1:懒汉式，静态工程方法，创建实例 */
    public static DatabaseAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAdapter(context);
        }
        return instance;
    }

    /**
     * 添加数据
     *
     * @param atpifList
     */
    public void create(ATPIFListModel atpifList) {
        String sql = "insert into atpifList(atpifName, atpifRUNState, atpifIP, clientNum, lastConnectDate, atpif_photo_path)values(?,?,?,?,?,?)";
        Object[] args = {atpifList.atpifName,atpifList.atpifRUNState, atpifList.atpifIP, atpifList.clientNum,atpifList.lastConnectDate,atpifList.atpif_photo_path};
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL(sql, args);
        db.close();
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public void remove(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from atpifList where _id = ?";
        Object[] args = {id};
        db.execSQL(sql, args);
        db.close();
    }

    /**
     * 删除所有表数据
     *
     * @param
     */
    public void removeALL() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from atpifList";

        db.execSQL(sql);

        sql = "update sqlite_sequence SET seq = 0 where name ='atpifList'";

        db.execSQL(sql);

        db.close();
    }

    /**
     * 修改数据
     *
     * @param atpifList
     */
    public void update(ATPIFListModel atpifList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "update atpifList set atpifName = ?, atpifRUNState = ?, atpifIP = ?,clientNum=?, lastConnectDate=?,atpif_photo_path=? where _id = ?";
        Object[] args = {atpifList.atpifName,atpifList.atpifRUNState, atpifList.atpifIP, atpifList.clientNum,atpifList.lastConnectDate,atpifList.atpif_photo_path,atpifList.atpifid};
        db.execSQL(sql, args);
        db.close();
    }

    /**
     * 按id查询
     *
     * @param id
     * @return
     */
    public ATPIFListModel findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from atpifList where _id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});

        ATPIFListModel atpifList = null;
        if (cursor.moveToNext()) {
            atpifList = new ATPIFListModel();

            atpifList.atpifid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            atpifList.atpifName = cursor.getString(cursor.getColumnIndexOrThrow("atpifName"));
            atpifList.atpifRUNState = cursor.getString(cursor.getColumnIndexOrThrow("atpifRUNState"));
            atpifList.atpifIP = cursor.getString(cursor.getColumnIndexOrThrow("atpifIP"));
            atpifList.clientNum = cursor.getString(cursor.getColumnIndexOrThrow("clientNum"));
            atpifList.lastConnectDate = cursor.getString(cursor.getColumnIndexOrThrow("lastConnectDate"));
            atpifList.atpif_photo_path = cursor.getString(cursor.getColumnIndexOrThrow("atpif_photo_path"));
        }
        cursor.close();
        db.close();

        return atpifList;
    }

    /**
     * 查询所有
     *
     * @return
     */
    public ArrayList<ATPIFListModel> findAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from atpifList";
        Cursor cursor = db.rawQuery(sql,null);

        ArrayList<ATPIFListModel> atpifLists = new ArrayList<>();
        ATPIFListModel atpifList = null;
        while (cursor.moveToNext()) {
            atpifList = new ATPIFListModel();

            atpifList.atpifid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            atpifList.atpifName = cursor.getString(cursor.getColumnIndexOrThrow("atpifName"));
            atpifList.atpifRUNState = cursor.getString(cursor.getColumnIndexOrThrow("atpifRUNState"));
            atpifList.atpifIP = cursor.getString(cursor.getColumnIndexOrThrow("atpifIP"));
            atpifList.clientNum = cursor.getString(cursor.getColumnIndexOrThrow("clientNum"));
            atpifList.lastConnectDate = cursor.getString(cursor.getColumnIndexOrThrow("lastConnectDate"));
            atpifList.atpif_photo_path = cursor.getString(cursor.getColumnIndexOrThrow("atpif_photo_path"));

            atpifLists.add(atpifList);
        }
        cursor.close();
        db.close();
        return atpifLists;
    }

    /**
     * 分页查询
     *
     * @param limit 默认查询的数量
     * @param skip 跳过的行数
     * @return
     */
    public ArrayList<ATPIFListModel> findLimit(int limit, int skip) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from atpifList order by _id desc limit ? offset ?";
        String[] strs = new String[]{String.valueOf(limit), String.valueOf(skip)};
        Cursor cursor = db.rawQuery(sql,strs);

        ArrayList<ATPIFListModel> atpifLists = new ArrayList<>();
        ATPIFListModel atpifList = null;
        while (cursor.moveToNext()) {
            atpifList = new ATPIFListModel();

            atpifList.atpifid = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            atpifList.atpifName = cursor.getString(cursor.getColumnIndexOrThrow("atpifName"));
            atpifList.atpifRUNState = cursor.getString(cursor.getColumnIndexOrThrow("atpifRUNState"));
            atpifList.atpifIP = cursor.getString(cursor.getColumnIndexOrThrow("atpifIP"));
            atpifList.clientNum = cursor.getString(cursor.getColumnIndexOrThrow("clientNum"));
            atpifList.lastConnectDate = cursor.getString(cursor.getColumnIndexOrThrow("lastConnectDate"));
            atpifList.atpif_photo_path = cursor.getString(cursor.getColumnIndexOrThrow("atpif_photo_path"));

            atpifLists.add(atpifList);
        }
        cursor.close();
        db.close();
        return atpifLists;
    }
}

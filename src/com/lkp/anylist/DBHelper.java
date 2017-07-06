package com.lkp.anylist;

/**
 * Created by LKP on 2015/3/27.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    //表名
    private static final String TABLE_NAME="notes";
    //表的主键
    private static final String KEY_ID = "_id";

    //表中事件的紧急程度
    private static final String PRIORITY = "priority";
    //添加的内容
    private static final String CONTENT = "content";
    //时间
    private static final String TIME="time";
    private static final String ISNOTIFY="isNotifiy";
    private static final String FENLEI="fenLei";




    //创建一个表的sql语句
    private String sql = "create table "
            +TABLE_NAME
            +"("+KEY_ID
            +" integer primary key autoincrement,"
            +PRIORITY+" text,"
            +CONTENT+" text,"
            +TIME+" text,"
            +ISNOTIFY+" text,"
            +FENLEI+" text)";

//    private String sql= "create table notes (_id integer primary key autoincrement, priority text,content text,time text,isN );"

    public DBHelper(Context context) {
        //创建一个数据库
        super(context, "note.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //数据库中没有表时创建一个表
        db.execSQL(sql);
    }
    //更新数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.execSQL("drop table notes if exits");
        onCreate(db);
    }

    //插入一条数据
    public long insertData(String content ,String priority, String time,Boolean isNotify ,String fenLei){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PRIORITY, priority);
        values.put(CONTENT, content);
        values.put(TIME,time);
        values.put(ISNOTIFY,isNotify+"");
        values.put(FENLEI,fenLei);
        return db.insert(TABLE_NAME, null, values);
    }
    //查询数据，返回一个Cursor
    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from notes",null);
    }
    //根据主键删除某条记录
    public void deleteData(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("notes", "_id=?", new String[]{String.valueOf(id)});

    }
    public Cursor queryNotification() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("select * from notes where isNotifiy='true'",null);
    }

}

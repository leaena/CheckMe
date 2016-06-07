package com.leaena.checkme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class TodoDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TodoTable.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_TODO = "todo";
    public static final String TODO_COLUMN_ITEM = "item";

    private HashMap hp;

    public TodoDBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_TODO + " (id integer primary key, " + TODO_COLUMN_ITEM + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public boolean insertItem(String itemText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_COLUMN_ITEM, itemText);
        db.insert(TABLE_TODO, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select * from " + TABLE_TODO + " where id=" + id, null);
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_TODO);
        return numRows;
    }

    public boolean updateItem(Integer id, String itemText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TODO_COLUMN_ITEM, itemText);
        db.update(TABLE_TODO, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteItem(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TODO,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllItems() {
        ArrayList<String> itemList = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + TABLE_TODO, null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            itemList.add(res.getString(res.getColumnIndex(TODO_COLUMN_ITEM)));
            res.moveToNext();
        }
        return itemList;
    }
}

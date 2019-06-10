package com.aldi.sismul;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "history";
    private static final String column1 = "expression";
    private static final String column2 = "result";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + column1 + " TEXT," + column2 + " TEXT);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public boolean addData(String exp, String result) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column1, exp);
        contentValues.put(column2, result);

        Log.d(TAG, "addData: " + result + " to " + TABLE_NAME);

        long nResult = db.insert(TABLE_NAME, null, contentValues);
        if (nResult == 1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String GET_DATA = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(GET_DATA, null);
        return data;
    }

    public void removeData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String REMOVE_DATA = "DELETE FROM " + TABLE_NAME;
        db.execSQL(REMOVE_DATA);
    }
}

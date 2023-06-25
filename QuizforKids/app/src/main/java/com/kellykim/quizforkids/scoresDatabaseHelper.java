package com.kellykim.quizforkids;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class scoresDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "scores.db";
    public static final String TABLE_NAME = "scores";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "USER_ID";
    public static final String COL_3 = "AREA";
    public static final String COL_4 = "DATE";
    public static final String COL_5 = "SCORE";

    public scoresDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "USER_ID INTEGER, AREA TEXT, DATE TEXT, SCORE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adding a record(score)
    public boolean insertData(String user_id, String area, String date, String score){
        SQLiteDatabase db = this.getWritableDatabase();
        //Create and/or open a database that will be used for reading and writing.
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, user_id);
        contentValues.put(COL_3, area);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, score);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewRecord(int user_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] Cols = new String[] {COL_1, COL_2, COL_3, COL_4, COL_5};
        String selection = COL_2 +  " = ?";
        String[] selectionArgs = new String[]{String.valueOf(user_id)};
        String orderBy = COL_2 + " DESC";
        Cursor res = db.query(
                TABLE_NAME,
                Cols,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );
        //Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME + "WHERE ID=" + id, null);
        return res;
    }

    public Cursor viewAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }
}
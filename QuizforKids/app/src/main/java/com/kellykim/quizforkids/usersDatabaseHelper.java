package com.kellykim.quizforkids;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class usersDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "users.db";
    public static final String TABLE_NAME = "users";

    public static final String COL_1 = "USER_ID";
    public static final String COL_2 = "EMAIL";
    public static final String COL_3 = "USERNAME";
    public static final String COL_4 = "PASSWORD";

    public usersDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "EMAIL TEXT, USERNAME TEXT, PASSWORD TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //adding a record
    public boolean insertData(String email, String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        //Create and/or open a database that will be used for reading and writing.
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, email);
        contentValues.put(COL_3, username);
        contentValues.put(COL_4, password);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor viewRecord(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] Cols = new String[] {COL_1, COL_2, COL_3, COL_4};
        String selection = COL_3 +  " = ?";
        String[] selectionArgs = new String[]{username};
        String orderBy = COL_3 + " DESC";
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
    public Cursor viewRecord_by_id(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] Cols = new String[] {COL_1, COL_2, COL_3, COL_4};
        String selection = COL_1 +  " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String orderBy = COL_3 + " DESC";
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

    public boolean update_pw(String new_pw, String user_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+" SET PASSWORD = "+"'"+new_pw+"' "+ "WHERE USER_ID = "+"'"+user_id+"'");
        return true;
    }
}

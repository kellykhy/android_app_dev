package com.kellykim.quizforkids;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class quizDatabaseHelper extends SQLiteOpenHelper{

    private final Context context;

    public static final String DATABASE_NAME = "quiz.db";
    public static final String TABLE_NAME_1 = "animals";
    public static final String TABLE_NAME_2 = "cartoons";

    public static final String T1_COL_1 = "ID";
    public static final String T1_COL_2 = "PIC";

    public static final String T2_COL_1 = "ID";
    public static final String T2_COL_2 = "QUESTION";
    public static final String T2_COL_3 = "OPT1";
    public static final String T2_COL_4 = "OPT2";
    public static final String T2_COL_5 = "OPT3";
    public static final String T2_COL_6 = "ANSWER"; //value type: integer

    public quizDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME_1 +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "PIC TEXT)");
        // Initialize the animal db with values from xml
        ContentValues values = new ContentValues();
        Resources res = context.getResources();
        String[] animals_array = res.getStringArray(R.array.animals);

        int i = 0;
        while (i < 10){
            values.put(T1_COL_2, animals_array[i++]);
            db.insert(TABLE_NAME_1, null, values);
        }

        // Initializing cartoons db with values from xml
        db.execSQL("CREATE TABLE " + TABLE_NAME_2 +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "QUESTION TEXT, OPT1 TEXT, OPT2 TEXT, OPT3 TEXT, ANSWER INTEGER)");
        String[] cartoons_array = res.getStringArray(R.array.cartoons_questions);
        String[] cartoons_option1 = res.getStringArray(R.array.cartoons_option1);
        String[] cartoons_option2 = res.getStringArray(R.array.cartoons_option2);
        String[] cartoons_option3 = res.getStringArray(R.array.cartoons_option3);
        String[] cartoons_answer = res.getStringArray(R.array.cartoon_answer);
        values = new ContentValues();
        i = 0;
        while (i < 10){
            values.put(T2_COL_2, cartoons_array[i]);
            values.put(T2_COL_3, cartoons_option1[i]);
            values.put(T2_COL_4, cartoons_option2[i]);
            values.put(T2_COL_5, cartoons_option3[i]);
            values.put(T2_COL_6, cartoons_answer[i++]);
            db.insert(TABLE_NAME_2, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_2);
        onCreate(db);
    }

    public Cursor viewRecord_1(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] Cols = new String[] {T1_COL_1, T1_COL_2};
        String selection = T1_COL_1 +  " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String orderBy = T1_COL_1 + " DESC";
        Cursor res = db.query(
                TABLE_NAME_1,
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

    public Cursor viewRecord_2(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] Cols = new String[] {T2_COL_1, T2_COL_2, T2_COL_3, T2_COL_4, T2_COL_5, T2_COL_6};
        String selection = T2_COL_1 +  " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String orderBy = T2_COL_1 + " DESC";
        Cursor res = db.query(
                TABLE_NAME_2,
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
}

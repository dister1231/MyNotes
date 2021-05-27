package com.example.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBExpert extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MyNotes";
    public static final String TABLE = "MyNotesTable";

    public static final String KEY_ID = "_id";
    public static final String KEY_NOTES = "text";
    public static final String KEY_DATES = "date";

    public DBExpert(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NOTES + " TEXT, " + KEY_DATES + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(query);
        onCreate(db);
    }

    public int addRecord(String note, String date) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOTES, note);
        contentValues.put(KEY_DATES, date);
        int result = (int) database.insert(TABLE, null, contentValues);
        database.close();

        return result;
    }

    public void deleteRecord(int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE + " WHERE _id = " + id;
        database.execSQL(query);
        database.close();
    }

    public void upgradeRecord(int id, String newText, String newDate) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_NOTES, newText);
        contentValues.put(KEY_DATES, newDate);
        String whereClause = "_id = " + id;
        database.update(TABLE, contentValues, whereClause, null);
        database.close();
    }

    public Cursor readAllData() {

        SQLiteDatabase database = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE + " ORDER BY " + KEY_ID + " DESC";
        return database.rawQuery(query, null);
    }
}

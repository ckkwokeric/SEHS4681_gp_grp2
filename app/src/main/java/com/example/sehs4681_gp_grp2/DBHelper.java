package com.example.sehs4681_gp_grp2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBName = "gp.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "uid";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_SCORE = "score";

    public DBHelper(Context context) {
        super(context, "gp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_SCORE + " INTEGER DEFAULT 0);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users");
        onCreate(db);
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, values);
        
        if (result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from "+ TABLE_NAME +" where "+ COLUMN_USERNAME +" = ?", new String[] {username});

        if (cursor.getCount() > 0) {
            return true;
        }else {
            return false;
        }
    }

    public int checkusernamepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where " + COLUMN_USERNAME + " = ? and " + COLUMN_PASSWORD + " = ?", new String[]{username, password});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int uid = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            cursor.close();
            return uid;
        } else {
            return -1;
        }
    }

    public Cursor readAllData() {
        String query = "Select * from "+ TABLE_NAME +" order by "+ COLUMN_SCORE +" Desc";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    @SuppressLint("Range")
    public void addScore(int uid, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        // First, retrieve the current score for the user with the given uid
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(uid)});

        int currentScore = 0;
        if (cursor.moveToFirst()) {
            currentScore = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
        }
        cursor.close();

        // Next, update the user's score
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SCORE, currentScore + score);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(uid)});

        if (rowsAffected == 0) {
            Log.e("DatabaseHelper", "Failed to update score for uid: " + uid);
        } else {
            Log.d("DatabaseHelper", "Score updated successfully for uid: " + uid);
        }

        db.close();
    }

}

package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calculator_history.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EXPRESSION = "expression";
    public static final String COLUMN_RESULT = "result";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_HISTORY + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_EXPRESSION + " TEXT NOT NULL, "
            + COLUMN_RESULT + " TEXT NOT NULL, "
            + COLUMN_TIMESTAMP + " TEXT NOT NULL);";

    public HistoryDatabaseHelper(@NonNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
        onCreate(db);
    }
}

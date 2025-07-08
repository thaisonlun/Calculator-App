package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.example.myapplication.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private final HistoryDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public HistoryDAO(@NonNull Context context) {
        dbHelper = new HistoryDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null && database.isOpen()) {
            database.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public long insertHistory(String expression, String result, String timestamp) {
        ContentValues values = new ContentValues();
        values.put(HistoryDatabaseHelper.COLUMN_EXPRESSION, expression);
        values.put(HistoryDatabaseHelper.COLUMN_RESULT, result);
        values.put(HistoryDatabaseHelper.COLUMN_TIMESTAMP, timestamp);
        return database.insert(HistoryDatabaseHelper.TABLE_HISTORY, null, values);
    }

    public List<HistoryItem> getAllHistory() {
        List<HistoryItem> historyList = new ArrayList<>();
        Cursor cursor = database.query(
                HistoryDatabaseHelper.TABLE_HISTORY,
                null,
                null,
                null,
                null,
                null,
                HistoryDatabaseHelper.COLUMN_TIMESTAMP + " DESC"
        );

        try {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COLUMN_ID));
                    String expression = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COLUMN_EXPRESSION));
                    String result = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COLUMN_RESULT));
                    String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(HistoryDatabaseHelper.COLUMN_TIMESTAMP));

                    HistoryItem item = new HistoryItem(id, expression, result, timestamp);
                    historyList.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return historyList;
    }

    public void clearHistory() {
        database.delete(HistoryDatabaseHelper.TABLE_HISTORY, null, null);
    }

    public void deleteHistoryItem(long id) {
        database.delete(HistoryDatabaseHelper.TABLE_HISTORY,
                HistoryDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)});
    }
}

package com.thesis.dogharness.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.MakMoinee.library.services.MSqliteDBHelper;

public class MySqlite extends MSqliteDBHelper {

    public MySqlite(Context context, String dbName) {
        super(context, dbName);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName TEXT NOT NULL, " +
                "middleName TEXT, " +
                "lastName TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL)");
    }
}

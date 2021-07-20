package com.leandro.sysinv.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import static android.content.Context.MODE_PRIVATE;


public class Db {

    private static SQLiteDatabase conn = null;

    public Db() {
    }

    public static SQLiteDatabase getConnection() {

        try {
            if (conn == null) {

                conn = SQLiteDatabase.openOrCreateDatabase("app", null, null);

            }

        } catch(SQLiteException e) {
            throw new DbException(e.getMessage());
        }

        return conn;
    }

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch(SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }
}

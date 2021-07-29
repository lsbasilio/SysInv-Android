package com.leandro.sysinv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class Db {

    private static SQLiteDatabase conn = null;
    private static DadosOpenHelper dadosOpenHelper;

    public Db() {
    }

    public static SQLiteDatabase getConnection(Context context) {

        //SQLiteDatabase bancoDados = openOrCreateDatabase("app", MODE_PRIVATE, null);


        try {
            if (conn == null) {

                dadosOpenHelper = new DadosOpenHelper(context);
                conn = dadosOpenHelper.getWritableDatabase();

                criaTabelas();

                Log.i("Conectou", "ao SqLite");
            }

        } catch(SQLiteException e) {
            throw new DbException(e.getMessage());
        }

        return conn;
    }

    public static void criaTabelas() {

        conn.execSQL(getTableCentroCusto());
        conn.execSQL(getTableLocais());
        conn.execSQL(getTableDescrComplementar());
        conn.execSQL(getTableDescrPadrao());

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

    public static String getTableCentroCusto() {

        return "CREATE TABLE IF NOT EXISTS centrodecusto ( " +
                "CCUSTO_ID INTEGER NOT NULL PRIMARY KEY," +
                "DESCRICAO TEXT," +
                "STATUS INTEGER," +
                "DATA_INICIO TEXT," +
                "DATA_FIM TEXT," +
                "PENDENTES INTEGER," +
                "INVENTARIADOS INTEGER," +
                "NOVOS INTEGER);";
    }

    public static String getTableLocais() {
        return "CREATE TABLE IF NOT EXISTS locais ( " +
               "LOCAL_ID INTEGER NOT NULL PRIMARY KEY," +
               "DESCRICAO TEXT);";
    }

    public static String getTableDescrComplementar() {
        return "CREATE TABLE IF NOT EXISTS descrcomplementar ( " +
               "DESCRICAO_ID TEXT NOT NULL PRIMARY KEY," +
               "DESCRICAO TEXT);";
    }

    public static String getTableDescrPadrao() {
        return "CREATE TABLE IF NOT EXISTS descrpadrao ( " +
                "DESCRICAO_ID TEXT NOT NULL PRIMARY KEY," +
                "DESCRICAO TEXT);";
    }
}

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

        try {
            if (conn == null) {

                dadosOpenHelper = new DadosOpenHelper(context);
                conn = dadosOpenHelper.getWritableDatabase();

                criaTabelas();

                //Log.i("Conectou", "ao SqLite");
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
        conn.execSQL(getTableBens());

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

    public static String getTableBens() {
        return  "CREATE TABLE IF NOT EXISTS bens (" +
                "NUMERO_BEM INTEGER NOT NULL PRIMARY KEY," +
                "CCUSTO_ID INTEGER," +
                "STATUS INTEGER,"+
                "DATA_INV TEXT,"+
                "CONTA INTEGER,"+
                "DATA TEXT,"+
                "OBSERVACAO TEXT,"+
                "LOCAL_ID INTEGER,"+
                "USUARIO TEXT,"+
                "DESCRICAO TEXT,"+
                "MARCA TEXT,"+
                "MODELO TEXT,"+
                "NUMERO_SERIE TEXT,"+
                "SITUACAO TEXT,"+
                "NUMERO_BEMANT INTEGER,"+
                "CCUSTO_ANT INTEGER,"+
                "LOCAL_ANT INTEGER,"+
                "DESCRICAO_ANT TEXT,"+
                "MARCA_ANT TEXT,"+
                "MODELO_ANT TEXT,"+
                "NUMERO_SERIEANT TEXT,"+
                "SITUACAO_ANT TEXT,"+
                "FOREIGN KEY (CCUSTO_ID) REFERENCES CENTRODECUSTO(CCUSTO_ID),"+
                "FOREIGN KEY (LOCAL_ID) REFERENCES LOCAIS(LOCAL_ID));";
    }
}

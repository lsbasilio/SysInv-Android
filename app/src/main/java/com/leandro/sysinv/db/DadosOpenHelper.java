package com.leandro.sysinv.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.Nullable;


public class DadosOpenHelper extends SQLiteOpenHelper {

    //private static final String DATABASE_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Inventario/001/InventarioBD";
    private static final String DATABASE_NAME = "InventarioBD";

    public DadosOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*try {

            Log.i("Teste!", "Passou aqui");

            db.execSQL(Db.getTableCentroCusto());
            db.execSQL(Db.getTableLocais());
            db.execSQL(Db.getTableDescrComplementar());
            db.execSQL(Db.getTableDescrPadrao());

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

package com.leandro.sysinv.model.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.model.dao.LocaisDao;
import com.leandro.sysinv.model.entities.Local;

import java.util.ArrayList;
import java.util.List;

public class LocaisSqLite implements LocaisDao {

    private SQLiteDatabase conn;
    private String strSQL;

    public LocaisSqLite(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void insert(Local obj) {

        try {

            strSQL = "INSERT INTO locais";
            strSQL += "(Local_id,Descricao) ";
            strSQL += " VALUES ";
            strSQL += "(" + obj.getLocal_id() + ", '" + obj.getDescricao() + "')";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }

    }

    public void update(Local obj) {
        try {

            strSQL = "UPDATE locais SET ";
            strSQL += "Descricao = '" + obj.getDescricao() + "' ";
            strSQL += " WHERE Local_id = " + obj.getLocal_id();

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {

            strSQL = "DELETE FROM locais WHERE Local_id = " + id;

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public Local findById(Integer id) {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM locais WHERE Local_Id = " + id, null);

            if (cursor.moveToFirst()) {
                return instantiateLocal(cursor);
            }

            return null;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public List<Local> findAll() {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM locais ORDER BY descricao", null);

            List<Local> list = new ArrayList<>();

            while (cursor.moveToNext()) {
                Local local = instantiateLocal(cursor);
                list.add(local);
            }

            return list;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteAll() {
        try {

            strSQL = "DELETE FROM locais";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    private Local instantiateLocal(Cursor cur) throws SQLiteException {

        Local localTemp = new Local();
        localTemp.setLocal_id(cur.getInt(0));
        localTemp.setDescricao(cur.getString(1));

        return localTemp;
    }

}

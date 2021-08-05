package com.leandro.sysinv.model.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.model.dao.DescrComplementarDao;
import com.leandro.sysinv.model.entities.DescrComplementar;

import java.util.ArrayList;
import java.util.List;

public class DescrComplementarSqLite implements DescrComplementarDao {

    private SQLiteDatabase conn;
    private String strSQL;

    public DescrComplementarSqLite(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void insert(DescrComplementar obj) {

        try {

            strSQL = "INSERT INTO descrcomplementar";
            strSQL += "(Descricao_id,Descricao) ";
            strSQL += " VALUES ";
            strSQL += "('" + obj.getDescricao_id() + "', '" + obj.getDescricao() + "')";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }

    }

    public void update(DescrComplementar obj) {
        try {

            strSQL = "UPDATE descrcomplementar SET ";
            strSQL += "Descricao = '" + obj.getDescricao() + "' ";
            strSQL += "WHERE Descricao_id = '" + obj.getDescricao_id() + "'";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {

            strSQL = "DELETE FROM descrcomplementar WHERE Descricao_id = '" + id + "'";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public DescrComplementar findById(String id) {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM descrcomplementar WHERE Descricao_Id = '" + id + "'", null);

            if (cursor.moveToFirst()) {
                return instantiateDesrComplementar(cursor);
            }

            return null;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public List<DescrComplementar> findAll() {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM descrcomplementar ORDER BY descricao", null);

            List<DescrComplementar> list = new ArrayList<>();

            while (cursor.moveToNext()) {
                DescrComplementar descr = instantiateDesrComplementar(cursor);
                list.add(descr);
            }

            return list;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteAll() {
        try {

            strSQL = "DELETE FROM descrcomplementar";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    private DescrComplementar instantiateDesrComplementar(Cursor cur) throws SQLiteException {

        DescrComplementar descrTemp = new DescrComplementar();
        descrTemp.setDescricao_id(cur.getString(0));
        descrTemp.setDescricao(cur.getString(1));

        return descrTemp;
    }

}

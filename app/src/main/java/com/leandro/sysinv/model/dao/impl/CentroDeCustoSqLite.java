package com.leandro.sysinv.model.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.model.dao.CentroDeCustoDao;
import com.leandro.sysinv.model.entities.CentroDeCusto;
import com.leandro.sysinv.model.entities.enums.CcustoStatus;

import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CentroDeCustoSqLite implements CentroDeCustoDao {

    private SQLiteDatabase conn;
    private String strSQL;

    public CentroDeCustoSqLite(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void insert(CentroDeCusto obj) {

        try {

            strSQL = "INSERT INTO centrodecusto VALUES ";
            strSQL += "(Ccusto_id,Descricao,Status,Data_Inicio,Data_Fim,Pendentes,Inventariados,Novos) ";
            strSQL += " VALUES ";
            strSQL += "(" + obj.getCcusto_id() + ", '" + obj.getDescricao() + "', " + obj.getStatusNumerico() + ", '" + obj.getData_inicio() + "','";
            strSQL += obj.getData_fim() + "', " + obj.getPendentes() + ", " + obj.getInventariados() + ", " + obj.getNovos() + ")";

            conn.execSQL(strSQL);

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }

    }

    public void update(CentroDeCusto obj) {
        try {

            strSQL = "UPDATE centrodecusto SET ";
            strSQL += "Descricao = '" + obj.getDescricao() + "', ";
            strSQL += "Status = " + obj.getStatusNumerico() + ", ";
            strSQL += "Data_Inicio = '" + obj.getData_inicio() + "', ";
            strSQL += "Data_Fim = '" + obj.getData_fim() + "', ";
            strSQL += "Pendentes = " + obj.getPendentes() + ", ";
            strSQL += "Inventariados = " + obj.getInventariados() + ", ";
            strSQL += "Novos = " + obj.getNovos();
            strSQL += " WHERE Ccusto_id = " + obj.getCcusto_id();

            conn.execSQL(strSQL);

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {

            strSQL = "DELETE FROM centrodecusto WHERE Ccusto_id = " + id;

            conn.execSQL(strSQL);

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    public CentroDeCusto findById(Integer id) {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM centrodecusto WHERE Ccusto_Id = " + id, null);

            if (cursor.moveToNext()) {
               instantiateCentroDeCusto(cursor);
            }

            return null;

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    public List<CentroDeCusto> findAll() {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM centrodecusto ORDER BY descricao", null);

            List<CentroDeCusto> list = new ArrayList<>();

            while (cursor.moveToNext()) {
                CentroDeCusto ccusto = instantiateCentroDeCusto(cursor);
                list.add(ccusto);
            }

            return list;

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteAll() {
        try {

            strSQL = "DELETE FROM centrodecusto";

            conn.execSQL(strSQL);

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    private CentroDeCusto instantiateCentroDeCusto(Cursor cur) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        CentroDeCusto ccustoTemp = new CentroDeCusto();
        ccustoTemp.setCcusto_id(cur.getInt(0));
        ccustoTemp.setDescricao(cur.getString(1));
        ccustoTemp.setStatus(ccustoTemp.getStatusCodigo(cur.getInt(2)));
        ccustoTemp.setData_inicio(sdf.parse(cur.getString(3)));
        ccustoTemp.setData_fim(sdf.parse(cur.getString(4)));
        ccustoTemp.setPendentes(cur.getInt(5));
        ccustoTemp.setInventariados(cur.getInt(6));
        ccustoTemp.setNovos(cur.getInt(7));

        return ccustoTemp;
    }

}

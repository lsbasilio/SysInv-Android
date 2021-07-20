package com.leandro.sysinv.model.dao.impl;

import android.database.sqlite.SQLiteDatabase;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.model.dao.CentroDeCustoDao;
import com.leandro.sysinv.model.entities.CentroDeCusto;

import java.sql.PreparedStatement;

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
            strSQL += "(" + obj.getCcusto_id() + ", '" + obj.getDescricao() + "', " + obj.getStatus() + ", '" + obj.getData_inicio() + "','";
            strSQL += obj.getData_fim() + "', " + obj.getPendentes() + ", " + obj.getInventariados() + ", " + obj.getNovos() + ")";

            conn.execSQL(strSQL);

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }

    }

}

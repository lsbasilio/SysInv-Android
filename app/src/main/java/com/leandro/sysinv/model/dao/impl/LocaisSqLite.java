package com.leandro.sysinv.model.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.importacao.ArqException;
import com.leandro.sysinv.importacao.Arquivos;
import com.leandro.sysinv.model.dao.LocaisDao;
import com.leandro.sysinv.model.entities.DescrPadrao;
import com.leandro.sysinv.model.entities.Local;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocaisSqLite implements LocaisDao {

    private SQLiteDatabase conn;
    private String strSQL;
    private String strSelectSQL = "SELECT l.local_id,  l.descricao, " +
                                  "(SELECT COUNT(*) FROM bens b WHERE l.local_id = b.local_id) AS total_bens " +
                                  "FROM " +
                                  "locais l ";
    private String strWhereSQL   = "WHERE l.local_id = ? ";
    private String strWhereDescricaoSQL   = "";
    private String strGroupBySQL = "GROUP BY 1,2 ";
    private String strOrderBySQL = "ORDER BY descricao";

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

            //Cursor cursor = conn.rawQuery("SELECT * FROM locais WHERE Local_Id = " + id, null);
            strSQL = strSelectSQL + strWhereSQL + strGroupBySQL + strOrderBySQL;
            Cursor cursor = conn.rawQuery(strSQL,  new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                return instantiateLocal(cursor);
            }

            return null;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public List<Local> findByDescricao(String descricao) {

        try {

            //Cursor cursor = conn.rawQuery("SELECT * FROM locais ORDER BY descricao", null);
            //strSQL = strSelectSQL + strWhereDescricaoSQL + strGroupBySQL + strOrderBySQL;
            descricao = "'%" + descricao + "%'";
            strWhereDescricaoSQL = "WHERE l.descricao like " + descricao;
            strSQL = strSelectSQL + strWhereDescricaoSQL + strGroupBySQL + strOrderBySQL;

            //Cursor cursor = conn.rawQuery(strSQL, new String[]{ descricao });
            Cursor cursor = conn.rawQuery(strSQL, null);

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

    public List<Local> findAll() {

        try {

            //Cursor cursor = conn.rawQuery("SELECT * FROM locais ORDER BY descricao", null);
            strSQL = strSelectSQL + strGroupBySQL + strOrderBySQL;

            Cursor cursor = conn.rawQuery(strSQL, null);

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
        localTemp.setTotalBens(cur.getInt(2));

        return localTemp;
    }

    public void carregaArquivoCsv(String empresa, Context contexto) throws IOException {

        String nomeArquivo = Arquivos.ARQUIVO_LOCAL;
        String codigo, descricao;
        String nomePastaDados = contexto.getFilesDir().getPath();

        Local localTemp = new Local();

        Arquivos.pathInventario = nomePastaDados + "/" + Arquivos.NOME_PASTA;

        if (!Arquivos.pathInventarioExiste(empresa)) {
            throw new ArqException("Diret??rio da Importa????o n??o existe!");
        } else if (!Arquivos.fileInventarioExiste(empresa, nomeArquivo)) {
            throw new ArqException("Arquivo de Importa????o dos Locais n??o encontrado!");
        } else {

            nomeArquivo = Arquivos.pathInventario + "/" + empresa + "/" + nomeArquivo;

            FileReader arq = new FileReader(nomeArquivo);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();

            this.deleteAll();

            try {

                while (linha != null) {

                    String dados[] = linha.split(";");

                    codigo = dados[0];
                    descricao = dados[1];

                    localTemp.setLocal_id(Integer.parseInt(codigo));
                    localTemp.setDescricao(descricao);

                    this.insert(localTemp);

                    linha = lerArq.readLine();
                }

            } finally {
                arq.close();
            }
        }
    }

}

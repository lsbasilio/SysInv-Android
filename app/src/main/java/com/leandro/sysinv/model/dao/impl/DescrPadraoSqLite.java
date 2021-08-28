package com.leandro.sysinv.model.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.importacao.ArqException;
import com.leandro.sysinv.importacao.Arquivos;
import com.leandro.sysinv.model.dao.DescrPadraoDao;
import com.leandro.sysinv.model.entities.DescrPadrao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DescrPadraoSqLite implements DescrPadraoDao {

    private SQLiteDatabase conn;
    private String strSQL;

    public DescrPadraoSqLite(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void insert(DescrPadrao obj) {

        try {

            strSQL = "INSERT INTO descrpadrao";
            strSQL += "(Descricao_id,Descricao) ";
            strSQL += " VALUES ";
            strSQL += "('" + obj.getDescricao_id() + "', '" + obj.getDescricao() + "')";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }

    }

    public void update(DescrPadrao obj) {
        try {

            strSQL = "UPDATE descrpadrao SET ";
            strSQL += "Descricao = '" + obj.getDescricao() + "' ";
            strSQL += "WHERE Descricao_id = '" + obj.getDescricao_id() + "'";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(String id) {
        try {

            strSQL = "DELETE FROM descrpadrao WHERE Descricao_id = '" + id + "'";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public DescrPadrao findById(String id) {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM descrpadrao WHERE Descricao_Id = '" + id + "'", null);

            if (cursor.moveToFirst()) {
                return instantiateDescrPadrao(cursor);
            }

            return null;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public List<DescrPadrao> findAll() {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM descrpadrao ORDER BY descricao", null);

            List<DescrPadrao> list = new ArrayList<>();

            while (cursor.moveToNext()) {
                DescrPadrao descr = instantiateDescrPadrao(cursor);
                list.add(descr);
            }

            return list;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteAll() {
        try {

            strSQL = "DELETE FROM descrpadrao";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }


    private DescrPadrao instantiateDescrPadrao(Cursor cur) throws SQLiteException {

        DescrPadrao descrTemp = new DescrPadrao();
        descrTemp.setDescricao_id(cur.getString(0));
        descrTemp.setDescricao(cur.getString(1));

        return descrTemp;
    }


    public void carregaArquivoCsv(String empresa, Context contexto) throws IOException {

        String nomeArquivo = "DescrPadrao.csv";
        String codigo, descricao;
        String nomePastaDados = contexto.getFilesDir().getPath();

        DescrPadrao descrTemp = new DescrPadrao();

        Arquivos.pathInventario = nomePastaDados + "/" + Arquivos.nomePasta;

        if (!Arquivos.pathInventarioExiste(empresa)) {
            throw new ArqException("Diretório da Importação não existe!");
        } else if (!Arquivos.fileInventarioExiste(empresa, nomeArquivo)) {
                throw new ArqException("Arquivo de Importação da Descrição Padrão não encontrado!");
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

                    descrTemp.setDescricao_id(codigo);
                    descrTemp.setDescricao(descricao);

                    this.insert(descrTemp);

                    linha = lerArq.readLine();
                }

            } finally {
                arq.close();
            }
        }
    }
}

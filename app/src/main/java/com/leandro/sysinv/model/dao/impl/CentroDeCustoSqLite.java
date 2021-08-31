package com.leandro.sysinv.model.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.importacao.ArqException;
import com.leandro.sysinv.importacao.Arquivos;
import com.leandro.sysinv.model.dao.CentroDeCustoDao;
import com.leandro.sysinv.model.entities.CentroDeCusto;
import com.leandro.sysinv.model.entities.enums.CcustoStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.leandro.sysinv.util.Util.DateToStr;
import static com.leandro.sysinv.util.Util.TryParseToDate;

public class CentroDeCustoSqLite implements CentroDeCustoDao {

    private SQLiteDatabase conn;
    private String strSQL;

    public CentroDeCustoSqLite(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void insert(CentroDeCusto obj) {

        try {

            strSQL = "INSERT INTO centrodecusto";
            strSQL += "(Ccusto_id,Descricao,Status,Data_Inicio,Data_Fim,Pendentes,Inventariados,Novos) ";
            strSQL += " VALUES ";
            strSQL += "(" + obj.getCcusto_id() + ", '" + obj.getDescricao() + "', " + obj.getStatusNumerico() + ", '" + DateToStr(obj.getData_inicio()) + "','";
            strSQL += DateToStr(obj.getData_fim()) + "', " + obj.getPendentes() + ", " + obj.getInventariados() + ", " + obj.getNovos() + ")";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }

    }

    public void update(CentroDeCusto obj) {
        try {

            strSQL = "UPDATE centrodecusto SET ";
            strSQL += "Descricao = '" + obj.getDescricao() + "', ";
            strSQL += "Status = " + obj.getStatusNumerico() + ", ";
            strSQL += "Data_Inicio = '" + DateToStr(obj.getData_inicio()) + "', ";
            strSQL += "Data_Fim = '" + DateToStr(obj.getData_fim()) + "', ";
            strSQL += "Pendentes = " + obj.getPendentes() + ", ";
            strSQL += "Inventariados = " + obj.getInventariados() + ", ";
            strSQL += "Novos = " + obj.getNovos();
            strSQL += " WHERE Ccusto_id = " + obj.getCcusto_id();

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {
        try {

            strSQL = "DELETE FROM centrodecusto WHERE Ccusto_id = " + id;

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public CentroDeCusto findById(Integer id) {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM centrodecusto WHERE Ccusto_Id = " + id, null);

            if (cursor.moveToFirst()) {
              return instantiateCentroDeCusto(cursor);
            }

            return null;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public CentroDeCusto findCcustoAtivo() {
        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM centrodecusto WHERE Status = " + Integer.toString(CcustoStatus.ATIVO.ordinal() + 1), null);

            if (cursor.moveToFirst()) {
                return instantiateCentroDeCusto(cursor);
            }

            return null;

        } catch (SQLiteException e) {
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

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteAll() {
        try {

            strSQL = "DELETE FROM centrodecusto";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    private CentroDeCusto instantiateCentroDeCusto(Cursor cur) throws SQLiteException {

        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        CentroDeCusto ccustoTemp = new CentroDeCusto();
        ccustoTemp.setCcusto_id(cur.getInt(0));
        ccustoTemp.setDescricao(cur.getString(1));
        ccustoTemp.setStatus(ccustoTemp.getStatusCodigo(cur.getInt(2)));
        ccustoTemp.setData_inicio(TryParseToDate(cur.getString(3)));
        ccustoTemp.setData_fim(TryParseToDate(cur.getString(4)));
        ccustoTemp.setPendentes(cur.getInt(5));
        ccustoTemp.setInventariados(cur.getInt(6));
        ccustoTemp.setNovos(cur.getInt(7));

        return ccustoTemp;
    }

    public void carregaArquivoCsv(String empresa, Context contexto) throws IOException {

        String nomeArquivo = Arquivos.ARQUIVO_CCUSTO;
        String codigo, descricao;
        String nomePastaDados = contexto.getFilesDir().getPath();

        CentroDeCusto ccustoTemp = new CentroDeCusto();

        Arquivos.pathInventario = nomePastaDados + "/" + Arquivos.NOME_PASTA;

        if (!Arquivos.pathInventarioExiste(empresa)) {
            throw new ArqException("Diretório da Importação não existe!");
        } else if (!Arquivos.fileInventarioExiste(empresa, nomeArquivo)) {
            throw new ArqException("Arquivo de Importação do Centro de Custo não encontrado!");
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

                    ccustoTemp.setCcusto_id(Integer.parseInt(codigo));
                    ccustoTemp.setDescricao(descricao);

                    this.insert(ccustoTemp);

                    linha = lerArq.readLine();
                }

            } finally {
                arq.close();
            }
        }
    }

}

package com.leandro.sysinv.model.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.leandro.sysinv.db.DbException;
import com.leandro.sysinv.importacao.ArqException;
import com.leandro.sysinv.importacao.Arquivos;
import com.leandro.sysinv.model.dao.BensDao;
import com.leandro.sysinv.model.entities.Bens;
import com.leandro.sysinv.model.entities.CentroDeCusto;
import com.leandro.sysinv.model.entities.Local;
import com.leandro.sysinv.model.entities.enums.BensStatus;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.leandro.sysinv.util.Util.DateToStr;
import static com.leandro.sysinv.util.Util.TryParseToDate;

public class BensSqLite implements BensDao {

    private SQLiteDatabase conn;
    private String strSQL;

    public BensSqLite(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void insert(Bens obj) {

        try {

            strSQL = "INSERT INTO bens ";
            strSQL += "(Numero_Bem,Numero_BemAnt,Ccusto_Id,Status,Data_Inv,Conta,Data,Observacao,Local_Id,Usuario,Descricao,Marca,Modelo,Numero_Serie,Situacao)";
            strSQL += " VALUES ";
            strSQL += "(" + obj.getNumero_bem() + ", " + obj.getNumero_bemant() + ", " + obj.getCcusto_atual().getCcusto_id() + ", " + obj.getStatusNumerico() + ", '" + DateToStr(obj.getData_inv()) + "', ";
            strSQL += obj.getConta() + ", '" + DateToStr(obj.getData()) + "', '" + obj.getObservacao() + "', " + obj.getLocal_atual().getLocal_id();
            strSQL += ", '" + obj.getUsuario() + "', '" + obj.getDescricao() + "', '" + obj.getMarca() + "', '" + obj.getModelo() + "', '" + obj.getNumero_serie() + "','" + obj.getSituacao() + "')";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }

    }

    public void update(Bens obj) {

        try {

            strSQL = "UPDATE bens ";
            strSQL += "SET Ccusto_Id = " + obj.getCcusto_atual().getCcusto_id() + ",";
            strSQL += "Numero_Bem = " + obj.getNumero_bem() + ", ";
            strSQL += "Numero_BemAnt = " + obj.getNumero_bemant() + ", ";
            strSQL += "Ccusto_Id = " + obj.getCcusto_atual().getCcusto_id() + ", ";
            strSQL += "Status = " + obj.getStatusNumerico() + ", ";
            strSQL += "Data_Inv = '" + DateToStr(obj.getData_inv()) + "', ";
            strSQL += "Conta = " + obj.getConta() + ", ";
            strSQL += "Data = '" + DateToStr(obj.getData()) + "', ";
            strSQL += "Observacao = '" + obj.getObservacao() + "', ";
            strSQL += "Local_Id = " + obj.getLocal_atual().getLocal_id() + ", ";
            strSQL += "Usuario = '" + obj.getUsuario() + "', ";
            strSQL += "Descricao = '" + obj.getDescricao() + "', ";
            strSQL += "Marca = '" + obj.getMarca() + "', ";
            strSQL += "Modelo = '" + obj.getModelo() + "', ";
            strSQL += "Numero_Serie = '" + obj.getNumero_serie() + "', ";
            strSQL += "Situacao = '" + obj.getSituacao() + "' ";

            strSQL += " WHERE Numero_Bem = " + obj.getNumero_bem() + " OR Numero_Bem = " + obj.getNumero_bemant();

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void cancelar(Bens obj) {
        try {

            strSQL = "UPDATE bens ";
            strSQL += "SET Ccusto_Id = " + obj.getCcusto_ant() + ", ";
            if (obj.getNumero_bemant() > 0) {
                strSQL += "Numero_Bem = " + obj.getNumero_bemant() + ", ";
                strSQL += "Numero_BemAnt = 0, ";
            }
            strSQL += "Status = " + obj.getStatusNumerico() + ", ";
            strSQL += "Data_Inv = '" + DateToStr(obj.getData_inv()) + "', ";
            strSQL += "Observacao = '" + obj.getObservacao() + "', ";
            strSQL += "Local_Id = " + obj.getLocal_ant() + ", ";
            strSQL += "Usuario = '" + obj.getUsuario() + "', ";
            strSQL += "Descricao = '" + obj.getDescricao_ant() + "', ";
            strSQL += "Marca = '" + obj.getMarca_ant() + "', ";
            strSQL += "Modelo = '" + obj.getModelo_ant() + "', ";
            strSQL += "Numero_Serie = '" + obj.getNumero_serieant() + "', ";
            strSQL += "Situacao = '" + obj.getSituacao_ant() + "' ";

            strSQL += " WHERE Numero_Bem = " + obj.getNumero_bem();

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public boolean existe(Integer id) {

        try {

            Cursor cursor = conn.rawQuery("SELECT * FROM bens WHERE Numero_Bem = " + id, null);

            if (cursor.moveToFirst())
                return true;
            else
                return false;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteById(Integer id) {

        try {

            strSQL = "DELETE FROM bens";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public Bens findById(Integer id) {

        try {

            Cursor cursor = conn.rawQuery("SELECT Bens.*, centrodecusto.DESCRICAO As DescCCusto, locais.DESCRICAO As DescLocais " +
                    "FROM Bens LEFT JOIN centrodecusto " +
                    "ON Bens.CCUSTO_ID = centrodecusto.CCUSTO_ID " +
                    "LEFT JOIN locais ON Bens.LOCAL_ID = locais.LOCAL_ID " +
                    "WHERE Numero_Bem = " + id, null);

            //int indiceCodCcustoAnt = cursor.getColumnIndex("CCUSTO_ANT");

            if (cursor.moveToFirst()) {

                CentroDeCusto ccusto = instantiateCentroDeCusto(cursor);
                /*CentroDeCusto ccustoAnt = null;

                // Centro de Custo Anterior
                if (!cursor.isNull(indiceCodCcustoAnt))
                    ccustoAnt = instantiateCentroDeCustoAnt(cursor.getInt(indiceCodCcustoAnt));*/

                Local local = instantiateLocal(cursor);
                return instantiateBem(cursor, ccusto, local);
            }

            return null;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public List<Bens> findAll() {
        try {

            Cursor cursor = conn.rawQuery("SELECT Bens.*, centrodecusto.DESCRICAO As DescCCusto, locais.DESCRICAO As DescLocais " +
                            "FROM Bens LEFT JOIN centrodecusto " +
                            "ON Bens.CCUSTO_ID = centrodecusto.CCUSTO_ID " +
                            "LEFT JOIN locais ON Bens.LOCAL_ID = locais.LOCAL_ID ORDER BY Numero_Bem", null);

            int indiceCodCcusto = cursor.getColumnIndex("CCUSTO_ID");
            int indiceLocal = cursor.getColumnIndex("LOCAL_ID");

            List<Bens> list = new ArrayList<>();
            Map<Integer, CentroDeCusto> map = new HashMap<>();
            Map<Integer, Local> mapLocal = new HashMap<>();

            while (cursor.moveToNext()) {

                CentroDeCusto ccusto = map.get(cursor.getInt(indiceCodCcusto));

                if (ccusto == null) {
                    ccusto = instantiateCentroDeCusto(cursor);
                    map.put(cursor.getInt(indiceCodCcusto), ccusto);
                }

                Local local = mapLocal.get(cursor.getInt(indiceLocal));

                if (local == null) {
                    local = instantiateLocal(cursor);
                    mapLocal.put(cursor.getInt(indiceLocal), local);
                }

                Bens bem = instantiateBem(cursor, ccusto, local);
                list.add(bem);
            }

            return list;

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    public void deleteAll() {
        try {

            strSQL = "DELETE FROM bens";

            conn.execSQL(strSQL);

        } catch (SQLiteException e) {
            throw new DbException(e.getMessage());
        }
    }

    private CentroDeCusto instantiateCentroDeCusto(Cursor cur) throws SQLiteException {

        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        int indiceCodCcusto = cur.getColumnIndex("CCUSTO_ID");
        int indiceDescCcusto = cur.getColumnIndex("DescCCusto");
        //int indiceDescLocais = cur.getColumnIndex("DescLocais");

        //Log.i(cur.getColumnName(1), "teste");

        CentroDeCusto ccustoTemp = new CentroDeCusto();
        ccustoTemp.setCcusto_id(cur.getInt(indiceCodCcusto));
        ccustoTemp.setDescricao(cur.getString(indiceDescCcusto));

        return ccustoTemp;
    }

 /*   private CentroDeCusto instantiateCentroDeCustoAnt(int id) throws SQLiteException {

        CentroDeCustoSqLite ccustoDAO = new CentroDeCustoSqLite(conn);

        CentroDeCusto ccustoTemp = ccustoDAO.findById(id);

        if (ccustoTemp != null) {
            ccustoTemp.setCcusto_id(id);
            ccustoTemp.setDescricao(ccustoTemp.getDescricao());
        }

        return ccustoTemp;
    }*/

    private Local instantiateLocal(Cursor cur) throws SQLiteException {

        int indiceCodLocal = cur.getColumnIndex("LOCAL_ID");
        int indiceDescLocal = cur.getColumnIndex("DescLocais");

        Local localTemp = new Local();
        localTemp.setLocal_id(cur.getInt(indiceCodLocal));
        localTemp.setDescricao(cur.getString(indiceDescLocal));

        return localTemp;
    }


    private Bens instantiateBem(Cursor cur, CentroDeCusto ccusto, Local local) throws SQLiteException {

        Bens bemTemp = new Bens();
        bemTemp.setNumero_bem(cur.getInt(0));

        // Centro de Custo Atual
        bemTemp.setCcusto_atual(ccusto);

        bemTemp.getStatusCodigo(cur.getInt(2));
        bemTemp.setData_inv(TryParseToDate(cur.getString(3)));
        bemTemp.setConta(cur.getInt(4));
        bemTemp.setData(TryParseToDate(cur.getString(5)));
        bemTemp.setObservacao(cur.getString(6));

        // Local Atual
        bemTemp.setLocal_atual(local);

        bemTemp.setUsuario(cur.getString(8));
        bemTemp.setDescricao(cur.getString(9));
        bemTemp.setMarca(cur.getString(10));
        bemTemp.setModelo(cur.getString(11));
        bemTemp.setNumero_serie(cur.getString(12));
        bemTemp.setSituacao(cur.getString(13));
        bemTemp.setNumero_bemant(cur.getInt(14));

       //if (bemTemp.getCcusto_ant() != null)

        /*if (cur.getInt(16) != 0) {
           // bemTemp.setLocal_ant(locaisDAO.findById(cur.getInt(16)));
        }*/
         //   bemTemp.getLocal_ant().setLocal_id(cur.getInt(16));
        bemTemp.setDescricao_ant(cur.getString(17));
        bemTemp.setMarca_ant(cur.getString(18));
        bemTemp.setModelo_ant(cur.getString(19));
        bemTemp.setNumero_serieant(cur.getString(20));
        bemTemp.setSituacao_ant(cur.getString(21));

        return bemTemp;

    }

    public void carregaArquivoCsv(String empresa, Context contexto) throws IOException {

        String nomeArquivo = Arquivos.ARQUIVO_BENS;
        String codigo, descricao;
        String nomePastaDados = contexto.getFilesDir().getPath();

        Bens bensTemp = new Bens();
        CentroDeCusto ccustoTemp = new CentroDeCusto();
        Local localTemp = new Local();

        Arquivos.pathInventario = nomePastaDados + "/" + Arquivos.NOME_PASTA;

        if (!Arquivos.pathInventarioExiste(empresa)) {
            throw new ArqException("Diretório da Importação não existe!");
        } else if (!Arquivos.fileInventarioExiste(empresa, nomeArquivo)) {
            throw new ArqException("Arquivo de Importação dos Bens não encontrado!");
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
                    //descricao = dados[1];

                    bensTemp.setNumero_bem(Integer.parseInt(codigo));
                    ccustoTemp.setCcusto_id(Integer.parseInt(dados[1]));
                    bensTemp.setCcusto_atual(ccustoTemp);
                    localTemp.setLocal_id(Integer.parseInt(dados[2]));
                    bensTemp.setLocal_atual(localTemp);
                    bensTemp.setDescricao(dados[3]);
                    bensTemp.setMarca(dados[4]);
                    bensTemp.setModelo(dados[5]);
                    bensTemp.setNumero_serie(dados[6]);
                    bensTemp.setConta(Integer.parseInt(dados[7]));
                    bensTemp.setSituacao(dados[8]);
                    bensTemp.setStatus(BensStatus.PENDENTE);

                    this.insert(bensTemp);

                    linha = lerArq.readLine();
                }

            } finally {
                arq.close();
            }
        }
    }
}

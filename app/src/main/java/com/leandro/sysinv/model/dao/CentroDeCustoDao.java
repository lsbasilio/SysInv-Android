package com.leandro.sysinv.model.dao;

import android.content.Context;

import com.leandro.sysinv.model.entities.CentroDeCusto;

import java.io.IOException;
import java.util.List;

public interface CentroDeCustoDao {

    void insert(CentroDeCusto obj);
    void update(CentroDeCusto obj);
    void deleteById(Integer id);
    CentroDeCusto findById(Integer id);
    CentroDeCusto findCcustoAtivo();
    List<CentroDeCusto> findAll();
    void deleteAll();
    void carregaArquivoCsv(String empresa, Context contexto) throws IOException;
}

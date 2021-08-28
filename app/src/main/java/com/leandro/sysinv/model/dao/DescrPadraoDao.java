package com.leandro.sysinv.model.dao;

import android.content.Context;

import com.leandro.sysinv.model.entities.DescrPadrao;

import java.io.IOException;
import java.util.List;

public interface DescrPadraoDao {

    void insert(DescrPadrao obj);
    void update(DescrPadrao obj);
    void deleteById(String id);
    DescrPadrao findById(String id);
    List<DescrPadrao> findAll();
    void deleteAll();
    void carregaArquivoCsv(String empresa, Context contexto) throws IOException;

}

package com.leandro.sysinv.model.dao;

import android.content.Context;

import com.leandro.sysinv.model.entities.DescrComplementar;

import java.io.IOException;
import java.util.List;

public interface DescrComplementarDao {

    void insert(DescrComplementar obj);
    void update(DescrComplementar obj);
    void deleteById(String id);
    DescrComplementar findById(String id);
    List<DescrComplementar> findAll();
    void deleteAll();
    void carregaArquivoCsv(String empresa, Context contexto) throws IOException;

}

package com.leandro.sysinv.model.dao;

import com.leandro.sysinv.model.entities.CentroDeCusto;

import java.util.List;

public interface CentroDeCustoDao {

    void insert(CentroDeCusto obj);
    void update(CentroDeCusto obj);
    void deleteById(Integer id);
    CentroDeCusto findById(Integer id);
    List<CentroDeCusto> findAll();
    void deleteAll();
}

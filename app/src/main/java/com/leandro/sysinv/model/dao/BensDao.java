package com.leandro.sysinv.model.dao;

import com.leandro.sysinv.model.entities.Bens;

import java.util.List;

public interface BensDao {

    void insert(Bens obj);
    void update(Bens obj);
    void cancelar(Bens obj);
    boolean existe(Integer id);
    void deleteById(Integer id);
    Bens findById(Integer id);
    List<Bens> findAll();
    void deleteAll();

}

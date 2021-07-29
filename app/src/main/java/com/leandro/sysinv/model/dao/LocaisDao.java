package com.leandro.sysinv.model.dao;

import com.leandro.sysinv.model.entities.Local;

import java.util.List;

public interface LocaisDao {

    void insert(Local obj);
    void update(Local obj);
    void deleteById(Integer id);
    Local findById(Integer id);
    List<Local> findAll();
    void deleteAll();

}

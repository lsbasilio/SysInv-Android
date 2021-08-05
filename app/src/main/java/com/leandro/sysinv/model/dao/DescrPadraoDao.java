package com.leandro.sysinv.model.dao;

import com.leandro.sysinv.model.entities.DescrPadrao;

import java.util.List;

public interface DescrPadraoDao {

    void insert(DescrPadrao obj);
    void update(DescrPadrao obj);
    void deleteById(String id);
    DescrPadrao findById(String id);
    List<DescrPadrao> findAll();
    void deleteAll();


}

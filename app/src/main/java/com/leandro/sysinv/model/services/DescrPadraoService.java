package com.leandro.sysinv.model.services;

import android.content.Context;

import com.leandro.sysinv.model.dao.DaoFactory;
import com.leandro.sysinv.model.dao.DescrPadraoDao;
import com.leandro.sysinv.model.entities.DescrPadrao;

import java.util.List;

public class DescrPadraoService {

    private Context context;
    private DescrPadraoDao dao = DaoFactory.createDescrPadraoDao(this.context);

    public DescrPadraoService(Context context) {
        this.context = context;
    }

    public void saveOrUpdate(DescrPadrao obj) {
        if (dao.findById(obj.getDescricao_id()) == null)
            dao.insert(obj);
        else
            dao.update(obj);
    }

    public DescrPadrao findById(String id) {
        return dao.findById(id);
    }

    public List<DescrPadrao> findAll() {
        return dao.findAll();
    }

    public void remove(DescrPadrao obj) {
        dao.deleteById(obj.getDescricao_id());
    }

    public void deleteAll() {
        dao.deleteAll();
    }

}

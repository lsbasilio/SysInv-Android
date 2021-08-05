package com.leandro.sysinv.model.services;

import android.content.Context;

import com.leandro.sysinv.model.dao.CentroDeCustoDao;
import com.leandro.sysinv.model.dao.DaoFactory;
import com.leandro.sysinv.model.entities.CentroDeCusto;

import java.util.List;

public class CentroDeCustoService {

    private Context context;
    private CentroDeCustoDao dao = DaoFactory.createCentroDeCustoDao(this.context);

    public CentroDeCustoService(Context context) {
        this.context = context;
    }

    public void saveOrUpdate(CentroDeCusto obj) {
        if (dao.findById(obj.getCcusto_id()) == null)
            dao.insert(obj);
        else
            dao.update(obj);
    }

    public CentroDeCusto findById(Integer id) {
        return dao.findById(id);
    }

    public List<CentroDeCusto> findAll() {
        return dao.findAll();
    }

    public void remove(CentroDeCusto obj) {
        dao.deleteById(obj.getCcusto_id());
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public CentroDeCusto findCcustoAtivo() {
        return dao.findCcustoAtivo();
    }
}

package com.leandro.sysinv.model.services;

import android.content.Context;

import com.leandro.sysinv.model.dao.DaoFactory;
import com.leandro.sysinv.model.dao.LocaisDao;
import com.leandro.sysinv.model.entities.Local;

import java.util.List;

public class LocaisService {

    private Context context;
    private LocaisDao dao; //= DaoFactory.createLocaisDao(this.context);

    public LocaisService(Context context) {
        this.context = context;
        dao = DaoFactory.createLocaisDao(this.context);
    }

    public void saveOrUpdate(Local obj) {
        if (dao.findById(obj.getLocal_id()) == null)
            dao.insert(obj);
        else
            dao.update(obj);
    }

    public Local findById(Integer id) {
        return dao.findById(id);
    }

    public List<Local> findAll() {
        return dao.findAll();
    }

    public List<Local> findByDescricao(String descricao) {
        return dao.findByDescricao(descricao);
    }

    public void remove(Local obj) {
        dao.deleteById(obj.getLocal_id());
    }

    public void deleteAll() {
        dao.deleteAll();
    }

}

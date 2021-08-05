package com.leandro.sysinv.model.services;

import android.content.Context;

import com.leandro.sysinv.model.dao.DaoFactory;
import com.leandro.sysinv.model.dao.DescrComplementarDao;
import com.leandro.sysinv.model.entities.DescrComplementar;
import com.leandro.sysinv.model.entities.DescrPadrao;

import java.util.List;

public class DescrComplementarService {

    private Context context;
    private DescrComplementarDao dao = DaoFactory.createDescrComplementarDao(this.context);

    public DescrComplementarService(Context context) {
        this.context = context;
    }

    public void saveOrUpdate(DescrComplementar obj) {
        if (dao.findById(obj.getDescricao_id()) == null)
            dao.insert(obj);
        else
            dao.update(obj);
    }

    public DescrComplementar findById(String id) {
        return dao.findById(id);
    }

    public List<DescrComplementar> findAll() {
        return dao.findAll();
    }

    public void remove(DescrComplementar obj) {
        dao.deleteById(obj.getDescricao_id());
    }

    public void deleteAll() {
        dao.deleteAll();
    }

}

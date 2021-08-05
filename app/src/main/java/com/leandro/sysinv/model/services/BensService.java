package com.leandro.sysinv.model.services;

import android.content.Context;

import com.leandro.sysinv.model.dao.BensDao;
import com.leandro.sysinv.model.dao.DaoFactory;
import com.leandro.sysinv.model.entities.Bens;
import com.leandro.sysinv.model.entities.enums.BensStatus;

import java.util.List;

public class BensService {

    private Context context;
    private BensDao dao = DaoFactory.createBensDao(this.context);

    public BensService(Context context) {
        this.context = context;
    }

    public void saveOrUpdate(Bens obj) {
        if (obj.getStatus() == BensStatus.NAO_ENCONTRADO) {
            obj.setStatus(BensStatus.NOVO);
            dao.insert(obj);
        }
        else {
            if (obj.getStatus() == BensStatus.PENDENTE)
                if (obj.getNumero_bemant() != 0 && obj.getNumero_bemant() != null)
                    obj.setStatus(BensStatus.NUMERO_TROCADO);
                else
                    obj.setStatus(BensStatus.INVENTARIADO);

            dao.update(obj);
        }
    }

    public Bens findById(Integer id) {
        return dao.findById(id);
    }

    public List<Bens> findAll() {
        return dao.findAll();
    }

    public void remove(Bens obj) {
        dao.deleteById(obj.getNumero_bem());
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    public boolean existe(Integer id) {
        return dao.existe(id);
    }

    public void cancelar(Bens obj) {
        dao.cancelar(obj);
    }

}

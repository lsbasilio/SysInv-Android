package com.leandro.sysinv.model.dao;

import android.content.Context;

import com.leandro.sysinv.db.Db;
import com.leandro.sysinv.model.dao.impl.BensSqLite;
import com.leandro.sysinv.model.dao.impl.CentroDeCustoSqLite;
import com.leandro.sysinv.model.dao.impl.DescrComplementarSqLite;
import com.leandro.sysinv.model.dao.impl.DescrPadraoSqLite;
import com.leandro.sysinv.model.dao.impl.LocaisSqLite;


public class DaoFactory {

    public static BensDao createBensDao(Context context) {
        return new BensSqLite(Db.getConnection(context));
    }

    public static CentroDeCustoDao createCentroDeCustoDao(Context context) {
        return new CentroDeCustoSqLite(Db.getConnection(context));
    }

    public static DescrComplementarDao createDescrComplementarDao(Context context) {
        return new DescrComplementarSqLite(Db.getConnection(context));
    }

    public static DescrPadraoDao createDescrPadraoDao(Context context) {
        return new DescrPadraoSqLite(Db.getConnection(context));
    }

    public static LocaisDao createLocaisDao(Context context) {
        return new LocaisSqLite(Db.getConnection(context));
    }
}

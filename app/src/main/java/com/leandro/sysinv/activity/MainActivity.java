package com.leandro.sysinv.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.leandro.sysinv.R;
import com.leandro.sysinv.db.DadosOpenHelper;
import com.leandro.sysinv.db.Db;
import com.leandro.sysinv.model.dao.impl.BensSqLite;
import com.leandro.sysinv.model.dao.impl.CentroDeCustoSqLite;
import com.leandro.sysinv.model.dao.impl.DescrComplementarSqLite;
import com.leandro.sysinv.model.dao.impl.DescrPadraoSqLite;
import com.leandro.sysinv.model.dao.impl.LocaisSqLite;
import com.leandro.sysinv.model.entities.Bens;
import com.leandro.sysinv.model.entities.CentroDeCusto;
import com.leandro.sysinv.model.entities.DescrComplementar;
import com.leandro.sysinv.model.entities.DescrPadrao;
import com.leandro.sysinv.model.entities.Local;
import com.leandro.sysinv.model.entities.enums.CcustoStatus;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SQLiteDatabase bancoDados2 = openOrCreateDatabase("app", MODE_PRIVATE, null);

       // Db conn = new Db();

        TestaBens();

        //bensDAO.deleteById(2);

        /*Cursor cur = bancoDados.rawQuery("SELECT * FROM bens", null);

        while (cur.moveToNext()) {
            Log.i("Resultado - id: ", cur.getString(0));
            Log.i("Resultado - nome: ", cur.getString(9));
            Log.i("Resultado - ccusto: ", cur.getString(1));
            Log.i("Resultado - local: ", cur.getString(7));

        }*/

        Db.closeConnection();

    }

    public void TestaBens() {

        SQLiteDatabase bancoDados = Db.getConnection(this);

        BensSqLite bensDAO = new BensSqLite(bancoDados);
        CentroDeCustoSqLite ccustoDAO = new CentroDeCustoSqLite(bancoDados);
        LocaisSqLite locaisDAO = new LocaisSqLite(bancoDados);


        ccustoDAO.deleteAll();
        locaisDAO.deleteAll();

        Bens obj1 = new Bens();
        obj1.setNumero_bem(1);
        obj1.setDescricao("BEM 1");

        CentroDeCusto ccusto1 = new CentroDeCusto(1, "Ccusto 1");
        obj1.setCcusto_atual(ccusto1);
        ccustoDAO.insert(ccusto1);

        Local local1 = new Local(1, "Local1");
        obj1.setLocal_atual(local1);
        locaisDAO.insert(local1);

        Bens obj2 = new Bens();
        obj2.setNumero_bem(2);
        obj2.setDescricao("BEM 2");

        CentroDeCusto ccusto2 = new CentroDeCusto(2, "Ccusto 2");
        obj2.setCcusto_atual(ccusto2);
        ccustoDAO.insert(ccusto2);

        CentroDeCusto ccustoAnterior = new CentroDeCusto(666, "Ccusto Anterior");
        ccustoDAO.insert(ccustoAnterior);

        Local local2 = new Local(2, "Local 2");
        obj2.setLocal_atual(local2);
        locaisDAO.insert(local2);

        //obj2.setStatus(CcustoStatus.ATIVO);

       /* bancoDados.execSQL("INSERT INTO centrodecusto(CCUSTO_ID,DESCRICAO) VALUES (1, 'CCUSTO 1')");
        bancoDados.execSQL("INSERT INTO centrodecusto(CCUSTO_ID,DESCRICAO) VALUES (2, 'CCUSTO 2')");*/

        bensDAO.deleteAll();

        bensDAO.insert(obj1);
        bensDAO.insert(obj2);

        obj2.setDescricao("BE 2");

        bensDAO.update(obj2);
/*
        CentroDeCusto ccustoAtivo = ccustoDAO.findCcustoAtivo();
        Log.i("Resultado - id: ", ccustoAtivo.getCcusto_id().toString());
        Log.i("Resultado - nome: ", ccustoAtivo.getDescricao());
*/

        List<Bens> lista = bensDAO.findAll();

        for (Bens bem : lista) {
            Log.i("Resultado - id: ", bem.getNumero_bem().toString());
            Log.i("Resultado - nome: ", bem.getDescricao());
            //CentroDeCusto ccusto = bem.getCcusto_atual();
            Log.i("Resultado - ccusto: ", bem.getCcusto_atual().getDescricao());
            //Local local = bem.getLocal_atual();
            Log.i("Resultado - local: ", bem.getLocal_atual().getDescricao());
            //Log.i("Resultado - Status: " + ccusto.getStatus().ordinal() + 1, "status");
        }

        Bens obj3 = bensDAO.findById(1);
        Log.i("Achou - ", obj3.getDescricao());

        Log.i("Ccusto", ccusto1.toString());
        Log.i("Ccusto", ccusto2.toString());

    }
}

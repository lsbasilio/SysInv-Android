package com.leandro.sysinv;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.leandro.sysinv.db.DadosOpenHelper;
import com.leandro.sysinv.db.Db;
import com.leandro.sysinv.model.dao.impl.CentroDeCustoSqLite;
import com.leandro.sysinv.model.dao.impl.DescrComplementarSqLite;
import com.leandro.sysinv.model.dao.impl.DescrPadraoSqLite;
import com.leandro.sysinv.model.dao.impl.LocaisSqLite;
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

        SQLiteDatabase bancoDados = Db.getConnection(this);
        DescrComplementarSqLite descrComplementarDAO = new DescrComplementarSqLite(bancoDados);
        //CentroDeCustoSqLite ccustoDAO = new CentroDeCustoSqLite(bancoDados);

        DescrComplementar obj1 = new DescrComplementar();
        obj1.setDescricao_id("1");
        obj1.setDescricao("Descr compl TESTE 1");

        DescrComplementar obj2 = new DescrComplementar();
        obj2.setDescricao_id("2");
        obj2.setDescricao("Descr compl TESTE 2");
        //obj2.setStatus(CcustoStatus.ATIVO);

       /* bancoDados.execSQL("INSERT INTO centrodecusto(CCUSTO_ID,DESCRICAO) VALUES (1, 'CCUSTO 1')");
        bancoDados.execSQL("INSERT INTO centrodecusto(CCUSTO_ID,DESCRICAO) VALUES (2, 'CCUSTO 2')");*/

        descrComplementarDAO.deleteAll();

        descrComplementarDAO.insert(obj1);
        descrComplementarDAO.insert(obj2);

        obj2.setDescricao("DC 2");

        descrComplementarDAO.update(obj2);
/*
        CentroDeCusto ccustoAtivo = ccustoDAO.findCcustoAtivo();
        Log.i("Resultado - id: ", ccustoAtivo.getCcusto_id().toString());
        Log.i("Resultado - nome: ", ccustoAtivo.getDescricao());
*/

        List<DescrComplementar> lista = descrComplementarDAO.findAll();

        for (DescrComplementar descrComplementar : lista) {
            Log.i("Resultado - id: ", descrComplementar.getDescricao_id());
            Log.i("Resultado - nome: ", descrComplementar.getDescricao());
            //Log.i("Resultado - Status: " + ccusto.getStatus().ordinal() + 1, "status");
        }

        DescrComplementar obj3 = descrComplementarDAO.findById("1");
        Log.i("Achou - ", obj3.getDescricao());

        descrComplementarDAO.deleteById(2);

        Cursor cur = bancoDados.rawQuery("SELECT * FROM descrcomplementar", null);

        while (cur.moveToNext()) {
            Log.i("Resultado - id: ", cur.getString(0));
            Log.i("Resultado - nome: ", cur.getString(1));
        }

        Db.closeConnection();

    }
}

package com.leandro.sysinv;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.leandro.sysinv.db.DadosOpenHelper;
import com.leandro.sysinv.db.Db;
import com.leandro.sysinv.model.dao.impl.CentroDeCustoSqLite;
import com.leandro.sysinv.model.entities.CentroDeCusto;

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
        CentroDeCustoSqLite ccustoDAO = new CentroDeCustoSqLite(bancoDados);

        CentroDeCusto obj1 = new CentroDeCusto();
        obj1.setCcusto_id(1);
        obj1.setDescricao("CCUSTO TESTE 1");

        CentroDeCusto obj2 = new CentroDeCusto();
        obj2.setCcusto_id(2);
        obj2.setDescricao("CCUSTO TESTE 2");

       /* bancoDados.execSQL("INSERT INTO centrodecusto(CCUSTO_ID,DESCRICAO) VALUES (1, 'CCUSTO 1')");
        bancoDados.execSQL("INSERT INTO centrodecusto(CCUSTO_ID,DESCRICAO) VALUES (2, 'CCUSTO 2')");*/

        ccustoDAO.deleteAll();

        ccustoDAO.insert(obj1);
        ccustoDAO.insert(obj2);

        List<CentroDeCusto> lista = ccustoDAO.findAll();

        for (CentroDeCusto ccusto : lista) {
            Log.i("Resultado - id: ", ccusto.getCcusto_id().toString());
            Log.i("Resultado - nome: ", ccusto.getDescricao());
        }

        CentroDeCusto obj3 = ccustoDAO.findById(2);
        Log.i("Achou - ", obj3.getDescricao());

        /*Cursor cur = bancoDados.rawQuery("SELECT * FROM centrodecusto", null);

        while (cur.moveToNext()) {
            Log.i("Resultado - id: ", cur.getString(0));
            Log.i("Resultado - nome: ", cur.getString(1));
        }
*/
        bancoDados.close();

    }
}

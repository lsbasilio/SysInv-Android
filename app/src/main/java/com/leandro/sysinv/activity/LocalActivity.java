package com.leandro.sysinv.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leandro.sysinv.R;
import com.leandro.sysinv.adapter.AdapterLocais;
import com.leandro.sysinv.db.Db;
import com.leandro.sysinv.model.dao.impl.BensSqLite;
import com.leandro.sysinv.model.dao.impl.CentroDeCustoSqLite;
import com.leandro.sysinv.model.dao.impl.LocaisSqLite;
import com.leandro.sysinv.model.entities.Bens;
import com.leandro.sysinv.model.entities.CentroDeCusto;
import com.leandro.sysinv.model.entities.Local;
import com.leandro.sysinv.model.services.LocaisService;

import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends AppCompatActivity {

    private SQLiteDatabase bancoDados;
    private LocaisService service;
    private AdapterLocais adapterLocais;
    private RecyclerView recyclerLocais;
    private List<Local> listaLocais = new ArrayList<>();
    private ImageView imageBusca;
    private EditText textBusca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais);

        recyclerLocais = findViewById(R.id.recyclerLocais);
        imageBusca = findViewById(R.id.imageBusca);
        textBusca = findViewById(R.id.textBusca);


        // Obtendo lista de Locais
        //LocaisService service = new LocaisService(this);
        service = new LocaisService(this);
        listaLocais = service.findAll();
        /*Local local = service.findById(34000);
        listaLocais.add(local);*/

        // Configurar Adapter
        adapterLocais = new AdapterLocais( listaLocais, getApplicationContext() );

        // Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerLocais.setLayoutManager(layoutManager);
        recyclerLocais.setHasFixedSize(true);   // Otimização: tamanho fixo
        recyclerLocais.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerLocais.setAdapter( adapterLocais );

    }

    public void onClickBusca(View v) {

        String strBusca = textBusca.getText().toString().trim();
        boolean findByDescricao = false;
        Toast toast;

        if (!strBusca.isEmpty()) {
            listaLocais = service.findByDescricao(strBusca);
            findByDescricao = true;
        } else {
            listaLocais = service.findAll();
        }

        if (!listaLocais.isEmpty()) {

            adapterLocais = null;
            adapterLocais = new AdapterLocais( listaLocais, getApplicationContext() );
            recyclerLocais.setAdapter( adapterLocais );

            if (findByDescricao) {

                toast = Toast.makeText(getApplicationContext(), "Foram encontrados " + listaLocais.size() + " Locais com a palavra '" + strBusca + "'", Toast.LENGTH_LONG);
                toast.show();

            } else {

                toast = Toast.makeText(getApplicationContext(), "Foram encontrados " + listaLocais.size() + " Locais", Toast.LENGTH_LONG);
                toast.show();

            }

        } else {

            toast = Toast.makeText(getApplicationContext(), "Nenhum Local encontrado!", Toast.LENGTH_LONG);
            toast.show();

        }

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

package com.leandro.sysinv.activity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.leandro.sysinv.R;
import com.leandro.sysinv.db.Db;
import com.leandro.sysinv.model.dao.impl.DescrPadraoSqLite;
import com.leandro.sysinv.model.entities.DescrPadrao;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button buttonLogar, buttonFTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail   = findViewById(R.id.editEmail);
        editSenha   = findViewById(R.id.editSenha);
        buttonLogar = findViewById(R.id.buttonLogar);
        buttonFTP = findViewById(R.id.buttonFTP);

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailUsuario = editEmail.getText().toString();
                String senhaUsuario = editSenha.getText().toString();

                Log.i("Entrou aqui", emailUsuario);
                Log.i("Entrou aqui", senhaUsuario);

                if (emailUsuario.equals("leandro@gmail.com") && senhaUsuario.equals("leandro")) {

                    Log.i("Entrou aqui", "Passou aqui");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity( intent );

                }

            }
        });

        buttonFTP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Log.i("Storage Dir", getFilesDir().getPath());

                try {
                    SQLiteDatabase bancoDados = Db.getConnection(getApplicationContext());

                    DescrPadraoSqLite descrPadraoDAO = new DescrPadraoSqLite(bancoDados);

                    descrPadraoDAO.carregaArquivoCsv("001", getApplicationContext());

                    List<DescrPadrao> lista = descrPadraoDAO.findAll();

                    for (DescrPadrao descr : lista) {

                        Log.i("Resultado - id: ", descr.getDescricao_id());
                        Log.i("Resultado - nome: ", descr.getDescricao());

                    }
                } catch (IOException e) {
                    Log.i("erro", e.getMessage());
                }

                /*File diretorio = new File(Environment.getExternalStorageDirectory() + "/Inventario");

                if (!diretorio.isDirectory())
                    Log.i("Cartao", "não é diretório");
                else
                    Log.i("Cartao", "é diretório");


                try {

                    conectaFTP();

                } catch (SocketException s) {

                } catch (IOException i) {

                }*/
            }
        });
    }

    private void conectaFTP()  throws SocketException, IOException {

        FTPClient ftp = new FTPClient();

        ftp.connect("localhost");
        ftp.login("leandro", "KGZlb4590");
        ftp.port(null, 21);
        //ftp.changeWorkingDirectory ("meuDir");

        String[] arq = ftp.listNames();

        Log.i("Listando arquivos: \n", "Arquivos");

        for (String f : arq) {

            Log.i("Arquivo: ", f);
        }

        ftp.logout();
        ftp.disconnect();
    }
}

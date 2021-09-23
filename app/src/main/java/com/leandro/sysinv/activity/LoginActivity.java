package com.leandro.sysinv.activity;

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
import com.leandro.sysinv.model.dao.impl.LocaisSqLite;
import com.leandro.sysinv.model.entities.Local;

import org.apache.commons.net.ftp.FTPClient;

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

                    Intent intent = new Intent(getApplicationContext(), LocalActivity.class);
                    startActivity( intent );

                }

            }
        });

        buttonFTP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try {
                    //Log.i("Storage Dir", getFilesDir().getPath());

                    Log.i("SdCArd", Environment.getExternalStorageDirectory().getAbsolutePath());

                    SQLiteDatabase bancoDados = Db.getConnection(getApplicationContext());

                    LocaisSqLite locaisDAO = new LocaisSqLite(bancoDados);

                    locaisDAO.carregaArquivoCsv("001", getApplicationContext());

                    List<Local> lista = locaisDAO.findAll();

                    for (Local loc : lista) {

                        Log.i("Resultado - id: ", loc.getLocal_id().toString());
                        Log.i("Resultado - nome: ", loc.getDescricao());

                    }
                } catch (IOException i) {
                   Log.i("Erro", i.getMessage());
                }
                /*File diretorio = new File(Environment.getExternalStorageDirectory() + "/Inventario");

                if (!diretorio.isDirectory())
                    Log.i("Cartao", "não é diretório");
                else
                    Log.i("Cartao", "é diretório");*/


                /*SQLiteDatabase bancoDados = Db.getConnection(getApplicationContext());
                Log.i("Data", getDatabasePath("InventarioBD").getPath());*/

                /*try {

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

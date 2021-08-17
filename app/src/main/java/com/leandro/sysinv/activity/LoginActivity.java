package com.leandro.sysinv.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leandro.sysinv.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button buttonLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail   = findViewById(R.id.editEmail);
        editSenha   = findViewById(R.id.editSenha);
        buttonLogar = findViewById(R.id.buttonLogar);

        buttonLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailUsuario = editEmail.getText().toString();
                String senhaUsuario = editSenha.getText().toString();

                if (emailUsuario == "leandro@gmail.com" && senhaUsuario == "leandro") {

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity( intent );

                }

            }
        });
    }
}

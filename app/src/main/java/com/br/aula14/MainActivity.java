package com.br.aula14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.br.aula14.dao.PessoaDAO;
import com.br.aula14.entity.Pessoa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    EditText editTextNome;
    EditText editTextTelefone;
    EditText editTextEmail;
    Button btnSalvar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNome = findViewById(R.id.editTextNome);
        editTextTelefone = findViewById(R.id.editTextTelefone);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(editTextNome.getText().toString().equals("") || editTextTelefone.getText().toString().equals("") || editTextEmail.getText().toString().equals(""))) {

                    PessoaDAO dao = new PessoaDAO(getApplicationContext());
                    Pessoa pessoa = new Pessoa();

                    pessoa.setNome(editTextNome.getText().toString());
                    pessoa.setTelefone(editTextTelefone.getText().toString());
                    pessoa.setEmail(editTextEmail.getText().toString());

                    dao.cadastrar(pessoa);
                    dao.close();

                    limpaFormulario();

                } else {

                    Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();

                }

            }
        });

        exibirDados();

    }

    private void exibirDados() {
        FloatingActionButton fab = findViewById(R.id.fabListar);
        Intent intent = new Intent(this, ListActivity.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }

    private void limpaFormulario() {
        editTextNome.setText("");
        editTextNome.requestFocus();
        editTextTelefone.setText("");
        editTextEmail.setText("");
    }
}